package solutions;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day25 implements Day {


  @Override
  public String part1(List<String> input) {
    char[][] initialMap = processInput(input);
    char[][] newMap = initialMap;

    for (int i = 1; i < 1000; i++) {
      Pair<char[][], Boolean> result = step(newMap);

      if (!result.getValue()) {
        return String.valueOf(i);
      }

      newMap = result.getKey();
      System.out.println("--- " + i);
    }
    return null;
  }

  private Pair<char[][], Boolean> step(char[][] map) {
    char[][] newMap = new char[map.length][map[0].length];
    boolean changed = false;
    for (char[] row : newMap) {
      Arrays.fill(row, '.');
    }

    for (int i = map.length - 1; i >= 0; i--) {
      for (int j = map[0].length - 1; j >= 0; j--) {
        if (map[i][j] == '>') {
          if (moveRight(map, i, j)) {
            setRight(newMap, i, j);
            changed = true;
          } else {
            newMap[i][j] = '>';
          }
        }
      }
    }

    for (int i = map.length - 1; i >= 0; i--) {
      for (int j = map[0].length - 1; j >= 0; j--) {
        if (map[i][j] == 'v') {
          if (moveDown(map, newMap, i, j)) {
            setDown(newMap, i, j);
            changed =true;
          } else {
            newMap[i][j] = 'v';
          }
        }
      }
    }
    return Pair.of(newMap, changed);
  }

  private boolean moveRight(char[][] map, int i, int j) {
    if (j + 1 == map[0].length) {
      return map[i][0] == '.';
    }

    return map[i][j + 1] == '.';
  }

  private void setRight(char[][] map, int i, int j) {
    if (j + 1 == map[0].length) {
      map[i][0] = '>';
      return;
    }

    map[i][j+1] = '>';
  }

  private boolean moveDown(char[][] map, char[][] newMap, int i, int j) {
    if (i + 1 == map.length) {
      return newMap[0][j] == '.' && map[0][j] != 'v';
    }

    return newMap[i + 1][j] == '.' && map[i+1][j] != 'v';
  }

  private void setDown(char[][] map, int i, int j) {
    if (i + 1 == map.length) {
      map[0][j] = 'v';
      return;
    }

    map[i + 1][j] = 'v';
  }

  private char[][] processInput(List<String> input) {
    char[][] map = new char[input.size()][input.get(0).length()];
    for (int i = 0; i < input.size(); i++) {
      char[] in = input.get(i).toCharArray();
      System.arraycopy(in, 0, map[i], 0, in.length);
    }
    return map;
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }
}
