package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day11Test {

  private Day11 underTest;

  private String input = "11111\n"
      + "19991\n"
      + "19191\n"
      + "19991\n"
      + "11111\n";

  private String input2 = "5483143223\n"
      + "2745854711\n"
      + "5264556173\n"
      + "6141336146\n"
      + "6357385478\n"
      + "4167524645\n"
      + "2176841721\n"
      + "6882881134\n"
      + "4846848554\n"
      + "5283751526";

  @Test
  public void testPart1A() {
    underTest = new Day11(2);
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "9");
  }

  @Test
  public void testPart1B() {
    underTest = new Day11(10);
    List<String> inputs = splitByNewLine(input2);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "204");
  }

  @Test
  public void testPart1C() {
    underTest = new Day11();
    List<String> inputs = splitByNewLine(input2);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "1656");
  }


  @Test
  public void testPart2() {
    underTest = new Day11();
    List<String> inputs = splitByNewLine(input2);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "195");
  }
}