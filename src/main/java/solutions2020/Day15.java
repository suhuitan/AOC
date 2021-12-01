package solutions2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Day;

public class Day15 implements Day {

  public static final boolean debug = true;

  @Override
  public String part1(List<String> input) {
    long lastNumberSpoken = getLastNumberSpoken(input, 2020);

    return String.valueOf(lastNumberSpoken);
  }

  public long getLastNumberSpoken(List<String> input, int targetNumberSpoken) {
    Map<Long, Long> inputAgeMap = new HashMap<>();
    long counter = 0;
    long lastNumberSpoken = 0;

    String[] startingNumbers = input.get(0).split(",");
    for (int i = 0; i < startingNumbers.length; i++) {
      Long inputNumber = Long.valueOf(startingNumbers[i]);
      inputAgeMap.put(inputNumber, i + 1L);
      lastNumberSpoken = inputNumber;
      counter++;
    }

    long nextNumber = 0;
    while(counter < targetNumberSpoken) {
      if (inputAgeMap.containsKey(lastNumberSpoken)) {
        nextNumber = counter - inputAgeMap.get(lastNumberSpoken);
      } else {
        nextNumber = 0;
      }
      inputAgeMap.put(lastNumberSpoken, counter);
      lastNumberSpoken = nextNumber;
      counter ++;
      if (debug && counter % 1000 == 0) {
        System.out.println(String.format("At turn %d: %d", counter, lastNumberSpoken));
      }
    }
    return lastNumberSpoken;
  }

  @Override
  public String part2(List<String> input) {

    long lastNumberSpoken = getLastNumberSpoken(input, 30000000);

    return String.valueOf(lastNumberSpoken);

  }

}


