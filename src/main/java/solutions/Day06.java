package solutions;

import static java.util.stream.Collectors.summingLong;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.Day;

public class Day06 implements Day {

  @Override
  public String part1(List<String> input) {
    int days = 80;
    return spawnOfFish(input, days);
  }

  private String spawnOfFish(List<String> input, int days) {
    Map<Integer, Long> fishTimerToNumberMap = Arrays.stream(input.get(0).split(","))
        .collect(Collectors.groupingBy((s) -> Integer.parseInt(s), Collectors.counting()));

    for (int i = 0; i < days; i++) {
      Map<Integer, Long> nextFishTimerToNumberMap = new HashMap<>();
      fishTimerToNumberMap.entrySet().stream()
          .forEach((fishEntry) -> {
            int nextValue = fishEntry.getKey() - 1;
              Long numberOfFish = fishEntry.getValue();

            if (nextValue < 0) {
              nextFishTimerToNumberMap.put(8, numberOfFish);
              nextFishTimerToNumberMap.compute(6, (k, v) -> v == null ? numberOfFish : v + numberOfFish);
            } else {
              nextFishTimerToNumberMap.merge(nextValue, numberOfFish, Long::sum);
            }
          });
      fishTimerToNumberMap = nextFishTimerToNumberMap;
    }

    return String.valueOf(fishTimerToNumberMap.values().stream().reduce(Long::sum).get());
  }

  @Override
  public String part2(List<String> input) {
    int days = 256;
    return spawnOfFish(input, days);
  }
}
