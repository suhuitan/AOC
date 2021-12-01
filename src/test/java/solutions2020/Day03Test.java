package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day03Test {

  String test = "..##.......\n"
      + "#...#...#..\n"
      + ".#....#..#.\n"
      + "..#.#...#.#\n"
      + ".#...##..#.\n"
      + "..#.##.....\n"
      + ".#.#.#....#\n"
      + ".#........#\n"
      + "#.##...#...\n"
      + "#...##....#\n"
      + ".#..#...#.#";

  Day03 underTest;

  private List<String> inputs;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));
    underTest = new Day03();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "7");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "336");
  }
}