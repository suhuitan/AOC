package solutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day05 implements Day {

  @Override
  public String part1(List<String> input) {
    List<LineSegment> lineSegments = findVerticalAndHorizontalLineSegments(input);

    Map<Pair<Integer, Integer>, Long> countsOfEachCoordinate = lineSegments.stream()
        .map(LineSegment::getAllCoordinates)
        .flatMap(Collection::stream)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    long numberOfCoordinatesWithMoreThan1LineCrossing = countsOfEachCoordinate.values()
        .stream()
        .filter((value) -> value > 1)
        .count();

    return String.valueOf(numberOfCoordinatesWithMoreThan1LineCrossing);
  }

  List<LineSegment> findVerticalAndHorizontalLineSegments(List<String> input) {
    return input.stream()
        .map(this::toLineSegment)
        .filter(LineSegment::isHorizontalOrVertical)
        .collect(Collectors.toList());
  }

  private LineSegment toLineSegment(String s) {
    String[] split = s.split(" -> ");
    return new LineSegment(split[0], split[1]);
  }

  @Override
  public String part2(List<String> input) {
    List<LineSegment> lineSegments = input.stream()
        .map(this::toLineSegment)
        .collect(Collectors.toList());

    Map<Pair<Integer, Integer>, Long> countsOfEachCoordinate = lineSegments.stream()
        .map(LineSegment::getAllCoordinates)
        .flatMap(Collection::stream)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    long numberOfCoordinatesWithMoreThan1LineCrossing = countsOfEachCoordinate.values()
        .stream()
        .filter((value) -> value > 1)
        .count();

    return String.valueOf(numberOfCoordinatesWithMoreThan1LineCrossing);
  }

  static class LineSegment {
    Pair<Integer, Integer> startCoord;
    Pair<Integer, Integer> endCoord;

    public LineSegment(String start, String end) {
      int x1 = Integer.parseInt(start.substring(0, start.indexOf(',')));
      int y1 = Integer.parseInt(start.substring(start.indexOf(',') + 1));
      int x2 = Integer.parseInt(end.substring(0, end.indexOf(',')));
      int y2 = Integer.parseInt(end.substring(end.indexOf(',') + 1));

      Pair<Integer, Integer> c1 = Pair.of(x1, y1);
      Pair<Integer, Integer> c2 = Pair.of(x2, y2);

      // presort so it is always going in increasing order
      startCoord = c1.compareTo(c2) < 0 ? c1 : c2;
      endCoord = c1.compareTo(c2) < 0 ? c2: c1;
    }

    List<Pair<Integer, Integer>> getAllCoordinates(){
      List<Pair<Integer, Integer>> coordinates = new ArrayList<>();
      if (this.isHorizontalOrVertical()) {
        for (int i = startCoord.getLeft(); i <= endCoord.getLeft(); i++) {
          for (int j = startCoord.getRight(); j <= endCoord.getRight(); j++) {
            coordinates.add(Pair.of(i, j));
          }
        }
      }
      // diagonal towards right
      else if( endCoord.getLeft() > startCoord.getLeft() && endCoord.getRight() > startCoord.getRight()) {
        for (int i = startCoord.getLeft(), j = startCoord.getRight(); i <= endCoord.getLeft() && j <= endCoord.getRight(); i++, j++) {
          coordinates.add(Pair.of(i, j));
        }
      }
      // diagonal towards left
      else {
        for (int i = startCoord.getLeft(), j = startCoord.getRight(); i <= endCoord.getLeft() && j >= endCoord.getRight(); i++, j--) {
          coordinates.add(Pair.of(i, j));
        }
      }
      return coordinates;
    }

    public boolean isHorizontalOrVertical() {
      return startCoord.getLeft().equals(endCoord.getLeft()) || startCoord.getRight().equals(endCoord.getRight());
    }
  }
}
