package solutions;

import static utils.Utils.splitByNewLine;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day20Test {

  private Day20 underTest;
  private String input = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##"
      + "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###"
      + ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#."
      + ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....."
      + ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.."
      + "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....."
      + "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#\n"
      + "\n"
      + "#..#.\n"
      + "#....\n"
      + "##..#\n"
      + "..#..\n"
      + "..###";

  @BeforeMethod
  public void setup() {
    underTest = new Day20();
  }

  @Test
  public void testPart1() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "35");
  }

  @Test
  public void testPart1Flash() {
    List<String> inputs = splitByNewLine("####....#.....##.####..#.##.###.########.##.#..#.##.#...#..##.######..#......#..###.#.##.####.#.#.#....######.###...###.#.###.####..###.......#..#.#.#.#.#.####..####.#..####.#..####..##.#.#.#.###..##..#....#...###.#....###....##.###...##..#..#..#...##...#.#..#..###...####.#.#.###..#.#.#..###.##.##.#..###...#.#.#.##...#...#..#...##..###..###..#...###.#....#.##.#.####...##...##.#.####.#####.##...#######.###..##.#####.##.....####.#######.#.#.##....#...##...#..##.###.######.#######.#.#.#....#..##.###.#..##..##.\n"
        + "\n"
        + "#");
    String result = underTest.part1(inputs);

    Assert.assertEquals(result, "6");
  }

  @Test
  public void testPart2() {
    List<String> inputs = splitByNewLine(input);
    String result = underTest.part2(inputs);

    Assert.assertEquals(result, "3351");
  }
}