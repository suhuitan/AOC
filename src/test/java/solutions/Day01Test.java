package solutions;

import java.util.Collections;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day01Test {
  Day01 underTest;

  @BeforeMethod
  public void setup() {
    underTest = new Day01();
  }

  @Test
  public void testPart1() {
    String result = underTest.part1(Collections.emptyList());
    Assert.assertEquals(result, null);
  }
}