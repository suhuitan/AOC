package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import utils.Day;

public class Day22 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Integer> player1 = new ArrayList<>();
    for (String i : input.subList(1, input.indexOf(""))) {
      player1.add(Integer.valueOf(i));
    }

    List<Integer> player2 = new ArrayList<>();
    for (String i : input.subList(input.indexOf("") + 2, input.size())) {
      player2.add(Integer.valueOf(i));
    }

    int i = 0;
    while (!player1.isEmpty() && !player2.isEmpty()) {
      List<Integer> currentPlay = Arrays.asList(player1.remove(0), player2.remove(0));

      List<Integer> winningDeck =  player1Wins(currentPlay) ? player1:player2;
      winningDeck.addAll(currentPlay.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()));

    }

    int value = calculateFinalScore(input, player1, player2);

    return String.valueOf(value);
  }

  public boolean player1Wins(List<Integer> currentPlay) {
    return currentPlay.get(0) > currentPlay.get(1);
  }

  public int calculateFinalScore(List<String> input, List<Integer> player1, List<Integer> player2) {
    Collections.reverse(player1);
    Collections.reverse(player2);
    int value = 0;

    for (int j = 1; j < input.size() - 2; j++) {
      int card = player1.size() > player2.size() ? player1.get(j-1) : player2.get(j-1);
      value += (j * card);
    }
    return value;
  }

  @Override
  public String part2(List<String> input) {
    System.out.println("part 2\n");
    List<Integer> player1 = new ArrayList<>();
    for (String i : input.subList(1, input.indexOf(""))) {
      player1.add(Integer.valueOf(i));
    }

    List<Integer> player2 = new ArrayList<>();
    for (String i : input.subList(input.indexOf("") + 2, input.size())) {
      player2.add(Integer.valueOf(i));
    }

    playCombat(player1, player2, 1);

    int value = calculateFinalScore(input, player1, player2);

    return String.valueOf(value);
  }

  // true if p1 Won
  private boolean playCombat(List<Integer> player1, List<Integer> player2, int game) {
    Set<String> record = new HashSet<>();

    int i =1;
    while (!player1.isEmpty() && !player2.isEmpty()) {

      String startingDeck = getRecord(player1, player2);
      if (record.contains(startingDeck)) {
        return true;
      }
      record.add(startingDeck);

      int p1 = player1.remove(0);
      int p2 = player2.remove(0);

      boolean p1Won;

      if (p1 <= player1.size() && p2 <= player2.size()) {
        List<Integer> newP1 = new ArrayList(player1.subList(0, p1));
        List<Integer> newP2 = new ArrayList(player2.subList(0, p2));
        p1Won = playCombat(newP1, newP2, game + 1);
      } else {
        p1Won = p1 > p2;
      }

      List<Integer> ordered = p1Won ? Arrays.asList(p1, p2) : Arrays.asList(p2, p1);
      List<Integer> winningDeck =  p1Won ? player1:player2;
      winningDeck.addAll(ordered);
    }
    return player1.size() > player2.size();
  }

  private String getRecord(List<Integer> player1, List<Integer> player2) {
    StringJoiner join =  new StringJoiner(", ", "p1:", "");
    player1.stream()
        .forEach((i) -> join.add(String.valueOf(i)));
    join.add("p2:");
    player2.stream()
        .forEach((i) -> join.add(String.valueOf(i)));
    return join.toString();
  }
}
