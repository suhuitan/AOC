package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day05Test {

  private Day05 underTest;
  private String input = "0,9 -> 5,9\n"
      + "8,0 -> 0,8\n"
      + "9,4 -> 3,4\n"
      + "2,2 -> 2,1\n"
      + "7,0 -> 7,4\n"
      + "6,4 -> 2,0\n"
      + "0,9 -> 2,9\n"
      + "3,4 -> 1,4\n"
      + "0,0 -> 8,8\n"
      + "5,5 -> 8,2\n";

  @BeforeMethod
  public void setup() {
    underTest = new Day05();
  }

  @Test
  public void testFindVerticalAndHorizontalSegmentsAndCoordinates() {
    List<Day05.LineSegment> segments = underTest.findVerticalAndHorizontalLineSegments(splitByNewLine(input));
    Assert.assertEquals(segments.size(), 6);
  }

  @Test
  public void testGetCoordinates() {
    Day05.LineSegment horizontal = new Day05.LineSegment("1,1", "1,3");
    List<Pair<Integer, Integer>> coordinates1 = horizontal.getAllCoordinates();
    Assert.assertEquals(coordinates1.size(), 3);
    Assert.assertEquals(coordinates1, List.of(Pair.of(1,1), Pair.of(1,2), Pair.of(1,3)));

    Day05.LineSegment vertical = new Day05.LineSegment("7,7", "9,7");
    List<Pair<Integer, Integer>> coordinates2 = vertical.getAllCoordinates();
    Assert.assertEquals(coordinates2.size(), 3);
    Assert.assertEquals(coordinates2, List.of(Pair.of(7,7), Pair.of(8,7), Pair.of(9,7)));

    Day05.LineSegment diagonalLeft = new Day05.LineSegment("7,9", "9,7");
    List<Pair<Integer, Integer>> coordinates3 = diagonalLeft.getAllCoordinates();
    Assert.assertEquals(coordinates3.size(), 3);
    Assert.assertEquals(coordinates3, List.of(Pair.of(7,9), Pair.of(8,8), Pair.of(9,7)));

    Day05.LineSegment diagonalRight = new Day05.LineSegment("1,1", "3,3");
    List<Pair<Integer, Integer>> coordinates4 = diagonalRight.getAllCoordinates();
    Assert.assertEquals(coordinates4.size(), 3);
    Assert.assertEquals(coordinates4, List.of(Pair.of(1,1), Pair.of(2,2), Pair.of(3,3)));

    Day05.LineSegment diagonalAgain = new Day05.LineSegment("2,0", "6,4");
    List<Pair<Integer, Integer>> coordinates5 = diagonalAgain.getAllCoordinates();
    Assert.assertEquals(coordinates5.size(), 5);

  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "5");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "12");
  }
}