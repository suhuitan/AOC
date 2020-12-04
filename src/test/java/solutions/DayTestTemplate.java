package solutions;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DayTestTemplate {
  String test = "";

  private List<String> inputs;

  @BeforeMethod
  public void setup(){
    inputs = Arrays.asList(test.split("\n"));

  }

  @Test
  public void testPart1() {
  }

  @Test
  public void testPart2() {
  }
}
