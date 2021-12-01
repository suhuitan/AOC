package solutions2020;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import utils.Day;

public class Day05 implements Day {

  public static final int NO_OF_COL = 8;
  public static final int NO_OF_ROWS = 128;

  Set<Character> lowerHalfIndicators = Set.of('F', 'L');

  @Override
  public String part1(List<String> input) {
    Optional<Integer> max = input.stream()
        .map(this::findSeatNumber)
        .max(Integer::compareTo);

    return String.valueOf(max.get());
  }

  int findSeatNumber(String input) {
    String rowInput = input.substring(0, 7);
    String colInput = input.substring(7);
    //    int rowNumber = findTarget(rowInput, 0, NO_OF_ROWS - 1);
    //    int colNumber = findTarget(colInput, 0, NO_OF_COL - 1);
    int rowNumber = findTarget(rowInput);
    int colNumber = findTarget(colInput);
    return rowNumber * NO_OF_COL + colNumber;
  }

  int findTarget(String input) {
    input = input.replaceAll("[FL]", "0");
    input = input.replaceAll("[BR]", "1");
    return Integer.parseInt(input, 2);
  }

  // first solution - before i realized it was just binary
  int findTarget(String input, int min, int max) {
    if (input.isEmpty()) {
      return min;
    }

    if (lowerHalfIndicators.contains(input.charAt(0))) {
      max = max - (max - min) / 2 - 1;
    } else {
      min = min + (max - min) / 2 + 1;
    }

    return findTarget(input.substring(1), min, max);
  }

  @Override
  public String part2(List<String> input) {
    Set<Integer> allSeatNumbers = input.stream()
        .map(this::findSeatNumber)
        .collect(Collectors.toSet());

    Optional<Integer> first = allSeatNumbers.stream()
        // next seat number is missing
        .filter((seatNumber) -> !allSeatNumbers.contains(seatNumber + 1))
        // two seat numbers after is not missing
        .filter((seatNumber) -> allSeatNumbers.contains(seatNumber + 2))
        .findFirst();

    // return missing seat number
    return String.valueOf(first.get() + 1);
  }
}
