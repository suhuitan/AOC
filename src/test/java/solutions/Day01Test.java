package solutions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day01Test {
  Day01 underTest;
  String input = "199\n"
      + "200\n"
      + "208\n"
      + "210\n"
      + "200\n"
      + "207\n"
      + "240\n"
      + "269\n"
      + "260\n"
      + "263";

  @BeforeMethod
  public void setup() {
    underTest = new Day01();
  }

  @Test
  public void testPart1() {
    List<String> inputs = parseInputString(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "7");
  }

  @Test
  public void testPart2() {
    List<String> inputs = parseInputString(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "5");
  }

  private List<String> parseInputString(String input) {
    List<String> inputs = Arrays.asList(input.split("\n"));
    return inputs;
  }
}