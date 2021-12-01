package solutions2020;

import java.util.List;

import utils.Day;

public class Day03 implements Day {

  @Override
  public String part1(List<String> inputs) {
    int numberOfTrees = getNumberOfTrees(inputs, 3, 1);

    return String.valueOf(numberOfTrees);
  }

  private int getNumberOfTrees(List<String> inputs, int horizontalDistance, int verticalDistance) {
    int numberOfTrees = 0;
    int currentIndex = 0;

    for (int i = 0; i < inputs.size(); i += verticalDistance) {
      String input = inputs.get(i);
      if (input.charAt(currentIndex) == '#') {
        numberOfTrees ++;
      }

      currentIndex = (currentIndex + horizontalDistance) % input.length();
    }

    return numberOfTrees;
  }

  @Override
  public String part2(List<String> input) {
    long totalTrees = (long) getNumberOfTrees(input, 1, 1)
        * getNumberOfTrees(input, 3, 1)
        * getNumberOfTrees(input, 5, 1)
        * getNumberOfTrees(input, 7, 1)
        * getNumberOfTrees(input, 1, 2);

    return String.valueOf(totalTrees);
  }
}
