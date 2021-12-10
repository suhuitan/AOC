package solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day10 implements Day {
  Map<Character, Character> matchingBrackets = Map.of('{','}',
      '(', ')',
      '[',']',
      '<', '>');

  @Override
  public String part1(List<String> input) {
    Map<String, Long> occurencesOfBrackets = input.stream()
        .map(this::check)
        .filter((result) -> !result.getKey())
        .map(Pair::getValue)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    Long errorScore = occurencesOfBrackets.entrySet().stream()
        .map((entry) -> {
          Long occurence = entry.getValue();
          switch (entry.getKey().charAt(0)) {
            case ')':
              return occurence * 3;
            case '}':
              return occurence * 1197;
            case ']':
              return occurence * 57;
            case '>':
              return occurence * 25137;
          }
          return 0L;
        })
        .reduce(0L, Long::sum);

    return String.valueOf(errorScore);
  }

  private Pair<Boolean, String> check(String s) {
    List<Character> stack = new ArrayList<>();
    for(Character c : s.toCharArray()) {
      switch (c) {
        case '{':
        case '(':
        case '[':
        case '<': stack.add(c); break;
        case '}':
        case ')':
        case ']':
        case '>':
          Character removed = stack.remove(stack.size() - 1);
          if (!matchingBrackets.get(removed).equals(c)) {
            return Pair.of(false, String.valueOf(c));
          }
      }
    }
    return Pair.of(true, stack.stream()
        .map(String::valueOf)
        .collect(Collectors.joining()));
  }

  @Override
  public String part2(List<String> input) {
    List<Long> scores = input.stream()
        .map(this::check)
        .filter(Pair::getKey)
        .map(Pair::getValue)
        .map(this::compute)
        .sorted()
        .collect(Collectors.toList());
    return String.valueOf(scores.get(scores.size()/2));
  }

  private Long compute(String bracketsToClose) {
    long totalScore = 0;
    for (int i = bracketsToClose.length()-1; i > -1 ; i--) {
      long value = 0L;
      Character c = bracketsToClose.charAt(i);
      switch(matchingBrackets.get(c)) {
        case ')': value = 1; break;
        case ']': value = 2; break;
        case '}': value = 3; break;
        case '>': value = 4; break;
      }
      totalScore *= 5;
      totalScore += value;
    }
    return totalScore;
  }

}
