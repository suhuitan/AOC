package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.Day;

public class Day16 implements Day {

  public static Pattern conditionPattern = Pattern
      .compile("(?<field>.+): (?<range1min>\\d+)-(?<range1max>\\d+) or (?<range2min>\\d+)-(?<range2max>\\d+)");

  @Override
  public String part1(List<String> input) {
    List<Condition> conditions = getConditions(input);
    List<List<Integer>> nearbyTickets = getNearbyTickets(input);

    List<Integer> errors = getErrors(conditions, nearbyTickets);

    int errorRate = errors.stream()
        .reduce(Integer::sum)
        .orElse(0);

    return String.valueOf(errorRate);
  }

  public List<Integer> getErrors(List<Condition> conditions, List<List<Integer>> nearbyTickets) {
    return nearbyTickets.stream()
        .flatMap(List::stream)
        .filter((ticketNumber) -> !matchConditions(conditions, ticketNumber))
        .collect(Collectors.toList());
  }

  private boolean matchConditions(List<Condition> conditions, Integer ticketNumber) {
    return conditions.stream()
        .anyMatch((condition) -> condition.isValid(ticketNumber));
  }

  private List<List<Integer>> getNearbyTickets(List<String> input) {
    int counter = input.indexOf(new String("nearby tickets:")) + 1;
    return input.subList(counter, input.size())
        .stream()
        .map(s -> Arrays.asList(s.split(",")))
        .map(arr -> arr.stream().map(Integer::valueOf).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  private List<Condition> getConditions(List<String> input) {
    List<Condition> conditions = new ArrayList<>();
    int counter = 0;
    while (counter < input.indexOf("")) {
      conditions.add(new Condition(input.get(counter)));
      counter++;
    }
    return conditions;
  }

  @Override
  public String part2(List<String> input) {
    List<Condition> conditions = new ArrayList<>(getConditions(input));

    List<Integer> myTicket = Arrays.asList(input.get(input.indexOf("your ticket:") + 1).split(","))
        .stream()
        .map(Integer::valueOf).collect(
            Collectors.toList());

    List<List<Integer>> validTickets = getNearbyTickets(input)
        .stream()
        .filter((ticket) -> ticket.stream().allMatch((num) -> matchConditions(conditions, num)))
        .collect(Collectors.toList());

    List<List<Condition>> possibleConditionsPerPosition = findPossibleConditionsPerPosition(conditions, validTickets);
    Map<Condition, Integer> finalPosition = reducePossibleConditions(possibleConditionsPerPosition);


    List<Integer> finalValues = finalPosition.keySet()
        .stream().filter((condition) -> condition.getName().startsWith("departure"))
        .map(finalPosition::get)
        .map(myTicket::get)
        .collect(Collectors.toList());

    Long product = 1L;
    for (Integer val : finalValues) {
      product *= val;
    }

    return String.valueOf(product);
  }

  public Map<Condition, Integer> reducePossibleConditions(List<List<Condition>> possibleConditionsPerPosition) {
    Map<Condition, Integer> finalPosition = new HashMap<>();

    boolean updatedThisIteration = false;
    int i = 0;
    while (true) {
      // find the position that has only one possible condition, and remove that from the list
      // this absolutely breaks when there is a 2 way tie, however it is not the case in this input so ha!
      // probably the better way to do this is some grid[][] approach and find the grid row/column that is all filled
      if (possibleConditionsPerPosition.get(i).size() == 1) {
        Condition field = possibleConditionsPerPosition.get(i).get(0);
        finalPosition.put(field, i);
        for (List<Condition> possibleConditions : possibleConditionsPerPosition) {
          boolean removed = possibleConditions.remove(field);
          updatedThisIteration = updatedThisIteration || removed;
        }
      }

      i++;

      if (i == possibleConditionsPerPosition.size()) {
        if (possibleConditionsPerPosition.stream().allMatch(List::isEmpty)) {
          break;
        }
        // reset
        i = 0;
        updatedThisIteration = false;
      }
    }
    
    return finalPosition;
  }

  public List<List<Condition>> findPossibleConditionsPerPosition(List<Condition> conditions,
      List<List<Integer>> validNearbyTickets) {
    List<List<Condition>> possibleConditionsPerPosition = new ArrayList<>();
    for (int i = 0; i < validNearbyTickets.get(0).size(); i++) {
      int finalI = i;
      List<Condition> possibleConditions = conditions.stream()
          .filter((condition) ->
              validNearbyTickets.stream()
                  .map((ticket) -> ticket.get(finalI))
                  .allMatch((num) -> condition.isValid(num))
          )
          .collect(Collectors.toList());
      possibleConditionsPerPosition.add(i, possibleConditions);
    }
    return possibleConditionsPerPosition;
  }

  private class Condition {

    String name;
    int range1min, range1max;
    int range2min, range2max;

    public Condition(String input) {
      Matcher matcher = conditionPattern.matcher(input);
      if (matcher.matches()) {
        name = matcher.group("field");
        range1min = Integer.valueOf(matcher.group("range1min"));
        range1max = Integer.valueOf(matcher.group("range1max"));
        range2min = Integer.valueOf(matcher.group("range2min"));
        range2max = Integer.valueOf(matcher.group("range2max"));
      }
    }

    public boolean isValid(Integer ticketNumber) {
      return (range1min <= ticketNumber && ticketNumber <= range1max) || (range2min <= ticketNumber
          && ticketNumber <= range2max);
    }

    public String getName() {
      return name;
    }
  }
}
