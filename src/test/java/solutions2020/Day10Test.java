package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day10Test {
  String test = "16\n"
      + "10\n"
      + "15\n"
      + "5\n"
      + "1\n"
      + "11\n"
      + "7\n"
      + "19\n"
      + "6\n"
      + "12\n"
      + "4";

  String test1 = "28\n"
      + "33\n"
      + "18\n"
      + "42\n"
      + "31\n"
      + "14\n"
      + "46\n"
      + "20\n"
      + "48\n"
      + "47\n"
      + "24\n"
      + "23\n"
      + "49\n"
      + "45\n"
      + "19\n"
      + "38\n"
      + "39\n"
      + "11\n"
      + "1\n"
      + "32\n"
      + "25\n"
      + "35\n"
      + "8\n"
      + "17\n"
      + "7\n"
      + "9\n"
      + "4\n"
      + "2\n"
      + "34\n"
      + "10\n"
      + "3";

  private List<String> inputs;

  private Day underTest;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));
    underTest = new Day10();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "35");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "8");
  }

  @Test
  public void testPart2Input2() {
    inputs = Arrays.asList(test1.split("\n"));
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "19208");
  }
}
