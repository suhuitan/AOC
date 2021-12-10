package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.internal.collections.Pair;

import utils.Day;

public class Day09 implements Day {

  @Override
  public String part1(List<String> input) {
    int[][] arr = toPaddedArray(input);
    int riskLevel = 0;

    for (int i = 1; i < input.size() + 1; i++) {
      for (int j = 1; j < input.get(0).length()+1; j++) {
        if(isLocalMinimum(arr, i, j)) {
          riskLevel += arr[i][j] + 1;
        }
      }
    }
    return String.valueOf(riskLevel);
  }

  private boolean isLocalMinimum(int[][] arr, int i, int j) {
    int c = arr[i][j];
    int t = arr[i-1][j];
    int b = arr[i+1][j];
    int l = arr[i][j-1];
    int r = arr[i][j+1];

    List<Integer> numbers = Stream.of(c, t, b, l, r)
        .sorted()
        .collect(Collectors.toList());

    return numbers.get(0) == c && numbers.get(0) < numbers.get(1);
  }

  private int[][] toPaddedArray(List<String> input) {
    int[][] paddedArr = new int[input.size() + 2][input.get(0).length() + 2];
    for(int[] row : paddedArr) {
      Arrays.fill(row, 9);
    }

    for (int i = 1; i < input.size() + 1; i++) {
      for (int j = 1; j < input.get(0).length()+1; j++) {
        paddedArr[i][j] = Character.getNumericValue(input.get(i-1).charAt(j-1));
      }
    }
    return paddedArr;
  }

  @Override
  public String part2(List<String> input) {
    int[][] arr = toPaddedArray(input);
    List<Integer> basinSize = new ArrayList<>();

    for (int i = 1; i < input.size() + 1; i++) {
      for (int j = 1; j < input.get(0).length()+1; j++) {
        if(isLocalMinimum(arr, i, j)) {
          int size = countLowPortionsAround(arr, i, j);
          basinSize.add(size);
        }
      }
    }
    basinSize.sort(Comparator.reverseOrder());

    Integer largestThreeBasinsMultiplied = basinSize.subList(0, 3).stream()
        .reduce(1, (i, j) -> i * j);

    return String.valueOf(largestThreeBasinsMultiplied);
  }

  private int countLowPortionsAround(int[][] arr, int i, int j) {
     if(arr[i][j] == 9) {
       return 0;
     }
     arr[i][j] = 9;
     return countLowPortionsAround(arr, i - 1, j) + countLowPortionsAround(arr, i, j - 1) + countLowPortionsAround(arr, i, j + 1) + countLowPortionsAround(arr, i + 1, j) + 1;
  }
}
