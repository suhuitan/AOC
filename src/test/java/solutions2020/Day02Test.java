package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day02Test {
  List<String> input;
  Day02 underTest;

  @BeforeMethod
  public void setUp() {
    input = Arrays.asList("1-3 a: abcde",
    "1-3 b: cdefg",
    "2-9 c: ccccccccc");
    underTest = new Day02();
  }

  @Test
  public void isValid1(){
    Day02.Input input = new Day02.Input("2-3 t: ttttt");
    Assert.assertFalse(underTest.isValid1(input));
  }

  @Test
  public void part1(){
    String s = underTest.part1(input);
    Assert.assertEquals(s, "2");
  }

  @Test
  public void part2(){
    String s = underTest.part2(input);
    Assert.assertEquals(s, "1");
  }


}