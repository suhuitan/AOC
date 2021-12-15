package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day14Test {

  private Day14 underTest;
  private String input = "NNCB\n"
      + "\n"
      + "CH -> B\n"
      + "HH -> N\n"
      + "CB -> H\n"
      + "NH -> C\n"
      + "HB -> C\n"
      + "HC -> B\n"
      + "HN -> C\n"
      + "NN -> C\n"
      + "BH -> H\n"
      + "NC -> B\n"
      + "NB -> B\n"
      + "BN -> B\n"
      + "BB -> N\n"
      + "BC -> B\n"
      + "CC -> N\n"
      + "CN -> C";

  @BeforeMethod
  public void setup() {
    underTest = new Day14();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "1588");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "2188189693529");
  }
}