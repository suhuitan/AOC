package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day12Test {

  private Day12 underTest;
  private String input = "start-A\n"
      + "start-b\n"
      + "A-c\n"
      + "A-b\n"
      + "b-d\n"
      + "A-end\n"
      + "b-end";

  private String input2 = "dc-end\n"
      + "HN-start\n"
      + "start-kj\n"
      + "dc-start\n"
      + "dc-HN\n"
      + "LN-dc\n"
      + "HN-end\n"
      + "kj-sa\n"
      + "kj-HN\n"
      + "kj-dc";

  private String input3 = "fs-end\n"
      + "he-DX\n"
      + "fs-he\n"
      + "start-DX\n"
      + "pj-DX\n"
      + "end-zg\n"
      + "zg-sl\n"
      + "zg-pj\n"
      + "pj-he\n"
      + "RW-he\n"
      + "fs-DX\n"
      + "pj-RW\n"
      + "zg-RW\n"
      + "start-pj\n"
      + "he-WI\n"
      + "zg-he\n"
      + "pj-fs\n"
      + "start-RW";

  @BeforeMethod
  public void setup() {
    underTest = new Day12();
  }

  @Test
  public void testPart1a() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "10");
  }

  @Test
  public void testPart1b() {
    List<String> inputs = splitByNewLine(input2);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "19");
  }

  @Test
  public void testPart1c() {
    List<String> inputs = splitByNewLine(input3);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "226");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "36");
  }
}