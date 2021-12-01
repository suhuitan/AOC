package solutions2020;

import static solutions2020.Day12.Direction.EAST;
import static solutions2020.Day12.Direction.NORTH;
import static solutions2020.Day12.Direction.SOUTH;
import static solutions2020.Day12.Direction.WEST;

import java.util.Arrays;
import java.util.List;

import utils.Day;

public class Day12 implements Day {

  boolean debug = true;

  @Override
  public String part1(List<String> input) {
    Ship ship = new Ship();

    for (String s : input) {
      String inst = s.substring(0, 1);
      Integer val = Integer.valueOf(s.substring(1));
      if (inst.equals("L") || inst.equals("R")) {
        ship.changeFacing(inst, val);
      } else {
        ship.moveShip(inst, val);
      }
    }

    return String.valueOf(ship.calculateManhattanDistance());
  }

  private class Ship {

    Direction facing = EAST;
    int shipX = 0;
    int shipY = 0;


    public int calculateManhattanDistance() {
      return Math.abs(shipX) + Math.abs(shipY);
    }

    public void moveShip(String s, Integer value) {
      Direction travel;
      switch (s) {
        case "N":
        case "S":
        case "W":
        case "E":
          travel = Direction.getVal(s);
          break;
        case "F":
          travel = facing;
          break;
        default:
          throw new IllegalArgumentException("what is this");
      }
      moveShip(travel, value);

      if (debug) {
        System.out.println(String.format("%d %d", shipX, shipY));
      }
    }

    private void changeFacing(String direction, Integer value) {
      List<Direction> values = Arrays.asList(EAST, SOUTH, WEST, NORTH);
      int index = values.indexOf(facing);

      for (int i = 0; i < value / 90; i++) {
        if (direction.equals("R")) {
          index++;
          if (index == values.size()) {
            index = 0;
          }
        }

        if (direction.equals("L")) {
          index--;
          if (index < 0) {
            index = values.size() - 1;
          }
        }
        facing = values.get(index);
      }
      if (debug) {
        System.out.println(facing);
      }
    }

    private void moveShip(Direction dir, Integer value) {
      if (dir == EAST) {
        shipX += value;
      }
      if (dir == WEST) {
        shipX -= value;
      }
      if (dir == NORTH) {
        shipY += value;
      }
      if (dir == SOUTH) {
        shipY -= value;
      }
    }
  }

  @Override
  public String part2(List<String> input) {
    Ship2 ship = new Ship2();

    for (String s : input) {
      String inst = s.substring(0, 1);
      Integer val = Integer.valueOf(s.substring(1));

      switch (inst) {
        case "L":
        case "R":
          ship.rotateWayPoint(inst, val);
          break;
        case "F":
          ship.moveToWayPoint(val);
          break;
        case "N":
        case "S":
        case "E":
        case "W":
          ship.moveWayPoint(inst, val);
          break;
      }
    }
    return String.valueOf(ship.calculateManhattanDistance());
  }

  public enum Direction {
    EAST("E"),
    SOUTH("S"),
    WEST("W"),
    NORTH("N");

    private final String val;

    Direction(String n) {
      this.val = n;
    }

    public static Direction getVal(String s) {
      for (Direction dir : Direction.values()) {
        if (s.equals(dir.val)) {
          return dir;
        }
      }
      return null;
    }
  }

  private class Ship2 extends Ship {

    int wayPointX = 10;
    int wayPointY = 1;

    public void rotateWayPoint(String inst, Integer value) {
      int iterations = inst.equals("R") ? value / 90 : 4 - value / 90;
      for (int i = 0; i < iterations; i++) {
        int oldX = wayPointX;
        int oldY = wayPointY;
        wayPointX = oldY;
        wayPointY = -oldX;
      }

      if (debug) {
        System.out.println(String.format("rotated %d %d", wayPointX, wayPointY));
      }
    }

    public void moveToWayPoint(Integer val) {
      shipX += (wayPointX * val);
      shipY += (wayPointY * val);
      if (debug) {
        System.out.println(String.format("Moved %d %d", shipX, shipY));
      }
    }

    public void moveWayPoint(String inst, Integer value) {
      Direction dir = Direction.getVal(inst);
      if (dir == EAST) {
        wayPointX += value;
      }
      if (dir == WEST) {
        wayPointX -= value;
      }
      if (dir == NORTH) {
        wayPointY += value;
      }
      if (dir == SOUTH) {
        wayPointY -= value;
      }
      if (debug) {
        System.out.println(String.format("moved way %d %d", wayPointX, wayPointY));
      }
    }
  }
}
