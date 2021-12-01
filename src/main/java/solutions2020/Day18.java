package solutions2020;

import java.util.List;
import java.util.stream.Collectors;

import utils.Day;

public class Day18 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Long> solutions = input.stream()
        .map((String s) -> evaluateLtoR(s, 0, '+'))
        .collect(Collectors.toList());
    Long reduce = solutions.stream().mapToLong(Long::valueOf).reduce(Long::sum).getAsLong();
    return String.valueOf(reduce);
  }

  private Long evaluateLtoR(String s, long sum, char operator) {
    if (s.isEmpty()) {
      return sum;
    }

    if (Character.isDigit(s.charAt(0))) {
      int firstSpace = s.indexOf(' ');

      // if this is the last digit
      if (firstSpace == -1) {
        int value = Integer.parseInt(s);
        return findNewSum(sum, operator, value);
      }

      //read value
      int value = Integer.parseInt(s.substring(0, firstSpace));
      //read next operator
      char op = s.charAt(firstSpace + 1);

      return evaluateLtoR(s.substring(firstSpace + 3).trim(), findNewSum(sum, operator, value), op);
    }

    if (s.charAt(0) == '(') {
      // find corresponding right bracket
      for (int i = 1, encounteredLeftBrackets = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
          encounteredLeftBrackets++;
        } else if (s.charAt(i) == ')') {
          if (encounteredLeftBrackets == 0) {
            long value = evaluateLtoR(s.substring(1, i), 0, '+');
            if (i == s.length() - 1) {
              return findNewSum(sum, operator, value);
            }
            //read next operator
            char op = s.charAt(i + 2);
            return evaluateLtoR(s.substring(i + 3).trim(), findNewSum(sum, operator, value), op);
          }
          encounteredLeftBrackets--;
        }
      }
    }

    throw new IllegalArgumentException();
  }

  private long findNewSum(long sum, char operator, long value) {
    switch (operator) {
      case '+':
        return sum + value;
      case '*':
        return sum * value;
    }
    return 0;
  }

  @Override
  public String part2(List<String> input) {
    List<Long> solutions = input.stream()
        .map((String s) -> evaluateLtoRWithPrecedence(s, 0, '+'))
        .collect(Collectors.toList());
    Long reduce = solutions.stream().mapToLong(Long::valueOf).reduce(Long::sum).getAsLong();
    return String.valueOf(reduce);
  }

  private Long evaluateLtoRWithPrecedence(String s, long sumSoFar, char previousOperator) {
    if (Character.isDigit(s.charAt(0))) {
      if (s.length() == 1) {
        return findNewSum(sumSoFar,previousOperator, Long.valueOf(s));
      }

      long op1 = Integer.parseInt(s.substring(0, 1));
      char nextOperator = s.charAt(2);

      if (previousOperator == '+') {
        long newSum = sumSoFar + op1;
        Long returnVal = evaluateLtoRWithPrecedence(s.substring(4), newSum, nextOperator);
        return returnVal;
      }

      if (previousOperator == '*') {
        Long returnVal = evaluateLtoRWithPrecedence(s.substring(4), op1, nextOperator);
        return sumSoFar * returnVal;
      }
    }

    if (s.charAt(0) == '(') {
      // find corresponding right bracket
      int rightBracketI = findCorrespondingRightBracket(s);

      // consider this entire bracket as the left value
      long leftValue = evaluateLtoRWithPrecedence(s.substring(1, rightBracketI), 0, '+');
      if (rightBracketI == s.length() - 1) {
        return findNewSum(sumSoFar,previousOperator, leftValue);
      }
      //read next operator
      char nextOperator = s.charAt(rightBracketI + 2);

      // determine what to do with the right value
      if (previousOperator == '+') {
        long newSum = sumSoFar + leftValue;
        return evaluateLtoRWithPrecedence(s.substring(rightBracketI + 3).trim(), newSum, nextOperator);
      }

      if (previousOperator == '*') {
        Long returnVal = evaluateLtoRWithPrecedence(s.substring(rightBracketI + 3).trim(), leftValue, nextOperator);
        return sumSoFar * returnVal;
      }
    }

    return null;
  }

  private int findCorrespondingRightBracket(String s) {
    for (int i = 1, encounteredLeftBrackets = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        encounteredLeftBrackets++;
      } else if (s.charAt(i) == ')') {
        if (encounteredLeftBrackets == 0) {
          return i;
        }
        encounteredLeftBrackets--;
      }
    }
    return -1;
  }
}
