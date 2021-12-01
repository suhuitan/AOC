package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import utils.Day;


public class Day17 implements Day {

  private static int[][] coordinates
      = {
      {-1, -1, -1}, {-1, -1, 0}, {-1, -1, 1},
      {-1, 0, -1}, {-1, 0, 0}, {-1, 0, 1},
      {-1, 1, -1}, {-1, 1, 0}, {-1, 1, 1},

      {0, -1, -1}, {0, -1, 0}, {0, -1, 1},
      {0, 0, -1}, {0, 0, 1},
      {0, 1, -1}, {0, 1, 0}, {0, 1, 1},

      {1, -1, -1}, {1, -1, 0}, {1, -1, 1},
      {1, 0, -1}, {1, 0, 0}, {1, 0, 1},
      {1, 1, -1}, {1, 1, 0}, {1, 1, 1},
  };

  // something something brain broken couldn't extend the above.
  private static int[][] hypercubeCoordinates
      = {
      {-1, -1, -1, -1}, {-1, -1, -1, 0}, {-1, -1, -1, 1},
      {-1, -1, 0, -1}, {-1, -1, 0, 0}, {-1, -1, 0, 1},
      {-1, -1, 1, -1}, {-1, -1, 1, 0}, {-1, -1, 1, 1},

      {-1, 0, -1, -1}, {-1, 0, -1, 0}, {-1, 0, -1, 1},
      {-1, 0, 0, -1}, {-1, 0, 0, 0}, {-1, 0, 0, 1},
      {-1, 0, 1, -1}, {-1, 0, 1, 0}, {-1, 0, 1, 1},

      {-1, 1, -1, -1}, {-1, 1, -1, 0}, {-1, 1, -1, 1},
      {-1, 1, 0, -1}, {-1, 1, 0, 0}, {-1, 1, 0, 1},
      {-1, 1, 1, -1}, {-1, 1, 1, 0}, {-1, 1, 1, 1},

      {0, -1, -1, -1}, {0, -1, -1, 0}, {0, -1, -1, 1},
      {0, -1, 0, -1}, {0, -1, 0, 0}, {0, -1, 0, 1},
      {0, -1, 1, -1}, {0, -1, 1, 0}, {0, -1, 1, 1},

      {0, 0, -1, -1}, {0, 0, -1, 0}, {0, 0, -1, 1},
      {0, 0, 0, -1}, {0, 0, 0, 1},
      {0, 0, 1, -1}, {0, 0, 1, 0}, {0, 0, 1, 1},

      {0, 1, -1, -1}, {0, 1, -1, 0}, {0, 1, -1, 1},
      {0, 1, 0, -1}, {0, 1, 0, 0}, {0, 1, 0, 1},
      {0, 1, 1, -1}, {0, 1, 1, 0}, {0, 1, 1, 1},

      {1, -1, -1, -1}, {1, -1, -1, 0}, {1, -1, -1, 1},
      {1, -1, 0, -1}, {1, -1, 0, 0}, {1, -1, 0, 1},
      {1, -1, 1, -1}, {1, -1, 1, 0}, {1, -1, 1, 1},

      {1, 0, -1, -1}, {1, 0, -1, 0}, {1, 0, -1, 1},
      {1, 0, 0, -1}, {1, 0, 0, 0}, {1, 0, 0, 1},
      {1, 0, 1, -1}, {1, 0, 1, 0}, {1, 0, 1, 1},

      {1, 1, -1, -1}, {1, 1, -1, 0}, {1, 1, -1, 1},
      {1, 1, 0, -1}, {1, 1, 0, 0}, {1, 1, 0, 1},
      {1, 1, 1, -1}, {1, 1, 1, 0}, {1, 1, 1, 1},
  };


  @Override
  public String part1(List<String> input) {
    Set<Cube> cubes = readCubes(input);

    for (int cycles = 0; cycles < 6; cycles++) {
      cubes = cycleCubes(cubes);
    }

    return String.valueOf(cubes.size());
  }

  public Set<Cube> readCubes(List<String> input) {
    Set<Cube> initialCubes = new HashSet<>();
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(0).length(); j++) {
        if (input.get(i).charAt(j) == '#') {
          initialCubes.add(new RegularCube(i, j, 0));
        }
      }
    }
    return initialCubes;
  }

  private Set<Cube> cycleCubes(Set<Cube> startingCubes) {
    Set<Cube> nextActives = new HashSet<>();

    for (Cube cube : startingCubes) {
      long activeNeighbors = getActiveNeighbors(startingCubes, cube);
      if (activeNeighbors == 2 || activeNeighbors == 3) {
        nextActives.add(cube);
      }
    }

    for (Cube cube : startingCubes) {
      List<Cube> neighbors = cube.getNeighbors()
          .stream()
          .filter((neighbor) -> !startingCubes.contains(neighbor))
          .collect(Collectors.toList());
      for (Cube neighbor : neighbors) {
        long activeNeighbors = getActiveNeighbors(startingCubes, neighbor);
        if (activeNeighbors == 3) {
          nextActives.add(neighbor);
        }
      }
    }
    return nextActives;
  }

  private long getActiveNeighbors(Set<Cube> startingCubes, Cube cube) {
    List<Cube> neighbors = cube.getNeighbors();
    long activeNeighbors = neighbors.stream()
        .filter(startingCubes::contains)
        .count();
    return activeNeighbors;
  }

  @Override
  public String part2(List<String> input) {
    Set<Cube> cubes = readHyperCube(input);

    for (int cycles = 0; cycles < 6; cycles++) {
      cubes = cycleCubes(cubes);
    }

    return String.valueOf(cubes.size());
  }

  public Set<Cube> readHyperCube(List<String> input) {
    Set<Cube> initialCubes = new HashSet<>();
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(0).length(); j++) {
        if (input.get(i).charAt(j) == '#') {
          initialCubes.add(new HyperCube(0, 0, i, j));
        }
      }
    }
    return initialCubes;
  }

  public interface Cube {
    List<Cube> getNeighbors();
  }

  private static class HyperCube extends RegularCube implements Cube {

    int w;

    public HyperCube(int h, int i, int j, int k) {
      super(i, j, k);
      w = h;
    }

    public List<Cube> getNeighbors() {
      List<Cube> total = new ArrayList<>();

      List<Cube> hyperCubes = Arrays.stream(hypercubeCoordinates)
          .map((coord) -> new HyperCube(w + coord[0], x + coord[1], y + coord[2], z + coord[3]))
          .collect(Collectors.toList());
      total.addAll(hyperCubes);

      return total;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o != null && getClass() != o.getClass()) {
        return false;
      }
      HyperCube cube = (HyperCube) o;
      return w == cube.w &&
          super.equals(cube);
    }

    @Override
    public int hashCode() {
      return Objects.hash(z) + super.hashCode();
    }
  }

  private static class RegularCube implements Cube {

    int x, y, z;

    public RegularCube(int i, int j, int k) {
      x = i;
      y = j;
      z = k;
    }

    public List<Cube> getNeighbors() {
      return Arrays.stream(coordinates)
          .map((coord) -> new RegularCube(x + coord[0], y + coord[1], z + coord[2]))
          .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o != null && o.getClass() != getClass()) {
        return false;
      }
      RegularCube cube = (RegularCube) o;
      return x == cube.x &&
          y == cube.y &&
          z == cube.z;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y, z);
    }
  }
}
