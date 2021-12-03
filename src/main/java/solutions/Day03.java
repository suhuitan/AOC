package solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.Day;

public class Day03 implements Day {

  @Override
  public String part1(List<String> input) {
    int codeLength = input.get(0).length();
    int[] sums = new int[codeLength];

    input.forEach((str) -> {
      for (int i = 0; i < codeLength; i++) {
        sums[i] += str.charAt(i) == '0' ? 0 : 1;
      }
    });

    long gammaRate = 0, epislonRate = 0;
    for (int i = 0; i < codeLength; i++) {
      double value = Math.pow(2, (codeLength - i - 1));
      if(sums[i] > input.size()/2) {
        gammaRate += value;
      } else {
        epislonRate += value;
      }
    }

    return String.valueOf(gammaRate * epislonRate);
  }

  @Override
  public String part2(List<String> input) {

    String O2 = extract(new ArrayList<>(input), '1', '0' );
    String CO2 = extract(new ArrayList<>(input), '0', '1' );

    int O2val = Integer.parseInt(O2, 2);
    int CO2val = Integer.parseInt(CO2, 2);

    return String.valueOf(O2val * CO2val);
  }

  private String extract(List<String> input, char x, char y) {
    int i = 0;
    while (input.size() > 1) {
      int finalI = i;
      Integer sumAtIndex = input.stream()
          .map((str) -> str.charAt(finalI) == '0' ? 0 : 1)
          .reduce(0, Integer::sum);

      int compareResult = Integer.compare(sumAtIndex, input.size() - sumAtIndex);

      char bitCriteria = compareResult < 0 ? y : x;

      List<String> reducedList = input.stream()
          .filter((s) -> s.charAt(finalI) == bitCriteria)
          .collect(Collectors.toList());
      input = reducedList;
      i++;
    }
    return input.get(0);
  }
}
