package solutions;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.Coordinate;
import utils.Day;

public class Day22 implements Day {

  Pattern p = Pattern.compile(
      "(?<action>\\w+) x=(?<xMin>-?\\d+)..(?<xMax>-?\\d+),y=(?<yMin>-?\\d+)..(?<yMax>-?\\d+),z=(?<zMin>-?\\d+)..(?<zMax>-?\\d+)");

  @Override
  public String part1(List<String> input) {
    List<Instruction> instructions = input.stream()
        .map(this::parseInput)
        .collect(Collectors.toList());

    Set<Coordinate> cubes = new HashSet<>();

    for (Instruction instruction : instructions) {
      if (instruction.withinInitializationProcedure()) {
        List<Coordinate> cuboid = instruction.getCubeCoordinates();
        switch (instruction.action) {
          case ON:
            cubes.addAll(cuboid);
            break;
          case OFF:
            cubes.removeAll(cuboid);
        }
      }
    }
    return String.valueOf(cubes.size());
  }

  private Instruction parseInput(String s) {
    Matcher matcher = p.matcher(s);
    if (matcher.matches()) {
      return new Instruction(matcher);
    }
    throw new IllegalArgumentException("not a match");
  }


  @Override
  public String part2(List<String> input) {
    List<Instruction> instructions = input.stream()
        .map(this::parseInput)
        .collect(Collectors.toList());

    List<Cube> cubes = new ArrayList<>();

    for (Instruction i : instructions) {
      Cube newCube = i.getCubeParams();
      for(Cube c : cubes) {
        c.remove(newCube);
      }
      if (i.action.equals(Instruction.ACTION.ON)) {
        cubes.add(newCube);
      }
    }

    Optional<BigInteger> reduce = cubes.stream()
        .map(Cube::volume)
        .map((c) -> BigInteger.valueOf(c))
        .reduce(BigInteger::add);

    return reduce.get().toString();
  }


  private static class Instruction {

    public enum ACTION {
      ON,
      OFF;
    }

    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;
    private final ACTION action;

    public Instruction(Matcher matcher) {
      action = ACTION.valueOf(matcher.group("action").toUpperCase());
      xMin = Integer.parseInt(matcher.group("xMin"));
      xMax = Integer.parseInt(matcher.group("xMax"));
      yMin = Integer.parseInt(matcher.group("yMin"));
      yMax = Integer.parseInt(matcher.group("yMax"));
      zMin = Integer.parseInt(matcher.group("zMin"));
      zMax = Integer.parseInt(matcher.group("zMax"));
    }

    List<Coordinate> getCubeCoordinates() {
      List<Coordinate> coordinates = new ArrayList<>();
      for (int x = xMin; x <= xMax; x++) {
        for (int y = yMin; y <= yMax; y++) {
          for (int z = zMin; z <= zMax; z++) {
            coordinates.add(new Coordinate(x, y, z));
          }
        }
      }
      Collections.sort(coordinates);
      return coordinates;
    }

    Cube getCubeParams() {
      return new Cube(xMin, xMax, yMin, yMax, zMin, zMax);
    }

    public boolean withinInitializationProcedure() {
      return xMin >= -50
          && xMax <= 50
          && yMin >= -50
          && yMax <= 50
          && zMin >= -50
          && zMax <= 50;
    }
  }

  private static class Cube {

    int xMin;
    int xMax;
    int yMin;
    int yMax;
    int zMin;
    int zMax;
    List<Cube> vacuums = new ArrayList<>();

    public Cube(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
      this.zMin = zMin;
      this.zMax = zMax;
    }

    public Cube(Integer xMin, Integer xMax, Integer yMin, Integer yMax, Integer zMin, Integer zMax) {
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
      this.zMin = zMin;
      this.zMax = zMax;
    }

    private Optional<Pair<Integer, Integer>> intersectRange(int aMin, int aMax, int bMin, int bMax) {
      if (bMin > aMax || aMin > bMax) {
        return Optional.empty();
      }

      List<Integer> sorted = Arrays.asList(aMin, aMax, bMin, bMax).stream()
          .sorted().collect(Collectors.toList());

      return Optional.of(Pair.of(sorted.get(1), sorted.get(2)));
    }

    private Optional<Cube> computeVacumn(Cube c) {
      Optional<Pair<Integer, Integer>> xBounds = intersectRange(this.xMin, this.xMax, c.xMin, c.xMax);
      Optional<Pair<Integer, Integer>> yBounds = intersectRange(this.yMin, this.yMax, c.yMin, c.yMax);
      Optional<Pair<Integer, Integer>> zBounds = intersectRange(this.zMin, this.zMax, c.zMin, c.zMax);

      if (xBounds.isEmpty() || yBounds.isEmpty() || zBounds.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(new Cube(xBounds.get().getLeft(), xBounds.get().getRight(), yBounds.get().getLeft(), yBounds.get().getRight(),
              zBounds.get().getLeft(), zBounds.get().getRight()));
    }

    public void remove(Cube c) {
      Optional<Cube> maybeVacumn = computeVacumn(c);
      if (maybeVacumn.isPresent()) {
        vacuums.forEach((cube) -> cube.remove(maybeVacumn.get()) );
        vacuums.add(maybeVacumn.get());
      }
    }

    public long volume() {
      return Integer.toUnsignedLong(xMax - xMin + 1) * Integer.toUnsignedLong(yMax - yMin + 1) * Integer.toUnsignedLong(zMax - zMin + 1) - vacuums.stream().map(Cube::volume).reduce(0L, Long::sum);
    }

  }
}
