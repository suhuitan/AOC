package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day21Test {
  String test = "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n"
      + "trh fvjkl sbzzf mxmxvkd (contains dairy)\n"
      + "sqjhc fvjkl (contains soy)\n"
      + "sqjhc mxmxvkd sbzzf (contains fish)";

  private List<String> inputs;

  private Day underTest;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));
    underTest = new Day21();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "5");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "mxmxvkd,sqjhc,fvjkl");
  }
}
