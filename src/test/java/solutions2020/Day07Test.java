package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day07Test {

  String test = "light red bags contain 1 bright white bag, 2 muted yellow bags.\n"
      + "dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n"
      + "bright white bags contain 1 shiny gold bag.\n"
      + "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n"
      + "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n"
      + "dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n"
      + "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n"
      + "faded blue bags contain no other bags.\n"
      + "dotted black bags contain no other bags.";

  private List<String> inputs;
  private Day07 underTest = new Day07();

  @BeforeMethod
  public void setup() {
    inputs = Arrays.asList(test.split("\n"));
  }

  @Test
  public void testPart1() {
    String max = underTest.part1(inputs);
    Assert.assertEquals(max, "4");
  }

  @Test
  public void testPart2() {
    String max = underTest.part2(inputs);
    Assert.assertEquals(max, "32");
  }
}