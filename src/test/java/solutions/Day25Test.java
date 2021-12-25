package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day25Test {

  private Day25 underTest;
  private String input = "v...>>.vv>\n"
      + ".vv>>.vv..\n"
      + ">>.>v>...v\n"
      + ">>v>>.>.v.\n"
      + "v>v.vv.v..\n"
      + ">.>>..v...\n"
      + ".vv..>.>v.\n"
      + "v.v..>>v.v\n"
      + "....v..v.>";
  private String input1 = "...>>>>>...";
  private String input2 = "..........\n"
      + ".>v....v..\n"
      + ".......>..\n"
      + "..........";
  private String input3 = "...>...\n"
      + ".......\n"
      + "......>\n"
      + "v.....>\n"
      + "......>\n"
      + ".......\n"
      + "..vvv..";

  @BeforeMethod
  public void setup() {
    underTest = new Day25();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "58");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, null);
  }
}