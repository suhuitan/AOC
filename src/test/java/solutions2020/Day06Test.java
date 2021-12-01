package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day06Test {

  String test = "abc\n"
      + "\n"
      + "a\n"
      + "b\n"
      + "c\n"
      + "\n"
      + "ab\n"
      + "ac\n"
      + "\n"
      + "a\n"
      + "a\n"
      + "a\n"
      + "a\n"
      + "\n"
      + "b"
      + "\n";

  private List<String> inputs;
  private Day06 underTest = new Day06();

  @BeforeMethod
  public void setup() {
    inputs = Arrays.asList(test.split("\n"));

  }

  @Test
  public void testPart1() {
    String max = underTest.part1(inputs);
    Assert.assertEquals(max, "11");
  }

  @Test
  public void testPart2() {
    String max = underTest.part2(inputs);

    Assert.assertEquals(max, "6");
  }
}