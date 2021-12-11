package solutions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import utils.Coordinate;
import utils.Day;

public class Day11 implements Day {

  public static final List<Pair<Integer, Integer>> DIRECTIONS = Arrays
      .asList(
          Pair.of(-1, -1), Pair.of(-1, 0), Pair.of(-1, 1),
          Pair.of(0, -1), Pair.of(0, 1),
          Pair.of(1, -1), Pair.of(1, 0), Pair.of(1, 1)
      );
  int numberOfSteps;
  public Day11() {
    numberOfSteps = 100;
  }

  public Day11(int stepSize) {
    numberOfSteps = stepSize;
  }


  @Override
  public String part1(List<String> input) {
    int[][] startingGrid = parseInput(input);
    int flashes = 0;
    for (int i = 0; i < numberOfSteps; i++) {
      Pair<int[][], Integer> nextGrid = advance(startingGrid);
      flashes += nextGrid.getValue();
      startingGrid = nextGrid.getKey();
    }

    return String.valueOf(flashes);
  }

  private Pair<int[][], Integer> advance(int[][] grid) {
    int[][] nextGrid = new int[grid.length][grid[0].length];

    for (int i = 1; i < nextGrid.length - 1; i++) {
      for (int j = 1; j < nextGrid[0].length - 1; j++) {
        nextGrid[i][j] = grid[i][j] + 1;
      }
    }

    Set<Coordinate> flashedOctopuses = new HashSet<>();
    int initialSize = 0;

    do {
      initialSize = flashedOctopuses.size();
      for (int i = 1; i < nextGrid.length - 1; i++) {
        for (int j = 1; j < nextGrid[0].length - 1; j++) {
          if (nextGrid[i][j] > 9 && !flashedOctopuses.contains(new Coordinate(i, j))) {
            flashedOctopuses.add(new Coordinate(i, j));
            addToSurrounding8(nextGrid, i, j, 1);
          }
        }
      }
    } while (flashedOctopuses.size() != initialSize);

    for (int i = 1; i < nextGrid.length - 1; i++) {
      for (int j = 1; j < nextGrid[0].length - 1; j++) {
        nextGrid[i][j] = nextGrid[i][j] > 9 ? 0 : nextGrid[i][j];
      }
    }
    return Pair.of(nextGrid, flashedOctopuses.size());
  }


  public static void addToSurrounding8(int[][] arr, int x, int y, int valueToAdd) {
    for(Pair<Integer, Integer> coordinate : DIRECTIONS) {
      int i = x + coordinate.getLeft();
      int j = y + coordinate.getRight();
      arr[i][j] += valueToAdd;
    }
  }

  private int[][] parseInput(List<String> input) {
    int[][] grid = new int[input.size() + 2][input.get(0).length() + 2];
    for (int i = 1; i <= input.size(); i++) {
      for(int j = 1; j <= input.get(0).length(); j++) {
        grid[i][j] = Character.getNumericValue(input.get(i-1).charAt(j-1));
      }
    }
    return grid;
  }

  @Override
  public String part2(List<String> input) {

    int[][] startingGrid = parseInput(input);
    for (int i = 0; i < 500; i++) {
      Pair<int[][], Integer> nextGrid = advance(startingGrid);
      if (nextGrid.getRight() == 100) {
        return String.valueOf(i + 1);
      }
      startingGrid = nextGrid.getKey();
    }

    return String.valueOf(0);
  }
}
