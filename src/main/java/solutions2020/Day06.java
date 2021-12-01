package solutions2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import utils.Day;

public class Day06 implements Day {

  public static final Character DELIMITER = ':';

  @Override
  public String part1(List<String> input) {
    int sum = 0;
    Set<Character> question = new HashSet<>();

    List<String> parsedInputs = parse(input);

    for(String in : parsedInputs) {
      Set<Character> characters = in.chars()
          .mapToObj((val) -> (char) val)
          .filter((val) -> !DELIMITER.equals(val))
          .collect(Collectors.toSet());

        sum += characters.size();
      }

    return String.valueOf(sum);
  }

  @Override
  public String part2(List<String> input) {
    int sum = 0;

    List<String> groupInputs = parse(input);

    for(String in : groupInputs) {
      Map<Character, Integer> frequencyMap = in.chars()
          .mapToObj(c -> (char) c)
          .collect(Collectors.toMap(Function.identity(), startVal -> 1, Integer::sum));

      int numberOfGroups = frequencyMap.get(DELIMITER) == null ? 1 : frequencyMap.get(DELIMITER) + 1;
      sum += frequencyMap.values()
          .stream()
          // if the number of characters in a group is equal to the number of groups, then everyone answered yes
          .filter((count) -> count.equals(numberOfGroups))
          .count();

    }

    return String.valueOf(sum);
  }

  private List<String> parse(List<String> input) {
    List<String> parsed = new ArrayList<>();
    StringJoiner buffer = new StringJoiner(String.valueOf(DELIMITER));
    for (String in: input) {
      if (in.isEmpty()) {
        parsed.add(buffer.toString());
        buffer = new StringJoiner(String.valueOf(DELIMITER));
      } else {
        buffer.add(in);
      }
    }

    parsed.add(buffer.toString());
    return parsed;
  }
}
