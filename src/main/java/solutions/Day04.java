package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day04 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Integer> drawnNumbers = Arrays.stream(input.get(0).split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    List<Board> boards = process(input.subList(2, input.size()));

    for (Integer cur : drawnNumbers) {
      Optional<Board> winningBoard = boards.stream()
          .filter((board) -> board.mark(cur))
          .findFirst();

      if(winningBoard.isPresent()) {
        return String.valueOf(winningBoard.get().sumOfUnmarked() * cur);
      }
    }
    return "no result";
  }

  private List<Board> process(List<String> input) {
    List<Board> boards = new ArrayList<>();
    for (int i = 0; i < input.size()/6 + 1; i++) {
      Board board = new Board(input.subList(i*6, i*6+5));
      boards.add(board);
    }

    return boards;
  }

  @Override
  public String part2(List<String> input) {
    List<Integer> drawnNumbers = Arrays.stream(input.get(0).split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    List<Board> boards = process(input.subList(2, input.size()));
    Board finalWinningBoard = null;
    int valueAtFinal = -1;
    for (Integer cur : drawnNumbers) {
      List<Board> winningBoard = boards.stream()
          .filter((board) -> board.mark(cur))
          .collect(Collectors.toList());

      if(!winningBoard.isEmpty()) {
        finalWinningBoard = winningBoard.get(0);
        boards.removeAll(winningBoard);
        valueAtFinal = cur;
      }
    }
    return String.valueOf(finalWinningBoard.sumOfUnmarked() * valueAtFinal);
  }

  private class Board {
    int[][] board = new int[5][5];
    Map<Integer, Pair<Integer, Integer>> locationMap = new HashMap<>();

    public Board(List<String> strings) {
      for (int i = 0; i < 5; i++) {
        String[] row = strings.get(i).trim().split("\\s+");
        for (int j = 0; j < 5; j++) {
          int value = Integer.parseInt(row[j]);
          board[i][j] = value;
          locationMap.put(value, Pair.of(i, j));
        }
      }
    }

    public boolean mark(int value){
      if (!locationMap.containsKey(value)){
        return false;
      }

      Pair<Integer, Integer> location = locationMap.remove(value);
      board[location.getLeft()][location.getRight()] = -1;

      if(win(location)) {
        return true;
      }
      return false;
    }

    private boolean win(Pair<Integer, Integer> location) {
      int sumOfRow = IntStream.of(board[location.getLeft()]).sum();
      int sumOfColumn = IntStream.range(0, 5)
          .map((i) -> board[i][location.getRight()]).sum();

      return sumOfColumn == -5 || sumOfRow == -5;
    }

    public int sumOfUnmarked() {
      Integer sum = locationMap.keySet().stream().reduce(0, Integer::sum);
      return sum;
    }
  }
}
