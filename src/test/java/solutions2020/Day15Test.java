package solutions2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day15Test {

  String test1 =
      "0,3,6";
  String test2 =
      "1,3,2";
  String test3 =
      "2,1,3";
  String test4 =
      "1,2,3";
  String test5 =
      "2,3,1";
  String test6 =
      "3,2,1";
  String test7 =
      "3,1,2";

  private Map<Integer, List<String>> inputs = new HashMap<>();

  private Day underTest;

  @BeforeMethod
  public void setup() {
    inputs.put(1, Arrays.asList(test1.split("\n")));
    inputs.put(2, Arrays.asList(test2.split("\n")));
    inputs.put(3, Arrays.asList(test3.split("\n")));
    inputs.put(4, Arrays.asList(test4.split("\n")));
    inputs.put(5, Arrays.asList(test5.split("\n")));
    inputs.put(6, Arrays.asList(test6.split("\n")));
    inputs.put(7, Arrays.asList(test7.split("\n")));
    underTest = new Day15();
  }

  @Test
  public void testPart10() {
    String s = underTest.part1(inputs.get(1));
    Assert.assertEquals(s, "436");
  }
  @Test
  public void testPart11() {
    String s = underTest.part1(inputs.get(2));
    Assert.assertEquals(s, "1");
  }
  @Test
  public void testPart12() {
    String s = underTest.part1(inputs.get(3));
    Assert.assertEquals(s, "10");
  }
  @Test
  public void testPart13() {
    String s = underTest.part1(inputs.get(4));
    Assert.assertEquals(s, "27");
  }
  @Test
  public void testPart14() {
    String s = underTest.part1(inputs.get(5));
    Assert.assertEquals(s, "78");

  }
  @Test
  public void testPart15() {
    String s = underTest.part1(inputs.get(6));
    Assert.assertEquals(s, "438");

  }
  @Test
  public void testPart16() {
    String s = underTest.part1(inputs.get(7));
    Assert.assertEquals(s, "1836");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs.get(1));
    Assert.assertEquals(s, "175594");
  }
}
