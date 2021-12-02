package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day02Test {
  Day02 underTest;
  String input = "forward 5\n"
      + "down 5\n"
      + "forward 8\n"
      + "up 3\n"
      + "down 8\n"
      + "forward 2";

  @BeforeMethod
  public void setup() {
    underTest = new Day02();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "150");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "900");
  }


}
