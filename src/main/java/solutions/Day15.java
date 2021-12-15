package solutions;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import utils.Day;

public class Day15 implements Day {

  private static int dx[] = {-1, 0, 1, 0};
  private static int dy[] = {0, -1, 0, 1};

  private int[][] cost;

  @Override
  public String part1(List<String> input) {
    parse(input);

    int rows = input.size();
    int columns = input.get(0).length();
    int min = minimumCostShortestPath(rows - 1, columns - 1, cost);


    return String.valueOf(min);
  }

  private int minimumCostShortestPath(int endX, int endY, int[][] cost) {
    int accCost[][] = new int[endX +1][endY+1];
    boolean visited[][] = new boolean[endX +1][endY+1];
    for (int i = 0; i <= endX; i++) {
      for (int j = 0; j <= endY; j++) {
        accCost[i][j] = Integer.MAX_VALUE;
      }
    }

    Queue<Cell> queue = new PriorityQueue<>(Comparator.comparingInt(cell -> cell.val));

    // first Cell costs 0;
    accCost[0][0] = 0;
    queue.add(new Cell(0, 0, cost[0][0]));

    while (!queue.isEmpty()) {
      Cell cell = queue.poll();
      int x = cell.x;
      int y = cell.y;

      for (int i = 0; i < 4; i++) {
        int next_x = x + dx[i];
        int next_y = y + dy[i];

        if (navigable(next_x, next_y, endX, endY)
            && !visited[next_x][next_y]
            && (accCost[x][y] + cost[next_x][next_y]) < accCost[next_x][next_y]
        ) {
          accCost[next_x][next_y] = accCost[x][y] + cost[next_x][next_y];
          queue.add(new Cell(next_x, next_y, accCost[next_x][next_y]));
        }
      }
      visited[x][y] = true;
    }
    return accCost[endX][endY];
  }

  private boolean navigable(int x, int y, int maxX, int maxY) {
    return x >= 0 && x <= maxX && y >= 0 && y <= maxY;
  }

  private void parse(List<String> input) {
    int rows = input.size();
    int columns = input.get(0).length();
    cost = new int[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        cost[i][j] = Character.getNumericValue(input.get(i).charAt(j));
      }
    }
  }

  @Override
  public String part2(List<String> input) {

    int rows = input.size();
    int columns = input.get(0).length();
    parse(input);

    int[][] newCosts = new int[rows * 5][columns * 5];

    for (int j = 0; j < rows * 5; j++) {
      for (int k = 0; k < columns * 5; k++) {
        newCosts[j][k] = ((cost[j % rows][k % columns] + j/rows + k/columns -1) % 9) +1;
      }
    }


    int min = minimumCostShortestPath(rows*5 - 1, columns*5 - 1, newCosts);
    return String.valueOf(min);
  }

  private class Cell {

    private final int val;
    private final int x;
    private final int y;

    public Cell(int x, int y, int val) {
      this.x = x;
      this.y = y;
      this.val = val;
    }
  }
}
