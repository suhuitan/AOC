package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day03Test {
  Day03 underTest;
  String input = "00100\n"
      + "11110\n"
      + "10110\n"
      + "10111\n"
      + "10101\n"
      + "01111\n"
      + "00111\n"
      + "11100\n"
      + "10000\n"
      + "11001\n"
      + "00010\n"
      + "01010";

  @BeforeMethod
  public void setup() {
    underTest = new Day03();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "198");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "230");
  }


}
