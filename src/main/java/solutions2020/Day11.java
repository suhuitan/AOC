package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.internal.collections.Pair;

import utils.Day;

public class Day11 implements Day {

  public static final List<Pair<Integer, Integer>> DIRECTIONS = Arrays
      .asList(
          new Pair<>(-1, -1), new Pair<>(-1, 0), new Pair<>(-1, 1),
          new Pair<>(0, -1), new Pair<>(0, 1),
          new Pair<>(1, -1), new Pair<>(1, 0), new Pair<>(1, 1)
      );

  @Override
  public String part1(List<String> input) {
    char[][] seatMap = processInput(input);
    // padding grid so that i don't have to deal with edge cases
    int rowSize = input.size() + 2;
    int colSize = input.get(0).length() + 2;

    int flipped = 0;

    do {
      flipped = 0;
      char[][] newMap = new char[rowSize][colSize];
      for (int i = 1; i < seatMap.length - 1; i++) {
        for (int j = 1; j < seatMap[i].length - 1; j++) {
          int occupancy = checkNeighbors(i, j, seatMap, false);
          boolean hasFlipped = flipOrCopy(seatMap, newMap, i, j, occupancy, 5);
          if (hasFlipped) {
            flipped++;
          }
        }
      }
      seatMap=newMap;
    } while (flipped > 0);

    //debug
    // printSeatMap(seatMaps.get(iteration));

    return String.valueOf(countOccupied(seatMap));
  }

  @Override
  public String part2(List<String> input) {
    char[][] seatMap = processInput(input);
    int rowSize = input.size() + 2;
    int colSize = input.get(0).length() + 2;

    int flipped = 0;

    do {
      flipped = 0;
      char[][] newMap = new char[rowSize][colSize];
      for (int i = 1; i < seatMap.length - 1; i++) {
        for (int j = 1; j < seatMap[i].length - 1; j++) {
          int occupancy = checkNeighbors(i, j, seatMap,true);
          boolean hasFlipped = flipOrCopy(seatMap, newMap, i, j, occupancy, 5);
          if (hasFlipped) {
            flipped++;
          }
        }
      }
      seatMap=newMap;
    } while (flipped > 0);

    //debug
    // printSeatMap(seatMaps.get(iteration));

    return String.valueOf(countOccupied(seatMap));
  }

  public boolean flipOrCopy(char[][] seatMap, char[][] newMap, int i, int j, int occupancy, int tolerance) {
    if (seatMap[i][j] == '#' && occupancy >= tolerance) {
      newMap[i][j] = 'L';
      return true;
    }
    if (seatMap[i][j] == 'L' && occupancy == 0) {

      newMap[i][j] = '#';
      return true;
    }
    newMap[i][j] = seatMap[i][j];

    return false;
  }

  private int checkNeighbors(int i, int j, char[][] seatMap, boolean searchDirection) {
    int occupied = 0;

    for (Pair<Integer, Integer> direction : DIRECTIONS) {
      int x = i + direction.first();
      int y = j + direction.second();
      do {
        if (seatMap[x][y] == '#') {
          occupied++;
          break;
        } else if (seatMap[x][y] == 'L') {
          break;
        }
        x += direction.first();
        y += direction.second();
      } while ( searchDirection && x > 0 && x < seatMap.length - 1 && y > 0 && y < seatMap[0].length - 1);
    }

    return occupied;
  }

  private void printSeatMap(char[][] chars) {
    for (int i = 0; i < chars.length; i++) {
      System.out.println(chars[i]);
    }
  }

  private char[][] processInput(List<String> input) {
    char[][] paddedMap = new char[input.size() + 2][input.get(0).length() + 2];
    for (int i = 0; i < input.size(); i++) {
      char[] in = input.get(i).toCharArray();
      System.arraycopy(in, 0, paddedMap[i + 1], 1, in.length);
    }
    return paddedMap;
  }

  private long countOccupied(char[][] seatMap) {
    return Arrays.stream(seatMap)
        .map(String::new)
        .flatMapToInt(String::chars)
        .filter(x -> x == 35)
        .count();
  }
}
