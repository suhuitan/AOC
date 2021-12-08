package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day08 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Pair<List<String>, List<String>>> parsedInputs = splitInputs(input);
    List<String> count = parsedInputs.stream()
        .map((pair) -> pair.getRight())
        .flatMap(List::stream)
        .filter((s) -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
        .collect(Collectors.toList());

    return String.valueOf(count.size());
  }

  private List<Pair<List<String>, List<String>>> splitInputs(List<String> input) {
    return input.stream()
        .map((s) -> {
          List<String> split = Arrays.asList(s.split("\\W+"));
          return Pair.of(split.subList(0,10), split.subList(10, 14));
        })
        .collect(Collectors.toList());
  }

  @Override
  public String part2(List<String> input) {
    List<Integer> decodedValues  = splitInputs(input).stream()
        .map(this::decodeAttempt2)
        .collect(Collectors.toList());
    return String.valueOf(decodedValues.stream()
        .reduce(Integer::sum)
        .get());
  }

  private Integer decodeAttempt2(Pair<List<String>, List<String>> pair) {
    List<String> observedInputs = pair.getLeft().stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());

    Map<Integer, Long> numberOfOccurrences = observedInputs.stream()
        .flatMapToInt((s) -> s.chars())
        .boxed()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // you can tell these segments because they only occur once
    int segmentF = numberOfOccurrences.keySet().stream()
        .filter((k) -> numberOfOccurrences.get(k) == 9)
        .findFirst().get();

    int segmentE = numberOfOccurrences.keySet().stream()
        .filter((k) -> numberOfOccurrences.get(k) == 4)
        .findFirst().get();

    int segmentC = observedInputs.stream()
        .filter((s) -> s.length() == 2)
        .map((s) -> s.replace(String.valueOf((char)segmentF), ""))
        .findFirst().get().codePointAt(0);

    Integer number = pair.getRight().stream()
        .map((output) -> {
          switch(output.length()) {
            case 2: return "1";
            case 3: return "7";
            case 4: return "4";
            case 7: return "8";
            case 5:
              if(output.contains(String.valueOf((char) segmentE))) {
                return "2";
              } else if (output.contains(String.valueOf((char) segmentC))) {
                return "3";
              }
              return "5";
            case 6:
              if(!output.contains(String.valueOf((char) segmentE))) {
                return "9";
              } else if (output.contains(String.valueOf((char) segmentC))) {
                return "0";
              }
              return "6";
          }
          throw new IllegalArgumentException("unexpected length");
        })
        .reduce(String::concat)
        .map(Integer::valueOf)
        .get();

    return number;
  }

  private Integer decode(Pair<List<String>, List<String>> pair) {
    List<String> observedInputs = pair.getLeft().stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());

    Map<Integer, Long> numberOfOccurrences = observedInputs.stream()
        .flatMapToInt((s) -> s.chars())
        .boxed()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // you can tell these segments because they only occur once
    int segmentF = numberOfOccurrences.keySet().stream()
        .filter((k) -> numberOfOccurrences.get(k) == 9)
        .findFirst().get();

    int segmentE = numberOfOccurrences.keySet().stream()
        .filter((k) -> numberOfOccurrences.get(k) == 4)
        .findFirst().get();

    int segmentB = numberOfOccurrences.keySet().stream()
        .filter((k) -> numberOfOccurrences.get(k) == 6)
        .findFirst().get();

    // and then afterwards its a process of elimination
    int segmentC = observedInputs.get(0)
        .replace(Character.toString(segmentF), "")
        .codePointAt(0);

    int segmentA = observedInputs.get(1)
        .replace(Character.toString(segmentF), "")
        .replace(Character.toString(segmentC), "")
        .codePointAt(0);

    int segmentD = observedInputs.get(2)
        .replace(Character.toString(segmentF), "")
        .replace(Character.toString(segmentC), "")
        .replace(Character.toString(segmentB), "")
        .codePointAt(0);

    int segmentG = observedInputs.get(9)
        .replace(Character.toString(segmentA), "")
        .replace(Character.toString(segmentB), "")
        .replace(Character.toString(segmentC), "")
        .replace(Character.toString(segmentD), "")
        .replace(Character.toString(segmentE), "")
        .replace(Character.toString(segmentF), "")
        .codePointAt(0);

    SevenSegment sevenSegment = new SevenSegment(segmentA, segmentB, segmentC, segmentD, segmentE, segmentF, segmentG);

    Integer output = pair.getRight()
        .stream()
        .map(sevenSegment::getDigit)
        .reduce(String::concat)
        .map(Integer::valueOf)
        .get();

    return output;
  }


  private class SevenSegment {

    private List<Set<Integer>> values = new ArrayList<>();

    public SevenSegment(int a, int b, int c, int d, int e, int f, int g) {
      values.add(Set.of(a, b, c, e, f, g));
      values.add(Set.of(c, f));
      values.add(Set.of(a, c, d, e, g));
      values.add(Set.of(a, c, d, f, g));
      values.add(Set.of(b, c, d, f));
      values.add(Set.of(a, b, d, f, g));
      values.add(Set.of(a, b, d, e, f, g));
      values.add(Set.of(a, c, f));
      values.add(Set.of(a, b, c, d, e, f, g));
      values.add(Set.of(a, b, c, d, f, g));
    }

    String getDigit(String input) {
      Set<Integer> sortedInput = input.chars()
          .boxed()
          .collect(Collectors.toSet());

      return String.valueOf(values.indexOf(sortedInput));
    }
  }
}
