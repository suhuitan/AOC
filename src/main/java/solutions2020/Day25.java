package solutions2020;

import java.util.List;

import utils.Day;

public class Day25 implements Day {

  @Override
  public String part1(List<String> input) {
    Integer cardPublicKey = Integer.valueOf(input.get(0));
    Integer doorPublicKey = Integer.valueOf(input.get(1));

    long value = 1;
    int cardLoopSize = 0;

    while (value != cardPublicKey) {
      value = (7 * value) % 20201227;
      cardLoopSize++;
    }

    value = 1;
    for (int i = 0; i < cardLoopSize; i++) {
      value = (doorPublicKey * value) % 20201227;
    }

    return String.valueOf(value);
  }

  private long transform(int subjectNumber, int loopSize) {
    long value = 1;
    for (int i = 0; i < loopSize; i++) {
      value = (value * subjectNumber) % 20201227;
    }
    return value;
  }


  @Override
  public String part2(List<String> input) {
    return null;
  }
}
