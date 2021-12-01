package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day09Test {
  String test = "35\n"
      + "20\n"
      + "15\n"
      + "25\n"
      + "47\n"
      + "40\n"
      + "62\n"
      + "55\n"
      + "65\n"
      + "95\n"
      + "102\n"
      + "117\n"
      + "150\n"
      + "182\n"
      + "127\n"
      + "219\n"
      + "299\n"
      + "277\n"
      + "309\n"
      + "576";

  private List<String> inputs;
  private Day09 underTest;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));
    underTest = new Day09(5);

  }

  @Test
  public void testPart1() {
    String val = underTest.part1(inputs);
    Assert.assertEquals(val, "127");
  }

  @Test
  public void testPart2() {

    String val = underTest.part2(inputs);
    Assert.assertEquals(val, "62");
  }
}
