package solutions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.Day;

public class Day07 implements Day {

  @Override
  public String part1(List<String> input) {
    Map<Integer, Long> crabPositionToCount = Arrays.stream(input.get(0).split(","))
        .map(Integer::parseInt)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    int max = crabPositionToCount.keySet().stream().max(Integer::compareTo).get();
    int min = crabPositionToCount.keySet().stream().min(Integer::compareTo).get();

    Long minFuelCost = IntStream.range(0, max)
        .mapToObj((i) -> computeFuelCost(i, crabPositionToCount))
        .min(Long::compareTo)
        .get();

    return String.valueOf(minFuelCost);
  }

  private Long computeFuelCost(int i, Map<Integer, Long> crabPositionToCount) {
    return crabPositionToCount.entrySet().stream()
        .map((entry) -> Math.abs(entry.getKey() - i) * entry.getValue())
        .reduce(0L, Long::sum);
  }

  @Override
  public String part2(List<String> input) {
    Map<Integer, Long> crabPositionToCount = Arrays.stream(input.get(0).split(","))
        .map(Integer::parseInt)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    int max = crabPositionToCount.keySet().stream().max(Integer::compareTo).get();
    int min = crabPositionToCount.keySet().stream().min(Integer::compareTo).get();

    Long minFuelCost = IntStream.range(0, max)
        .mapToObj((i) -> computeFuelCost2(i, crabPositionToCount))
        .min(Long::compareTo)
        .get();

    return String.valueOf(minFuelCost);
  }

  private Long computeFuelCost2(int i, Map<Integer, Long> crabPositionToCount) {
    return crabPositionToCount.entrySet().stream()
        .map((entry) -> {
          int n = Math.abs(entry.getKey() - i);
          int cost = n*(n+1)/2;
          return cost * entry.getValue();
        })
        .reduce(0L, Long::sum);
  }
}
