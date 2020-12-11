package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.internal.collections.Pair;

public class Day11 implements Day {

  public static final List<Pair<Integer, Integer>> DIRECTIONS = Arrays
      .asList(new Pair<>(-1, -1), new Pair<>(-1, 0), new Pair<>(-1, 1),
          new Pair<>(0, -1), new Pair<>(0, 1),
          new Pair<>(1, -1), new Pair<>(1, 0), new Pair<>(1, 1));

  @Override
  public String part1(List<String> input) {
    List<char[][]> seatMaps = new ArrayList<>();
    seatMaps.add(processInput(input));
    int rowSize = input.size() + 2;
    int colSize = input.get(0).length() + 2;

    int flipped = 0;
    int iteration = 0;

    do {
      char[][] seatMap = seatMaps.get(iteration++);
      flipped = 0;
      char[][] newMap = getBlank(rowSize, colSize);
      for (int i = 1; i < seatMap.length - 1; i++) {
        for (int j = 1; j < seatMap[i].length - 1; j++) {
          int occupancy = checkNeighbors(i, j, seatMap);
          boolean hasFlipped = flipOrCopy(seatMap, newMap, i, j, occupancy, 4);
          if (hasFlipped) {
            flipped++;
          }
        }
      }
      seatMaps.add(newMap);
    } while (flipped > 0);

    //debug
    printSeatMap(seatMaps.get(iteration));

    return String.valueOf(countOccupied(seatMaps.get(iteration)));
  }

  @Override
  public String part2(List<String> input) {
    List<char[][]> seatMaps = new ArrayList<>();
    seatMaps.add(processInput(input));
    int rowSize = input.size() + 2;
    int colSize = input.get(0).length() + 2;

    int flipped = 0;
    int iteration = 0;

    do {
      char[][] seatMap = seatMaps.get(iteration++);
      flipped = 0;
      char[][] newMap = getBlank(rowSize, colSize);
      for (int i = 1; i < seatMap.length - 1; i++) {
        for (int j = 1; j < seatMap[i].length - 1; j++) {
          int occupancy = checkNeighbors2(i, j, seatMap);
          boolean hasFlipped = flipOrCopy(seatMap, newMap, i, j, occupancy, 5);
          if (hasFlipped) {
            flipped++;
          }
        }
      }
      seatMaps.add(newMap);
    } while (flipped > 0);

    //debug
    // printSeatMap(seatMaps.get(iteration));

    return String.valueOf(countOccupied(seatMaps.get(iteration)));
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

  private int checkNeighbors2(int i, int j, char[][] seatMap) {
    int occupied = 0;

    for (Pair<Integer, Integer> direction : DIRECTIONS) {
      int x = i + direction.first();
      int y = j + direction.second();
      while (x > 0 && x < seatMap.length - 1 && y > 0 && y < seatMap[0].length - 1) {
        if (seatMap[x][y] == '#') {
          occupied++;
          break;
        } else if (seatMap[x][y] == 'L') {
          break;
        }
        x += direction.first();
        y += direction.second();
      }
    }

    return occupied;
  }

  private void printSeatMap(char[][] chars) {
    for (int i = 0; i < chars.length; i++) {
      System.out.println(chars[i]);
    }
  }

  private int checkNeighbors(int i, int j, char[][] seatMap) {
    int occupied = 0;

    for (int x = i - 1; x <= i + 1; x++) {
      for (int y = j - 1; y <= j + 1; y++) {
        if (!(x == i && y == j)) {
          if (seatMap[x][y] == '#') {
            occupied++;
          }
        }
      }
    }
    return occupied;
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

  private char[][] getBlank(int rowSize, int colSize) {
    char[][] paddedMap = new char[rowSize][colSize];
    return paddedMap;
  }
}
