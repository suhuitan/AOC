package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.testng.internal.collections.Pair;

import utils.Day;

public class Day20 implements Day {

  Map<String, Long> stringCount;
  List<Tile> tiles;

  public static String nessie =
      "                  # \n"
          + "#    ##    ##    ###\n"
          + " #  #  #  #  #  #   ";

  @Override
  public String part1(List<String> input) {
    tiles = parseInput(input);
    stringCount = countEdgeValues(tiles);

    Long multipliedId = tiles.stream()
        .filter((tile) -> isCorner(tile))
        .mapToLong(Tile::getNumber)
        .reduce((l1, l2) -> l1 * l2).getAsLong();

    return String.valueOf(multipliedId);
  }

  public Map<String, Long> countEdgeValues(List<Tile> tiles) {
    return tiles.stream()
        .map(Tile::getAllStrings)
        .flatMap(List::stream)
        .collect(Collectors.groupingBy(String::valueOf, Collectors.counting()));
  }

  private boolean isCorner(Tile tile) {
    int count = 0;

   for (String strs :tile.getAllStrings()) {
     if(stringCount.get(strs) == 1L) {
       count++;
     }
   }

    return count > 2;
  }


  private List<Tile> parseInput(List<String> input) {
    int delimiterIndex = 0;
    List<Tile> tiles = new ArrayList<>();
    while (delimiterIndex < input.size()) {
      int num = Integer.parseInt(input.get(delimiterIndex).substring(5, input.get(delimiterIndex).indexOf(":")));
      List<String> tileData = input.subList(delimiterIndex + 1, delimiterIndex + 11);
      tiles.add(new Tile(num, tileData));
      delimiterIndex += 12;
    }

    return tiles;
  }

  @Override
  public String part2(List<String> input) {
    tiles = parseInput(input);
    stringCount = countEdgeValues(tiles);
    int size = (int) Math.sqrt(tiles.size());

    Tile[][] puzzle = solvePuzzle(size);

    char[][] imageData = extractImage(size, puzzle);

    int nessies = 0;

    // flip and rotate until we find monsters
    for (int i = 0, rotated = 0; i < 4 && rotated < 2; i++) {
      nessies = findNessies(imageData);
      if (nessies != 0) {
        break;
      }
      imageData = rotate(imageData);

      if (i == 3 && rotated == 0) {
        imageData = flip(imageData);
      }
    }

    int hashes = countHashes(imageData);
    // return hashes - nessie hashes
    return String.valueOf(hashes - (nessies * getNessieCoordinates().size()));
  }

  public char[][] extractImage(int size, Tile[][] puzzle) {
    char[][] imageData = new char[8 * size][8 * size];
    for (int tx = 0; tx < size; tx++) {
      for (int ty = 0; ty < size; ty++) {
        for (int x = 0; x < 8; x++) {
          for (int y = 0; y < 8; y++) {
            imageData[tx * 8 + x][ty * 8 + y] = puzzle[tx][ty].data[x + 1][y + 1];
          }
        }
      }
    }
    return imageData;
  }

  public int countHashes(char[][] imageData) {
    int hashes = 0;
    for (int i = 0; i < imageData.length; i++) {
      for (int j = 0; j < imageData.length; j++) {
        if (imageData[i][j] == '#') {
          hashes++;
        }
      }
    }
    return hashes;
  }

  public int findNessies(char[][] imageData) {
    for (int i = 0; i < imageData.length; i++) {
      System.out.println(imageData[i]);
    }
    List<Pair<Integer, Integer>> nessieCoordinates = getNessieCoordinates();
    int nessieCount = 0;
    for (int i = 0; i < imageData.length - 3; i++) {
      for (int j = 0; j < imageData.length - nessie.indexOf("\n"); j++) {
        boolean isNessie = true;
        for (Pair<Integer, Integer> coord : nessieCoordinates) {
          if (isNessie && imageData[i + coord.first()][j + coord.second()] != '#') {
            isNessie = false;
          }
        }

        if (isNessie) {
          nessieCount++;
        }

      }
    }
    return nessieCount;
  }

  public List<Pair<Integer, Integer>> getNessieCoordinates() {
    String[] nessieSplit = nessie.split("\n");
    List<Pair<Integer, Integer>> nessieCoords = new ArrayList<>();
    for (int i = 0; i < nessieSplit.length; i++) {
      for (int j = 0; j < nessieSplit[0].length(); j++) {
        if (nessieSplit[i].charAt(j) == '#') {
          nessieCoords.add(Pair.of(i, j));
        }
      }
    }
    return nessieCoords;
  }

  public static char[][] rotate(char[][] data) {
    char[][] buf = new char[data.length][data.length];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data.length; j++) {
        buf[i][j] = data[data.length - 1 - j][i];
      }
    }
    return buf;
  }

  public static char[][] flip(char[][] data) {
    char[][] buf = new char[data.length][data.length];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data.length; j++) {
        buf[i][j] = data[i][data.length - 1 - j];
      }
    }
    return buf;
  }

  public Tile[][] solvePuzzle(int size) {
    Tile[][] puzzle = new Tile[size][size];
    Tile topLeft = tiles.stream()
        .filter(((tile) -> stringCount.get(tile.getTop()) != 2 && stringCount.get(tile.getLeft()) != 2
            && stringCount.get(tile.getBottom()) == 2 && stringCount.get(tile.getRight()) == 2))
        .findFirst().get();
    puzzle[0][0] = topLeft;
    tiles.remove(topLeft);

    for (int i = 1; i < size; i++) {
      String rightTarget = puzzle[0][i - 1].getRight();
      for (Tile t : tiles) {
        if (t.getAllStrings().contains(rightTarget)) {
          Tile tile = findTileMatch(t, (p) -> p.getLeft().equals(rightTarget) && stringCount.get(p.getTop()) == 1);
          puzzle[0][i] = tile;
          tiles.remove(t);
          break;
        }
      }
    }

    for (int i = 1; i < size; i++) {
      String bottomTarget = puzzle[i - 1][0].getBottom();
      for (Tile t : tiles) {
        if (t.getAllStrings().contains(bottomTarget)) {
          Tile tile = findTileMatch(t, (p) -> p.getTop().equals(bottomTarget) && stringCount.get(p.getLeft()) == 1);

          puzzle[i][0] = tile;
          tiles.remove(t);
          break;
        }
      }
    }

    for (int i = 1; i < size; i++) {
      for (int j = 1; j < size; j++) {
        String bottomTarget = puzzle[i - 1][j].getBottom();
        String rightTarget = puzzle[i][j - 1].getRight();
        for (Tile t : tiles) {
          if (t.getAllStrings().contains(bottomTarget)) {

            Tile tile = findTileMatch(t, (p) -> p.getTop().equals(bottomTarget) && p.getLeft().equals(rightTarget));
            puzzle[i][j] = tile;
            tiles.remove(t);
            break;
          }
        }
      }
    }
    return puzzle;
  }

  public Tile findTileMatch(Tile t, Predicate<Tile> tilePredicate) {
    List<Tile> possibilities = Tile.getPossibleCombinations(t);
    Tile tile = possibilities.stream()
        .filter(tilePredicate)
        .findFirst().get();
    return tile;
  }

  private static class Tile {

    int number;
    char[][] data;

    public Tile(int number, List<String> data) {
      this.number = number;
      this.data = new char[data.get(0).length()][data.get(0).length()];
      for (int i = 0; i < data.get(0).length(); i++) {
        this.data[i] = data.get(i).toCharArray();
      }
    }

    public Tile(Tile tile, char[][] data) {
      this.number = tile.number;
      this.data = data;
    }

    public int getNumber() {
      return number;
    }

    public List<String> getAllStrings() {
      return Arrays.asList(getTop(), getRight(), getBottom(), getLeft()
          , getTopRvr(), getRightRvr(), getBottomRvr(),
          getLeftRvr());
    }

    public static List<Tile> getPossibleCombinations(Tile tile) {
      List<Tile> tiles = new ArrayList<>();

      char[][] data = tile.data;
      for (int i = 0; i < 4; i++) {
        data = rotate(data);
        tiles.add(new Tile(tile, data));
      }
      data = flip(data);
      for (int i = 0; i < 4; i++) {
        data = rotate(data);
        tiles.add(new Tile(tile, data));
      }
      return tiles;
    }

    public String getTop() {
      return String.copyValueOf(data[0]);
    }

    public String getTopRvr() {
      StringBuilder join = new StringBuilder();
      for (int i = data.length - 1; i >= 0; i--) {
        join.append(data[0][i]);
      }
      return join.toString();
    }

    public String getBottom() {
      return String.copyValueOf(data[data.length - 1]);
    }

    public String getBottomRvr() {
      StringBuilder join = new StringBuilder();
      for (int i = data.length - 1; i >= 0; i--) {
        join.append(data[data.length - 1][i]);
      }
      return join.toString();
    }

    public String getLeft() {
      StringBuilder join = new StringBuilder();
      for (int i = 0; i < data.length; i++) {
        join.append(data[i][0]);
      }
      return join.toString();
    }

    public String getLeftRvr() {
      StringBuilder join = new StringBuilder();
      for (int i = data.length - 1; i >= 0; i--) {
        join.append(data[i][0]);
      }
      return join.toString();
    }

    public String getRight() {
      StringBuilder join = new StringBuilder();
      for (int i = 0; i < data.length; i++) {
        join.append(data[i][data.length - 1]);
      }
      return join.toString();
    }

    public String getRightRvr() {
      StringBuilder join = new StringBuilder();
      for (int i = data.length - 1; i >= 0; i--) {
        join.append(data[i][data.length - 1]);
      }
      return join.toString();
    }

  }
}
