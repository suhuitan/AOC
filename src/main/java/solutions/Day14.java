package solutions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import utils.Day;

public class Day14 implements Day {
  Map<String, String> insertionRule = new HashMap<>();

  @Override
  public String part1(List<String> input) {
    Map<String, Long> finalFrequency = expandPolymer(input, 10);

    long max = finalFrequency.values().stream().max(Long::compare).get();
    long min = finalFrequency.values().stream().min(Long::compare).get();

    return String.valueOf(max - min);
  }

  @Override
  public String part2(List<String> input) {
    int steps = 40;
    Map<String, Long> finalFrequency = expandPolymer(input, steps);

    long max = finalFrequency.values().stream().max(Long::compare).get();
    long min = finalFrequency.values().stream().min(Long::compare).get();

    return String.valueOf(max - min);
  }

  private Map<String, Long> expandPolymer(List<String> input, int steps) {
    String polymerTemplate = input.get(0);

    insertionRule = input.subList(2, input.size())
        .stream()
        .map((s) -> s.split(" -> "))
        .collect(Collectors.toMap((s) -> s[0], (s) -> s[1]));

    Map<String, Long> pairCount = new HashMap<>();
    for (int i = 0, j = i + 1; i < polymerTemplate.length() - 1; i++, j++) {
      pairCount.compute(polymerTemplate.substring(i, j + 1), (k, v) -> v == null ? 1 : v + 1);
    }

    for (int i = 0; i < steps; i++) {
      pairCount = step(pairCount);
    }

    Map<String, Long> finalFrequency = new HashMap<>();

    for (String pair : pairCount.keySet()) {
      finalFrequency.merge(pair.substring(0, 1), pairCount.get(pair), Long::sum);
    }

    finalFrequency.merge(String.valueOf(polymerTemplate.charAt(polymerTemplate.length() - 1)), 1L, Long::sum);

    return finalFrequency;
  }

  private Map<String, Long> step(Map<String, Long> pairCount) {
    Map<String, Long> newPairCount = new HashMap<>();
    pairCount.entrySet()
        .stream()
        .forEach((entry) -> {
          String template = entry.getKey();
          String elementToInsert = insertionRule.get(template);
          String pair1 = template.substring(0, 1).concat(elementToInsert);
          String pair2 = elementToInsert.concat(template.substring(1, 2));
          newPairCount.merge(pair1, entry.getValue(), Long::sum);
          newPairCount.merge(pair2, entry.getValue(), Long::sum);
        });

    return newPairCount;
  }
}
