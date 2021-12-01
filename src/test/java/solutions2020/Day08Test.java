package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day08Test {

  String test = "nop +0\n"
      + "acc +1\n"
      + "jmp +4\n"
      + "acc +3\n"
      + "jmp -3\n"
      + "acc -99\n"
      + "acc +1\n"
      + "jmp -4\n"
      + "acc +6";

  private List<String> inputs;
  private Day08 underTest = new Day08();

  @BeforeMethod
  public void setup() {
    inputs = Arrays.asList(test.split("\n"));
  }

  @Test
  public void testPart1() {
    String max = underTest.part1(inputs);
    Assert.assertEquals(max, "5");
  }

  @Test
  public void testPart2() {
    String max = underTest.part2(inputs);
    Assert.assertEquals(max, "8");
  }
}