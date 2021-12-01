package solutions2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day14Test {

  String test =
      "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n"
          + "mem[8] = 11\n"
          + "mem[7] = 101\n"
          + "mem[8] = 0";

  String test2 =
      "mask = 000000000000000000000000000000X1001X\n"
          + "mem[42] = 100\n"
          + "mask = 00000000000000000000000000000000X0XX\n"
          + "mem[26] = 1";

  private Map<Integer, List<String>> inputs = new HashMap<>();

  private Day underTest;

  @BeforeMethod
  public void setup() {
    inputs.put(0, Arrays.asList(test.split("\n")));
    inputs.put(1, Arrays.asList(test2.split("\n")));
    underTest = new Day14();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs.get(0));
    Assert.assertEquals(s, "165");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs.get(1));
    Assert.assertEquals(s, "208");
  }
}
