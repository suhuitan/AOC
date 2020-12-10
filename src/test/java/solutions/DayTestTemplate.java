package solutions;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DayTestTemplate {
  String test = "";

  private List<String> inputs;

  private Day underTest;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));

  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, null);
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, null);
  }
}
