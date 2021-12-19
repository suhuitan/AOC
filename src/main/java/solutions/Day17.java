package solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Coordinate;
import utils.Day;

public class Day17 implements Day {

  @Override
  public String part1(List<String> input) {
    Target target = new Target(input.get(0));
    List<Coordinate> coordinates = new ArrayList<>();
    int maxY = 0;

    for (int i = 1; i < 200; i++) {
      for (int j = 0; j < 200; j++) {
        int currX = 0;
        int currY = 0;
        int xVelocity = i;
        int yVelocity = j;
        int localMaxY = 0;
        while(!target.pastTarget(currX, currY, currX + xVelocity, currY +yVelocity)) {
          currX += xVelocity;
          currY += yVelocity;
          xVelocity = xVelocity == 0 ? 0 : xVelocity - 1;
          yVelocity -= 1;
          localMaxY = Math.max(localMaxY, currY);

          if (target.inTarget(currX, currY)) {
            coordinates.add(new Coordinate(currX, currY));
            maxY = Math.max(maxY, localMaxY);
            break;
          }
        }
      }
    }

    return String.valueOf(maxY);
  }

  @Override
  public String part2(List<String> input) {
    Target target = new Target(input.get(0));
    List<Coordinate> coordinates = new ArrayList<>();
    int maxY = 0;

    for (int i = 1; i < 319; i++) {
      for (int j = -92; j < 92; j++) {
        int currX = 0;
        int currY = 0;
        int xVelocity = i;
        int yVelocity = j;
        int localMaxY = 0;
        while(!target.pastTarget(currX, currY, currX + xVelocity, currY +yVelocity)) {
          currX += xVelocity;
          currY += yVelocity;
          xVelocity = xVelocity == 0 ? 0 : xVelocity - 1;
          yVelocity -= 1;
          localMaxY = Math.max(localMaxY, currY);

          if (target.inTarget(currX, currY)) {
            coordinates.add(new Coordinate(currX, currY));
            maxY = Math.max(maxY, localMaxY);
            break;
          }
        }
      }
    }

    return String.valueOf(coordinates.size());
  }

  private class Target {

    int minX;
    int maxX;
    int minY;
    int maxY;

    public Target(String s) {
      Pattern p = Pattern.compile("target area: x=(?<minX>\\d+)..(?<maxX>\\d+), y=(?<minY>-*\\d+)..(?<maxY>-*\\d+)");
      Matcher m = p.matcher(s);
      if (m.matches()) {
        minX = Integer.parseInt(m.group("minX"));
        maxX = Integer.parseInt(m.group("maxX"));
        minY = Integer.parseInt(m.group("minY"));
        maxY = Integer.parseInt(m.group("maxY"));
      }
    }

    public boolean inTarget(int x, int y) {
      return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public boolean pastTarget(int currX, int currY, int nextX, int nextY) {
      return nextX > maxX && nextY < minY || (currX == nextX && currY < minY);
    }
  }
}
