package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day13Test {

  private Day13 underTest;
  private String input = "6,10\n"
      + "0,14\n"
      + "9,10\n"
      + "0,3\n"
      + "10,4\n"
      + "4,11\n"
      + "6,0\n"
      + "6,12\n"
      + "4,1\n"
      + "0,13\n"
      + "10,12\n"
      + "3,4\n"
      + "3,0\n"
      + "8,4\n"
      + "1,10\n"
      + "2,14\n"
      + "8,10\n"
      + "9,0\n"
      + "\n"
      + "fold along y=7\n"
      + "fold along x=5";

  @BeforeMethod
  public void setup() {
    underTest = new Day13();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "17");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "16");
  }
}