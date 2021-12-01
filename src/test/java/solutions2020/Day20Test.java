package solutions2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Day;

public class Day20Test {
  String test = "Tile 2311:\n"
      + "..##.#..#.\n"
      + "##..#.....\n"
      + "#...##..#.\n"
      + "####.#...#\n"
      + "##.##.###.\n"
      + "##...#.###\n"
      + ".#.#.#..##\n"
      + "..#....#..\n"
      + "###...#.#.\n"
      + "..###..###\n"
      + "\n"
      + "Tile 1951:\n"
      + "#.##...##.\n"
      + "#.####...#\n"
      + ".....#..##\n"
      + "#...######\n"
      + ".##.#....#\n"
      + ".###.#####\n"
      + "###.##.##.\n"
      + ".###....#.\n"
      + "..#.#..#.#\n"
      + "#...##.#..\n"
      + "\n"
      + "Tile 1171:\n"
      + "####...##.\n"
      + "#..##.#..#\n"
      + "##.#..#.#.\n"
      + ".###.####.\n"
      + "..###.####\n"
      + ".##....##.\n"
      + ".#...####.\n"
      + "#.##.####.\n"
      + "####..#...\n"
      + ".....##...\n"
      + "\n"
      + "Tile 1427:\n"
      + "###.##.#..\n"
      + ".#..#.##..\n"
      + ".#.##.#..#\n"
      + "#.#.#.##.#\n"
      + "....#...##\n"
      + "...##..##.\n"
      + "...#.#####\n"
      + ".#.####.#.\n"
      + "..#..###.#\n"
      + "..##.#..#.\n"
      + "\n"
      + "Tile 1489:\n"
      + "##.#.#....\n"
      + "..##...#..\n"
      + ".##..##...\n"
      + "..#...#...\n"
      + "#####...#.\n"
      + "#..#.#.#.#\n"
      + "...#.#.#..\n"
      + "##.#...##.\n"
      + "..##.##.##\n"
      + "###.##.#..\n"
      + "\n"
      + "Tile 2473:\n"
      + "#....####.\n"
      + "#..#.##...\n"
      + "#.##..#...\n"
      + "######.#.#\n"
      + ".#...#.#.#\n"
      + ".#########\n"
      + ".###.#..#.\n"
      + "########.#\n"
      + "##...##.#.\n"
      + "..###.#.#.\n"
      + "\n"
      + "Tile 2971:\n"
      + "..#.#....#\n"
      + "#...###...\n"
      + "#.#.###...\n"
      + "##.##..#..\n"
      + ".#####..##\n"
      + ".#..####.#\n"
      + "#..#.#..#.\n"
      + "..####.###\n"
      + "..#.#.###.\n"
      + "...#.#.#.#\n"
      + "\n"
      + "Tile 2729:\n"
      + "...#.#.#.#\n"
      + "####.#....\n"
      + "..#.#.....\n"
      + "....#..#.#\n"
      + ".##..##.#.\n"
      + ".#.####...\n"
      + "####.#.#..\n"
      + "##.####...\n"
      + "##..#.##..\n"
      + "#.##...##.\n"
      + "\n"
      + "Tile 3079:\n"
      + "#.#.#####.\n"
      + ".#..######\n"
      + "..#.......\n"
      + "######....\n"
      + "####.#..#.\n"
      + ".#...#.##.\n"
      + "#.#####.##\n"
      + "..#.###...\n"
      + "..#.......\n"
      + "..#.###...";

  private String test2 ="";

  private String test3 = "";

  private Map<Integer, List<String>> inputs = new HashMap<>();

  private Day underTest;

  @BeforeMethod
  public void setup(){
    inputs.put(1, Arrays.asList(test.split("\n")));
    inputs.put(2, Arrays.asList(test2.split("\n")));
    inputs.put(3, Arrays.asList(test3.split("\n")));
    underTest = new Day20();
  }

  @Test
  public void testPart1() {
    String s = underTest.part1(inputs.get(1));
    Assert.assertEquals(s, "20899048083289");
  }

  @Test
  public void testPart2() {
    String s = underTest.part2(inputs.get(1));
    Assert.assertEquals(s, "273");
  }
}
