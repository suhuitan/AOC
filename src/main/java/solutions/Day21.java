package solutions;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day21 implements Day {

  BigInteger p1Wins = BigInteger.valueOf(0L);
  BigInteger p2Wins = BigInteger.valueOf(0L);

  @Override
  public String part1(List<String> input) {
    int p1start = Integer.parseInt(input.get(0).split(": ")[1]);
    int p2start = Integer.parseInt(input.get(1).split(": ")[1]);
    int dieRolls = 0;
    boolean onesTurn = true;
    DeterministicDie deterministicDie = new DeterministicDie();

    Player one = new Player(p1start);
    Player two = new Player(p2start);

    for (int dieValues = 0; one.points < 1000 && two.points < 1000; dieRolls++) {
      dieValues += deterministicDie.next();
      if ((dieRolls + 1) % 3 == 0) {
        Player current = onesTurn ? one : two;
        current.addPoints(dieValues);
        dieValues = 0;
        onesTurn = !onesTurn;

        //System.out.println(String.format("Player %d, space: %d, score: %d", onesTurn ? 1 : 2, current.currentPosition, current.points));
      }
    }

    Player losingPlayer = one.points < two.points ? one : two;

    return String.valueOf(dieRolls * losingPlayer.points);
  }

  @Override
  public String part2(List<String> input) {
    int p1start = Integer.parseInt(input.get(0).split(": ")[1]);
    int p2start = Integer.parseInt(input.get(1).split(": ")[1]);

    Player one = new Player(p1start);
    Player two = new Player(p2start);

    playOne(p1start, 0, p2start, 0, 3, 1);
    playOne(p1start, 0, p2start, 0, 4, 3);
    playOne(p1start, 0, p2start, 0, 5, 6);
    playOne(p1start, 0, p2start, 0, 6, 7);
    playOne(p1start, 0, p2start, 0, 7, 6);
    playOne(p1start, 0, p2start, 0, 8, 3);
    playOne(p1start, 0, p2start, 0, 9, 1);

    return p1Wins.compareTo(p2Wins) == 1 ? p1Wins.toString() : p2Wins.toString();
  }

  private void playOne(int p1Pos, int p1Score, int p2pos, int p2Score, int rollValue, long occurences) {
    Pair<Integer, Integer> result = addPoints(rollValue, p1Pos, p1Score);
    p1Score = result.getLeft();
    p1Pos = result.getRight();
    if (p1Score > 20) {
      p1Wins = p1Wins.add(BigInteger.valueOf(occurences));
      return;
    }
    playTwo(p1Pos, p1Score, p2pos, p2Score, 3, 1 * occurences);
    playTwo(p1Pos, p1Score, p2pos, p2Score, 4, 3 * occurences);
    playTwo(p1Pos, p1Score, p2pos, p2Score, 5, 6 * occurences);
    playTwo(p1Pos, p1Score, p2pos, p2Score, 6, 7 * occurences);
    playTwo(p1Pos, p1Score, p2pos, p2Score, 7, 6 * occurences);
    playTwo(p1Pos, p1Score, p2pos, p2Score, 8, 3 * occurences);
    playTwo(p1Pos, p1Score, p2pos, p2Score, 9, 1 * occurences);
  }

  Pair<Integer, Integer> addPoints(int x, int currentPosition, int points) {
    if (x > 10) {
      x = x % 10;
    }
    currentPosition += x;
    currentPosition = currentPosition > 10 ? currentPosition - 10 : currentPosition;
    points += currentPosition;
    return Pair.of(points, currentPosition);
  }

  private void playTwo(int p1Pos, int p1Score, int p2Pos, int p2Score, int rollValue, long occurences) {
    Pair<Integer, Integer> result = addPoints(rollValue, p2Pos, p2Score);
    p2Score = result.getLeft();
    p2Pos = result.getRight();
    if (p2Score > 20) {
      p2Wins = p2Wins.add(BigInteger.valueOf(occurences));
      return;
    }
    playOne(p1Pos, p1Score, p2Pos, p2Score, 3, 1 * occurences);
    playOne(p1Pos, p1Score, p2Pos, p2Score, 4, 3 * occurences);
    playOne(p1Pos, p1Score, p2Pos, p2Score, 5, 6 * occurences);
    playOne(p1Pos, p1Score, p2Pos, p2Score, 6, 7 * occurences);
    playOne(p1Pos, p1Score, p2Pos, p2Score, 7, 6 * occurences);
    playOne(p1Pos, p1Score, p2Pos, p2Score, 8, 3 * occurences);
    playOne(p1Pos, p1Score, p2Pos, p2Score, 9, 1 * occurences);
  }


  private class Game {

    Player one;
    Player two;
    boolean onesTurn;
    int dieRolls;
    int dieSum;


    public Game(Player one, Player two) {
      this.one = one;
      this.two = two;
      this.dieRolls = 0;
      this.dieSum = 0;
      this.onesTurn = true;
    }

    public Game(Game game) {
      this.one = new Player(game.one);
      this.two = new Player(game.two);
      this.onesTurn = game.onesTurn;
      this.dieRolls = game.dieRolls;
      this.dieSum = game.dieSum;
    }

    private void play(int dieValue) {
      dieSum += dieValue;
      if ((dieRolls + 1) % 3 == 0) {
        Player current = onesTurn ? one : two;
        current.addPoints(dieSum);
        dieSum = 0;
        onesTurn = !onesTurn;
      }
      dieRolls++;
    }
  }

  private class Player {

    int currentPosition;
    int points;

    public Player(int currentPosition) {
      this.currentPosition = currentPosition;
    }

    public Player(Player one) {
      this.currentPosition = one.currentPosition;
      this.points = one.points;
    }

    int addPoints(int x) {
      if (x > 10) {
        x = x % 10;
      }
      currentPosition += x;
      currentPosition = currentPosition > 10 ? currentPosition - 10 : currentPosition;
      points += currentPosition;
      return points;
    }

  }

  public static class DeterministicDie implements Iterator<Integer> {

    List<Integer> deterministicDie = IntStream.range(1, 101).boxed().collect(Collectors.toList());
    private Iterator<Integer> iterator = deterministicDie.iterator();

    @Override
    public boolean hasNext() {
      if (!iterator.hasNext()) {
        iterator = deterministicDie.iterator();
      }
      return iterator.hasNext();
    }

    @Override
    public Integer next() {
      if (!iterator.hasNext()) {
        iterator = deterministicDie.iterator();
      }
      return iterator.next();
    }

  }
}
