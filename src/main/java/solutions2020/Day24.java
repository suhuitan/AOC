package solutions2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.testng.internal.collections.Pair;

import utils.Day;

public class Day24 implements Day {

  @Override
  public String part1(List<String> input) {
    Set<Pair<Integer, Integer>> blackTiles = getBlackTilesFromInput(input);

    return String.valueOf(blackTiles.size());
  }

  public Set<Pair<Integer, Integer>> getBlackTilesFromInput(List<String> input) {
    List<Map<DIRECTION, Integer>> inputDirectionCounts = input.stream()
        .map(this::addDelimiter)
        .map((s) -> s.split(" "))
        .map((strs) -> countInstances(strs))
        .collect(Collectors.toList());

    Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> coordinates = inputDirectionCounts.stream()
        .map(this::sumDirectionValues)
        .collect(Collectors.groupingBy(Function.identity()));

    Set<Pair<Integer, Integer>> blackTiles = coordinates.values()
        .stream().filter((list) -> list.size() % 2 == 1)
        .map((list) -> list.get(0))
        .collect(Collectors.toSet());
    return blackTiles;
  }

  private Pair<Integer, Integer> sumDirectionValues(Map<DIRECTION, Integer> stringIntegerMap) {
    int x = 0;
    int y = 0;

    for (Map.Entry<DIRECTION, Integer> entry : stringIntegerMap.entrySet()) {
      DIRECTION direction = entry.getKey();
      int count = entry.getValue();
      x += count * direction.x;
      y += count * direction.y;

    }
    return new Pair<>(x, y);
  }


  private Map<DIRECTION, Integer> countInstances(String[] strs) {
    Map<DIRECTION, Integer> directionCounts = new HashMap<>();
    for (String str : strs) {
      directionCounts.compute(DIRECTION.valueOf(str.toUpperCase()), (k, v) -> v == null ? 1 : v + 1);
    }
    return directionCounts;
  }

  private String addDelimiter(String s) {
    s = s.replaceAll("w", "w ");
    s = s.replaceAll("e", "e ");
    return s;
  }

  @Override
  public String part2(List<String> input) {
    Set<Pair<Integer, Integer>> blackTiles = getBlackTilesFromInput(input);
    for (int cycles = 0; cycles < 100; cycles++) {
      blackTiles = cycleTiles(blackTiles);
    }
    return String.valueOf(blackTiles.size());
  }

  private Set<Pair<Integer, Integer>> cycleTiles(Set<Pair<Integer, Integer>> startingBlackTiles) {
    Set<Pair<Integer,Integer>> nextTiles = new HashSet<>();

    for(Pair<Integer, Integer> blackTile : startingBlackTiles) {
      long adjacentBlackTiles = checkNeighbors(blackTile, startingBlackTiles);
      if (adjacentBlackTiles == 1 || adjacentBlackTiles == 2) {
        nextTiles.add(blackTile);
      }
    }

    for(Pair<Integer, Integer> blackTile : startingBlackTiles) {
      List<Pair<Integer, Integer>> whiteNeighborTiles = getNeighbors(blackTile)
          .stream().filter((tile) -> !startingBlackTiles.contains(tile))
          .collect(Collectors.toList());

      for (Pair<Integer,Integer> neighborTile : whiteNeighborTiles) {
        long adjacentBlackTiles = checkNeighbors(neighborTile, startingBlackTiles);
        if (adjacentBlackTiles == 2) {
          nextTiles.add(neighborTile);
        }
      }
    }

    return nextTiles;
  }

  private long checkNeighbors(Pair<Integer, Integer> blackTile, Set<Pair<Integer, Integer>> startingBlackTiles) {
    List<Pair<Integer, Integer>> neighbors = getNeighbors(blackTile);
    return neighbors.stream().filter(startingBlackTiles::contains).count();
  }

  private List<Pair<Integer, Integer>> getNeighbors(Pair<Integer, Integer> blackTile) {
    List<Pair<Integer, Integer>> neighbors = Arrays.stream(DIRECTION.values())
        .map((dir) -> new Pair<Integer, Integer>(blackTile.first() + dir.x, blackTile.second() + dir.y))
        .collect(Collectors.toList());
    return neighbors;
  }

  public enum DIRECTION {
    NW(-1, 2),
    NE(1, 2),
    W(-2, 0),
    E(2, 0),
    SW(-1, -2),
    SE(1, -2);

    private final int x, y;

    DIRECTION(int x, int y) {
      this.x = x;
      this.y = y;
    }

  }
}
