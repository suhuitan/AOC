package solutions2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day17Test {

  String test1 =
            ".#.\n"
          + "..#\n"
          + "###";

  private Map<Integer, List<String>> inputs = new HashMap<>();

  private Day underTest;

  @BeforeMethod
  public void setup() {
    inputs.put(1, Arrays.asList(test1.split("\n")));
    underTest = new Day17();
  }

  @Test
  public void testPart10() {
    String s = underTest.part1(inputs.get(1));
    Assert.assertEquals(s, "112");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs.get(1));
    Assert.assertEquals(s, "848");
  }
}
