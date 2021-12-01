package solutions2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Day;

public class Day07 implements Day {

  private static final Pattern pattern = Pattern.compile("(?<number>\\d) (?<name>.+) bag.*");

  @Override
  public String part1(List<String> input) {
    Map<String, Bag> bags = parseInput(input);

    long possibleBagHolders = bags.values().stream()
        .filter((bag) -> bag.canHold(bags.get("shiny gold")))
        .count();

    return String.valueOf(possibleBagHolders);
  }

  private Map<String, Bag> parseInput(List<String> input) {
    Map<String, Bag> bags =  new HashMap<>();

    for(String rule: input) {
      String[] ruleParts = rule.split("( bags contain )|, ");

      String bagName = ruleParts[0];
      Bag bag = bags.computeIfAbsent(bagName, Bag::new);
      for (int i = 1; i < ruleParts.length; i++) {
        Matcher matcher = pattern.matcher(ruleParts[i]);
        if (matcher.matches()) {
          Integer number = Integer.valueOf(matcher.group("number"));
          String containedBagName = matcher.group("name");
          Bag containedBag = bags.computeIfAbsent(containedBagName, Bag::new);
          bag.addContainingBags(containedBag, number);
        }
      }
    }
    return bags;
  }

  @Override
  public String part2(List<String> input) {
    Map<String, Bag> bags = parseInput(input);

    int numberOfBags = bags.get("shiny gold").getContainingBagCount();

    return String.valueOf(numberOfBags);
  }

  private class Bag {
    String bagName;
    Map<Bag, Integer> containedBag = new HashMap<>();

    public Bag(String bagName) {
      this.bagName = bagName;
    }

    public void addContainingBags(Bag bag, Integer number) {
      containedBag.put(bag, number);
    }

    public boolean canHold(Bag bag) {
      return containedBag.keySet().stream()
          .anyMatch((b) -> b.equals(bag) || b.canHold(bag));
    }

    public int getContainingBagCount() {
      return containedBag.keySet().stream()
          .reduce(0, (count, bag) -> count + containedBag.get(bag) * (1+ bag.getContainingBagCount()), Integer::sum);
    }
  }
}
