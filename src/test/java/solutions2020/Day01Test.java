package solutions2020;

import java.util.Arrays;
import java.util.List;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day01Test {
  Day underTest;

  @BeforeMethod
  public void setup() {
    underTest = new Day01();
  }

  @Test
  public void testPart1() {
    List<String> integers = Arrays.asList("1721",
        "979",
        "366",
        "299",
        "675",
        "1456");
    String output = underTest.part1(integers);
    Assert.assertEquals(output, "514579");
  }

  @Test
  public void testPart2() {
    List<String> integers = Arrays.asList("1721",
        "979",
        "366",
        "299",
        "675",
        "1456");
    String output = underTest.part2(integers);
    Assert.assertEquals(output, "241861950");
  }
}