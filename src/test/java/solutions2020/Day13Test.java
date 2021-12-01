package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day13Test {

  String test =
      "939\n"
          + "7,13,x,x,59,x,31,19";
//  + "17,x,13,19";
//  + "67,7,59,61";
//  + "67,7,x,59,61";


  private List<String> inputs;

  private Day underTest;

  @BeforeMethod
  public void setup() {
    inputs = Arrays.asList(test.split("\n"));
    underTest = new Day13();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "295");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "1068781");
  }
}
