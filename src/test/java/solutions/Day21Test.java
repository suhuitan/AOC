package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day21Test {

  private Day21 underTest;
  private String input = "Player 1 starting position: 4\n"
      + "Player 2 starting position: 8";

  @BeforeMethod
  public void setup() {
    underTest = new Day21();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "739785");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "444356092776315");
  }
}