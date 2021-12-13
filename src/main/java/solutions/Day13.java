package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import utils.Coordinate;
import utils.Day;

public class Day13 implements Day {
    Set<Coordinate> coordinates = new HashSet<>();
    List<Pair<String, Integer>> instructions = new ArrayList<>();

  @Override
  public String part1(List<String> input) {
    parseInput(input);

    Set<Coordinate> newCoordinates = fold(instructions.get(0).getLeft(), instructions.get(0).getRight(), coordinates);

    return String.valueOf(newCoordinates.size());
  }

  private Set<Coordinate> fold(String axis, int value, Set<Coordinate> originalCoordinates) {
    Set<Coordinate> newCoordinates = new HashSet<>();

    if (axis.equals("y")) {
      for(Coordinate c : originalCoordinates) {
        if (c.getY() <= value) {
          newCoordinates.add(c);
        } else {
          int newY = value - (c.getY() - value);
          newCoordinates.add(new Coordinate(c.getX(), newY));
        }
      }
    } else {
      for(Coordinate c : originalCoordinates) {
        if (c.getX() <= value) {
          newCoordinates.add(c);
        } else {
          int newX = value - (c.getX() - value);
          newCoordinates.add(new Coordinate(newX, c.getY()));
        }
      }
    }

    return newCoordinates;
  }

  private Pair<String, Integer> parseInstruction(String s) {
    String[] split = s.replace("fold along ", "").split("=");
    return Pair.of(split[0], Integer.parseInt(split[1]));
  }

  private void mark(boolean[][] map) {
    for(Coordinate c : coordinates) {
      map[c.getX()][c.getY()] = true;
    }
  }

  private void parseInput(List<String> input) {
    boolean part2 = false;
    for (String s : input) {
      if (s.isBlank()) {
        part2 = true;
        continue;
      }

      if (!part2) {
        String[] split = s.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        coordinates.add(new Coordinate(x, y));
      } else {
        instructions.add(parseInstruction(s));
      }
    }
  }

  @Override
  public String part2(List<String> input) {
    parseInput(input);

    Set<Coordinate> newCoordinates = new HashSet<>(coordinates);
    for(Pair<String, Integer> inst : instructions) {
      newCoordinates = fold(inst.getLeft(), inst.getRight(), newCoordinates);
    }

    int xMax = 0;
    int yMax = 0;
    for (Coordinate c : newCoordinates) {
      xMax = Math.max(xMax, c.getX());
      yMax = Math.max(yMax, c.getY());
    }

    char[][] map = new char[yMax+1][xMax+1];

    for (int i = 0; i < xMax+1; i++) {
      for (int j = 0; j < yMax+1; j++) {
        if (newCoordinates.contains(new Coordinate(i, j))) {
          map[j][i] = '#';
        } else {
          map[j][i] = '.';
        }
      }
    }
    for(char[] c : map) {
      System.out.println(c);
    }
    return String.valueOf(newCoordinates.size());
  }
}
