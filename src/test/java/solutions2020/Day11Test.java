package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day11Test {

  String test = "L.LL.LL.LL\n"
      + "LLLLLLL.LL\n"
      + "L.L.L..L..\n"
      + "LLLL.LL.LL\n"
      + "L.LL.LL.LL\n"
      + "L.LLLLL.LL\n"
      + "..L.L.....\n"
      + "LLLLLLLLLL\n"
      + "L.LLLLLL.L\n"
      + "L.LLLLL.LL";

  private List<String> inputs;

  private Day11 underTest;

  @BeforeMethod
  public void setup() {
    inputs = Arrays.asList(test.split("\n"));
    underTest = new Day11();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "37");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "26");
  }
}
