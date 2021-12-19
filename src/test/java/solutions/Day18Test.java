package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Day18Test {

  private Day18 underTest;
  private String input = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]\n"
      + "[[[5,[2,8]],4],[5,[[9,9],0]]]\n"
      + "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]\n"
      + "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]\n"
      + "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]\n"
      + "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]\n"
      + "[[[[5,4],[7,7]],8],[[8,3],8]]\n"
      + "[[9,3],[[9,9],[6,[4,9]]]]\n"
      + "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]\n"
      + "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]";

  @BeforeMethod
  public void setup() {
    underTest = new Day18();
  }

  @Test(dataProvider = "provideExplodeStrings")
  public void testExplode(String inputStr, String expectedStr) {
    //    List<String> input = underTest.parseInput(inputStr);
    Assert.assertEquals(Pair.of(expectedStr, true), underTest.explode(inputStr));
  }

  @DataProvider(name = "provideExplodeStrings")
  public Object[][] explodeStrings() {

    return new Object[][] {
        {"[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]"},
        {"[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]"},
        {"[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]"},
        {"[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"},
        {"[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"}
    };
  }

  @Test(dataProvider = "provideSplitStrings")
  public void testSplit(String inputStr, String expectedStr) {
    Assert.assertEquals(Pair.of(expectedStr, true), underTest.split(inputStr));
  }

  @DataProvider(name = "provideSplitStrings")
  public Object[][] splitStrings() {
    return new Object[][] {
        {"[[[0,7],4],[15,[0,13]]],[1,1]]", "[[[0,7],4],[[7,8],[0,13]]],[1,1]]"},
        {"[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]"}
    };
  }

  @Test
  public void testAdd() {
    //    String result = underTest.add("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]");
    //    Assert.assertEquals(result,"[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");

    List<String> inputs = splitByNewLine("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]\n"
        + "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]\n"
        + "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]\n"
        + "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]\n"
        + "[7,[5,[[3,8],[1,4]]]]\n"
        + "[[2,[2,2]],[8,[8,1]]]\n"
        + "[2,9]\n"
        + "[1,[[[9,3],9],[[9,0],[0,7]]]]\n"
        + "[[[5,[7,4]],7],1]\n"
        + "[[[[4,2],2],6],[8,7]]"
    );

    String s = inputs.get(0);
    for (int i = 1; i < inputs.size(); i++) {
      s = underTest.add(s, inputs.get(i));

    }
    Assert.assertEquals(s, "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");

  }

  @DataProvider(name = "provideMagnitude")
  public Object[][] magnitudeStrings() {
    return new Object[][] {
        {"[[1,2],[[3,4],5]]", "143"},
        {"[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", "1384"},
        {"[[[[1,1],[2,2]],[3,3]],[4,4]]", "445"},
        {"[[[[3,0],[5,3]],[4,4]],[5,5]]", "791"},
        {"[[[[5,0],[7,4]],[5,5]],[6,6]]", "1137"},
        {"[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", "3488"}
    };
  }


  @Test(dataProvider = "provideMagnitude")
  public void testMags(String input, String output) {
    Assert.assertEquals(output, String.valueOf(underTest.magnitude(input)));
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "4140");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "3993");
  }
}