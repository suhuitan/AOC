package solutions2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day16Test {

  String test1 =
      "class: 1-3 or 5-7\n"
          + "row: 6-11 or 33-44\n"
          + "seat: 13-40 or 45-50\n"
          + "\n"
          + "your ticket:\n"
          + "7,1,14\n"
          + "\n"
          + "nearby tickets:\n"
          + "7,3,47\n"
          + "40,4,50\n"
          + "55,2,20\n"
          + "38,6,12";

  private Map<Integer, List<String>> inputs = new HashMap<>();

  private Day underTest;

  @BeforeMethod
  public void setup() {
    inputs.put(1, Arrays.asList(test1.split("\n")));
    underTest = new Day16();
  }

  @Test
  public void testPart10() {
    String s = underTest.part1(inputs.get(1));
    Assert.assertEquals(s, "71");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs.get(1));
    Assert.assertEquals(s, "1");
  }
}
