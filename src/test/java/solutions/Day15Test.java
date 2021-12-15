package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day15Test {

  private Day15 underTest;
  private String input = "1163751742\n"
      + "1381373672\n"
      + "2136511328\n"
      + "3694931569\n"
      + "7463417111\n"
      + "1319128137\n"
      + "1359912421\n"
      + "3125421639\n"
      + "1293138521\n"
      + "2311944581";

  @BeforeMethod
  public void setup() {
    underTest = new Day15();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "40");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "315");
  }
}