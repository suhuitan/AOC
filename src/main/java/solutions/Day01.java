package solutions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.Day;

public class Day01 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Integer> integers = parseInput(input);
    List<Integer> indexes = findIndexesWhereValueIsMoreThanPrevious(integers);

    return String.valueOf(indexes.size());
  }

  private List<Integer> findIndexesWhereValueIsMoreThanPrevious(List<Integer> integers) {
    List<Integer> indexes = IntStream.range(1, integers.size())
        .filter((i) -> integers.get(i - 1) < integers.get(i))
        .boxed()
        .collect(Collectors.toList());
    return indexes;
  }

  private List<Integer> parseInput(List<String> input) {
    List<Integer> integers = input.stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    return integers;
  }

  @Override
  public String part2(List<String> input) {
    List<Integer> integers = parseInput(input);

    List<Integer> windows = IntStream.range(0, integers.size()-2)
        .map((i) -> integers.get(i) + integers.get(i+1) + integers.get(i+2))
        .boxed()
        .collect(Collectors.toList());

    List<Integer> indexes = findIndexesWhereValueIsMoreThanPrevious(windows);

    return String.valueOf(indexes.size());
  }

}
