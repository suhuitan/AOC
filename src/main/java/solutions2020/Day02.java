package solutions2020;

import java.util.List;

import utils.Day;

public class Day02 implements Day {

  @Override
  public String part1(List<String> input) {
    long validPasswords = input.stream()
        .map(Input::new)
        .map(this::isValid1)
        .filter((output) -> output.equals(true))
        .count();
    return String.valueOf(validPasswords);
  }

  boolean isValid1(Input input) {
    String regexMatcher = String.format("^([^%1$c]*%1$c){%2$d,%3$d}[^%1$c]*$", input.letter, input.no1, input.no2);
    return input.password.matches(regexMatcher);
  }

  @Override
  public String part2(List<String> input) {
    long validPasswords = input.stream()
        .map(Input::new)
        .map(this::isValid2)
        .filter((output) -> output.equals(true))
        .count();
    return String.valueOf(validPasswords);
  }

  boolean isValid2(Input input) {
    char c1 = input.password.charAt(input.no1 - 1);
    char c2 = input.password.charAt(input.no2 - 1);
    return c1 == input.letter ^ c2 == input.letter;
  }

  public static class Input {
    int no1;
    int no2;
    char letter;
    String password;
    Input(String s) {
      String[] inputs = s.split(" ");
      String[] occurences = inputs[0].split("-");
      no1 = Integer.valueOf(occurences[0]);
      no2 = Integer.valueOf(occurences[1]);
      letter = inputs[1].charAt(0);
      password = inputs[2];
    }
  }
}
