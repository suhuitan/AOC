package solutions;

import static utils.Utils.splitByNewLine;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day08Test {

  private Day08 underTest;
  private String input = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | "
      + "fdgacbe cefdb cefbgd gcbe\n"
      + "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | "
      + "fcgedb cgb dgebacf gc\n"
      + "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | "
      + "cg cg fdcagb cbg\n"
      + "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | "
      + "efabcd cedba gadfec cb\n"
      + "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | "
      + "gecf egdcabf bgf bfgea\n"
      + "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | "
      + "gebdcfa ecba ca fadegcb\n"
      + "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | "
      + "cefg dcbef fcge gbcadfe\n"
      + "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | "
      + "ed bcgafe cdgba cbgef\n"
      + "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | "
      + "gbdfcae bgc cg cgb\n"
      + "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | "
      + "fgae cfgab fg bagce";

  @BeforeMethod
  public void setup() {
    underTest = new Day08();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "26");
  }

  @Test
  public void testPart21() {
    List<String> inputs = Collections.singletonList("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | "
        + "cdfeb fcadb cdfeb cdbaf");
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "5353");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "61229");
  }
}