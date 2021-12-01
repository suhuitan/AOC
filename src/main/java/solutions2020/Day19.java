package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.Day;

public class Day19 implements Day {

  @Override
  public String part1(List<String> input) {
    Map<Integer, Rule> rules = parseRules(input.subList(0, input.indexOf("")));

    Rule zero = rules.get(0);

    List<String> poss = zero.getPossibilities();

    List<String> matches = input.subList(input.indexOf(""), input.size())
        .stream()
        .filter(poss::contains)
        .collect(Collectors.toList());

    return String.valueOf(matches.size());
  }

  private Map<Integer, Rule> parseRules(List<String> input) {
    Map<Integer, Rule> rules = new HashMap<>();

    for (int i = 0; i < input.size(); i++) {
      String in = input.get(i);
      String[] parts = in.split(": ");

      Integer ruleNumber = Integer.valueOf(parts[0]);
      Rule thisRule = rules.compute(ruleNumber, (k, v) -> v == null ? new Rule(k) : v);

      if (parts[1].contains(" | ")) {
        String[] subRuleArr = parts[1].split("( \\| )");

        for (int j = 0; j < subRuleArr.length; j++) {
          List<Rule> subRule = parseSubRules(rules, subRuleArr[j]);
          thisRule.addSubrule(subRule);
        }

      } else if (!parts[1].matches("\"[a|b]\"")) {
        thisRule.addSubrule(parseSubRules(rules, parts[1]));
      } else {
        thisRule.base = parts[1].substring(1, 2);
      }
    }
    return rules;
  }

  private List<Rule> parseSubRules(Map<Integer, Rule> rules, String s) {
    return Arrays.asList(s.split(" "))
        .stream()
        .mapToInt(Integer::valueOf)
        .mapToObj((subRuleNum) -> rules.compute(subRuleNum, (k, v) -> v == null ? new Rule(k) : v))
        .collect(Collectors.toList());
  }

  @Override
  public String part2(List<String> input) {
    Map<Integer, Rule> rules = parseRules(input.subList(0, input.indexOf("")));

    Rule fortyTwo = rules.get(42);
    List<String> possibilities42 = fortyTwo.getPossibilities();
    Rule thirtyOne = rules.get(31);
    List<String> possibilities31 = thirtyOne.getPossibilities();
    String regex42 = getRegex(possibilities42);
    String regex31 = getRegex(possibilities31);
    Pattern pattern42 = Pattern.compile(regex42);
    Pattern pattern31 = Pattern.compile(regex31);

    List<String> messages = input.subList(input.indexOf("")+1, input.size())
        .stream()
        .collect(Collectors.toList());

    List<String> matches = messages.stream().filter((message) -> checkRegex(pattern42, pattern31, message))
        .collect(Collectors.toList());

    return String.valueOf(matches.size());
  }

  private boolean checkRegex(Pattern regex42, Pattern regex31, String message) {
    Matcher matcher = regex42.matcher(message);
    int count42 = 0;
    int fromIndex = 0;
    while(matcher.find(fromIndex)) {
      if(matcher.start() > fromIndex){
        break;
      }
      fromIndex = matcher.end();
      count42++;
    }

    Matcher matcher2 = regex31.matcher(message);
    int count31 = 0;
    while(matcher2.find(fromIndex)) {
      if(matcher2.start() > fromIndex){
        return false;
      }
      fromIndex = matcher2.end();
      count31++;
    }

    return count31 != 0 && count42 > count31 && fromIndex == message.length();
  }

  private String getRegex(List<String> possibilities) {
    StringJoiner joiner = new StringJoiner(")|(", "((", "))");
    possibilities.forEach((el) ->
        joiner.add(el)
    );
    return joiner.toString();
  }

  private class Rule {

    int index = 0;
    List<List<Rule>> subRules = new ArrayList<>();
    String base = "";

    public Rule(Integer k) {
      index = k;
    }

    public void addSubrule(List<Rule> subRule) {
      this.subRules.add(subRule);
    }

    public List<String> getPossibilities() {
      List<String> strings = new ArrayList<>();

      if (!base.isEmpty()) {
        strings.add(base);
        return strings;
      }

      for (List<Rule> subrule : subRules) {
        List<String> concatanated = subrule.get(0).getPossibilities();
        for (int i = 0; i < subrule.size(); i++) {

          if (i + 1 < subrule.size()) {
            List<String> nextPossibilities = subrule.get(i + 1).getPossibilities();
            concatanated = concat(concatanated, nextPossibilities);
          }
        }
        strings.addAll(concatanated);
      }

      return strings;
    }

    private List<String> concat(List<String> concatanated, List<String> possibilities) {

      return concatanated.stream()
          .map((s) -> possibilities.stream()
              .map(s::concat)
              .collect(Collectors.toList()))
          .flatMap(Collection::stream)
          .collect(Collectors.toList());
    }
  }
}
