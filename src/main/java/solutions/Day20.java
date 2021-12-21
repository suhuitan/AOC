package solutions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day20 implements Day {
  public static final List<Pair<Integer, Integer>> DIRECTIONS = Arrays
      .asList(
          Pair.of(-1, -1), Pair.of(-1, 0), Pair.of(-1, 1),
          Pair.of(0, -1), Pair.of(0, 0), Pair.of(0, 1),
          Pair.of(1, -1), Pair.of(1, 0), Pair.of(1, 1)
      );
  String algorithm = "";

  @Override
  public String part1(List<String> input) {
    algorithm = input.get(0).chars()
        .mapToObj((c) -> (char)c)
        .map((c) -> c == '#' ? "1" : "0")
        .collect(Collectors.joining());

    List<String> gridInput = input.subList(2, input.size());

    Pair<int[][], Integer> result = Pair.of(parse(gridInput), 0);

    for (int i = 0; i < 2; i++) {
      //print(result.getLeft());
      result = step(result.getLeft(), i % 2 == 0);
    }

    //print(result.getLeft());

    return String.valueOf(result.getRight());
  }

  private void print(int[][] image) {
    for (int[] x : image)
    {
      for (int y : x)
      {
        System.out.print(y + " ");
      }
      System.out.println();
    }
  }

  private Pair<int[][], Integer> step(int[][] image, boolean isEvenIteration) {
    int[][] nextImage = new int[image.length+2][image.length+2];
    for (int[] row : nextImage) {
      Arrays.fill(row, Character.getNumericValue(algorithm.charAt(0) == '1' ? (isEvenIteration ? '1' : '0') : '0'));
    }
    int numberOfLights = 0;

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image.length; j++) {
        // for each coordinate, f
        int index = getBinarySurrounding(i, j, image, isEvenIteration);
        if (algorithm.charAt(index) == '1') {
          numberOfLights++;
        }
        nextImage[i+1][j+1] = Character.getNumericValue(algorithm.charAt(index));
      }
    }

    return Pair.of(nextImage, numberOfLights);
  }

  private int getBinarySurrounding(int x, int y, int[][] image, boolean isEvenIteration) {
    StringBuilder b = new StringBuilder();

    for(Pair<Integer, Integer> coordinate : DIRECTIONS) {
      int i = x + coordinate.getLeft();
      int j = y + coordinate.getRight();
      if (i < 0 || j < 0 || i >= image.length || j>=image.length) {
        int def = Character.getNumericValue(algorithm.charAt(0) == '1' ? (isEvenIteration ? '0' : '1') : '0');
        b.append(def);
      } else {
        b.append(image[i][j]);
      }
    }
    return Integer.parseInt(b.toString(), 2);
  }


  private int[][] parse(List<String> input) {
    int[][] map = new int[input.size()+2][input.size()+2];
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(0).length(); j++) {
        if (input.get(i).charAt(j) == '#') {
          map[i+1][j+1] = 1;
        }
      }
    }
    return map;
  }

  @Override
  public String part2(List<String> input) {
    algorithm = input.get(0).chars()
        .mapToObj((c) -> (char)c)
        .map((c) -> c == '#' ? "1" : "0")
        .collect(Collectors.joining());

    List<String> gridInput = input.subList(2, input.size());

    Pair<int[][], Integer> result = Pair.of(parse(gridInput), 0);

    for (int i = 0; i < 50; i++) {
      result = step(result.getLeft(), i % 2 == 0);
    }

    return String.valueOf(result.getRight());
  }
}
