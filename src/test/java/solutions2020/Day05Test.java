package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day05Test {
  String test = "BFFFBBFRRR\n"+
      "FFFBBBFRRR\n"+
      "BBFFBBFRLL\n";

  private List<String> inputs;
  private Day05 underTest = new Day05();

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));
  }

  @Test
  public void testTargetFinder() {
    int target = underTest.findTarget("FBFBBFF", 0, 127);
    Assert.assertEquals(target, 44);

    int target2 = underTest.findTarget("RLR", 0, 7);
    Assert.assertEquals(target2, 5);
  }

  @Test
  public void testPart1() {
    String max = underTest.part1(inputs);
    Assert.assertEquals(max, "820");
  }


}
