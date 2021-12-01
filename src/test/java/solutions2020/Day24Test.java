package solutions2020;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day24Test {

  String test = "sesenwnenenewseeswwswswwnenewsewsw\n"
      + "neeenesenwnwwswnenewnwwsewnenwseswesw\n"
      + "seswneswswsenwwnwse\n"
      + "nwnwneseeswswnenewneswwnewseswneseene\n"
      + "swweswneswnenwsewnwneneseenw\n"
      + "eesenwseswswnenwswnwnwsewwnwsene\n"
      + "sewnenenenesenwsewnenwwwse\n"
      + "wenwwweseeeweswwwnwwe\n"
      + "wsweesenenewnwwnwsenewsenwwsesesenwne\n"
      + "neeswseenwwswnwswswnw\n"
      + "nenwswwsewswnenenewsenwsenwnesesenew\n"
      + "enewnwewneswsewnwswenweswnenwsenwsw\n"
      + "sweneswneswneneenwnewenewwneswswnese\n"
      + "swwesenesewenwneswnwwneseswwne\n"
      + "enesenwswwswneneswsenwnewswseenwsese\n"
      + "wnwnesenesenenwwnenwsewesewsesesew\n"
      + "nenewswnwewswnenesenwnesewesw\n"
      + "eneswnwswnwsenenwnwnwwseeswneewsenese\n"
      + "neswnwewnwnwseenwseesewsenwsweewe\n"
      + "wseweeenwnesenwwwswnew";

  private List<String> inputs;

  private Day underTest;

  @BeforeMethod
  public void setup() {
    inputs = Arrays.asList(test.split("\n"));
    underTest = new Day24();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs);
    Assert.assertEquals(s, "10");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs);
    Assert.assertEquals(s, "2208");
  }
}
