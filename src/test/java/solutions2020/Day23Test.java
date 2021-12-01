package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day23Test {
  String test = "389125467";

  private List<String> inputs;

  private Day underTest;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));

    underTest = new Day23();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "67384529");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "149245887792");
  }

}
