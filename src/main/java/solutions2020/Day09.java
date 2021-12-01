package solutions2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.Day;

public class Day09 implements Day {

  int preamble;

  public Day09() {
    this.preamble = 25;
  }

  public Day09(int preamble) {
    this.preamble = preamble;
  }

  @Override
  public String part1(List<String> input) {
    List<Long> parsed = input.stream()
        .map(Long::valueOf)
        .collect(Collectors.toList());

    Long newValue = findInvalidNumber(parsed);

    return String.valueOf(newValue);
  }

  private Long findInvalidNumber(List<Long> parsed) {
    Map<Long, Integer> encounteredValToIndex = new HashMap<>();
    Long newValue = 0L;

    for (int newValueIndex = preamble, i = 0; i < parsed.size() && i < newValueIndex; ) {
      newValue = parsed.get(newValueIndex);
      Long target = newValue - parsed.get(i);
      if (encounteredValToIndex.containsKey(target)) {
        encounteredValToIndex.remove(parsed.get(newValueIndex - preamble));
        newValueIndex++;
        // reset to beginning of window
        i = newValueIndex - preamble;
      }
      encounteredValToIndex.put(parsed.get(i), i);
      i++;
    }
    return newValue;
  }


  @Override
  public String part2(List<String> input) {
    List<Long> parsed = input.stream()
        .map(Long::valueOf)
        .collect(Collectors.toList());

    Long target = findInvalidNumber(parsed);

    List<Long> cumulativeSum = new ArrayList<>();

    for (int i = 0; i < parsed.size(); i++) {
      Long previousSum = i == 0 ? 0 : cumulativeSum.get(i-1);
      cumulativeSum.add(parsed.get(i) + previousSum);
    }

    for (int startIndex = 0, endIndex = 2; startIndex < parsed.size(); ) {
      Long sumBetweenPoints = cumulativeSum.get(endIndex) - cumulativeSum.get(startIndex);
      if (sumBetweenPoints < target) {
        endIndex++;
      } else if (sumBetweenPoints > target) {
        startIndex++;
      } else {
        List<Long> window = parsed.subList(startIndex, endIndex);
        Long min = window.stream().min(Long::compareTo).get();
        Long max = window.stream().max(Long::compareTo).get();
        return String.valueOf(min + max);
      }
    }

    return "pppbbbt blah";
  }

}
