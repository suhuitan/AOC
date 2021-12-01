package solutions2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day18Test {

  String test1 =
      "1 + 2 * 3 + 4 * 5 + 6\n"
          +
          "2 * 3 + (4 * 5)\n"
          +
          "5 + (8 * 3 + 9 + 3 * 4 * 3)\n"
          +
          "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))\n"
          +
          "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2";

  private Map<Integer, List<String>> inputs = new HashMap<>();

  private Day underTest;

  @BeforeMethod
  public void setup() {
    inputs.put(1, Arrays.asList(test1.split("\n")));
    underTest = new Day18();
  }

  @Test
  public void testPart10() {
    String s = underTest.part1(inputs.get(1));
    Assert.assertEquals(s, String.valueOf(71 + 26 + 437 + 12240 + 13632));
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs.get(1));
    Assert.assertEquals(s, String.valueOf(231 + 46 + 1445 + 669060 + 23340));
  }
}
