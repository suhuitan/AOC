package solutions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day18 implements Day {

  @Override
  public String part1(List<String> input) {
    String s = input.get(0);
    for (int i = 1; i < input.size(); i++) {
      s = add(s, input.get(i));
    }
    return String.valueOf(magnitude(s));
  }

  public int magnitude(String s) {
    Pattern p = Pattern.compile("\\[(?<left>\\d+),(?<right>\\d+)]");
    Matcher matcher = p.matcher(s);

    while(matcher.find()) {
      int left = Integer.parseInt(matcher.group("left"));
      int right = Integer.parseInt(matcher.group("right"));

      int magnitude = 3*left + 2*right;
      s = s.substring(0, matcher.start()) + magnitude + s.substring(matcher.end());
      matcher = p.matcher(s);
    }
    return Integer.parseInt(s);
  }

  public String add(String x, String y) {
    String input = String.format("[%s,%s]", x, y);
    boolean change = false;

    while (true) {
      String previous = String.valueOf(input);
      if (!(input = explode(input).getLeft()).equals(previous)) {
        continue;
      }

      if (!(input = split(input).getLeft()).equals(previous)) {
        continue;
      }

      return previous;
    }
  }

  public Pair<String, Boolean> split(String s) {
    StringBuilder b = new StringBuilder();
    Pattern p = Pattern.compile("\\d{2,}");
    Matcher matcher = p.matcher(s);

    while(matcher.find()) {
      int value = Integer.parseInt(matcher.group());
      int left = Math.floorDiv(value, 2);
      int right = (int) Math.ceil((double) value/2);
      String insert = String.format("[%d,%d]", left, right);
      b.append(s, 0, matcher.start());
      b.append(insert);
      b.append(s, matcher.end(), s.length());
      return Pair.of(b.toString(), true);
    }
    return Pair.of(s, false);
  }

  public Pair<String, Boolean> explode(String s) {
    StringBuilder b = new StringBuilder();
    Pattern p = Pattern.compile("\\[(?<left>\\d+),(?<right>\\d+)]");
    Matcher matcher = p.matcher(s);

    while(matcher.find()) {
      int start = matcher.start();
      long numberOfOpeningBrackets = s.substring(0, start).chars()
          .filter((c) -> c == '[')
          .count();

      long numberOfClosingBrackets = s.substring(0, start).chars()
          .filter((c) -> c == ']')
          .count();
      if (numberOfOpeningBrackets - numberOfClosingBrackets > 3) {
        int left = Integer.parseInt(matcher.group("left"));
        int right = Integer.parseInt(matcher.group("right"));
        String beforeString = s.substring(0, start);
        String afterString = s.substring(matcher.end());
        Optional<Integer> leftMostInt = Arrays.stream(beforeString
                .split(",|\\[|]"))
            .filter(StringUtils::isNumeric)
            .map(Integer::parseInt)
            .reduce((x, y) -> y);

        Optional<Integer> rightMostInt = Arrays.stream(afterString
                .split(",|\\[|]"))
            .filter(StringUtils::isNumeric)
            .map(Integer::parseInt)
            .findFirst();

        if (leftMostInt.isPresent()) {
          String strValue = String.valueOf(leftMostInt.get());
          int index = beforeString.lastIndexOf(strValue);
          beforeString = beforeString.substring(0, index) + (leftMostInt.get() + left) + beforeString.substring(index + strValue.length());
        }

        if (rightMostInt.isPresent()) {
          afterString = afterString.replaceFirst(rightMostInt.get().toString(), String.valueOf((rightMostInt.get() + right)));
        }

        b.append(beforeString);
        b.append("0");
        b.append(afterString);
        return Pair.of(b.toString(), true);
      }
    }

    return Pair.of(s, false);
  }

  @Override
  public String part2(List<String> input) {
    int max = 0;
    for (int i = 1; i < input.size(); i++) {
      for (int j = 0; j < input.size(); j++) {
        max = Math.max(max, magnitude(add(input.get(i), input.get(j))));
        max = Math.max(max, magnitude(add(input.get(j), input.get(i))));
      }
    }
    return String.valueOf(max);
  }
}
