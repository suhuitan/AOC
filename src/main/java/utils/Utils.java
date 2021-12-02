package utils;

import java.util.Arrays;
import java.util.List;

public class Utils {

  public static List<String> splitByNewLine(String input) {
    List<String> inputs = Arrays.asList(input.split("\n"));
    return inputs;
  }
}
