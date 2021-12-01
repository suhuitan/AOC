package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day22Test {
  String test = "Player 1:\n"
      + "9\n"
      + "2\n"
      + "6\n"
      + "3\n"
      + "1\n"
      + "\n"
      + "Player 2:\n"
      + "5\n"
      + "8\n"
      + "4\n"
      + "7\n"
      + "10";

  String test2 = "Player 1:\n"
      + "43\n"
      + "19\n"
      + "\n"
      + "Player 2:\n"
      + "2\n"
      + "29\n"
      + "14";

  private List<String> inputs;

  private Day underTest;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));

    underTest = new Day22();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "306");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "291");
  }

  @Test
  public void testInfinitePart2() {
    try {
      String s = underTest.part2(Arrays.asList(test2.split("\n")));
    } catch (IndexOutOfBoundsException ex) {
      Assert.assertTrue(true);
    }
  }
}
