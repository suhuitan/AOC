package solutions2020;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import utils.Day;

public class Day01 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Integer> numbers = getSortedIntegers(input);

    for (int num : numbers) {
      int target = 2020 - num;
      if (numbers.contains(target)) {
        return String.valueOf(num * target);
      }
    }

    return "NADA";
  }

  @Override
  public String part2(List<String> input) {

    List<Integer> numbers = getSortedIntegers(input);

    for (int i = 0; i < numbers.size(); i++) {
      int target1 = numbers.get(i);
      int targetSum = 2020 - target1;

      for (int j = i + 1; j < numbers.size(); j++) {
        int target2 = numbers.get(j);
        int target3 = targetSum - target2;

        if (numbers.contains(target3)) {
          return String.valueOf(target1 * target2 * target3);
        }
      }
    }
    return "NADA";
  }

  private List<Integer> getSortedIntegers(List<String> input) {
    List<Integer> numbers = input.stream()
        .map(Integer::valueOf)
        .collect(Collectors.toList());

    Collections.sort(numbers);
    return numbers;
  }
}
