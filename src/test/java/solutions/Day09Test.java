package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day09Test {

  private Day09 underTest;
  private String input = "2199943210\n"
      + "3987894921\n"
      + "9856789892\n"
      + "8767896789\n"
      + "9899965678\n";

  @BeforeMethod
  public void setup() {
    underTest = new Day09();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "15");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "1134");
  }
}