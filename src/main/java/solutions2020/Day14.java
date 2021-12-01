package solutions2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.Day;


public class Day14 implements Day {

  private static final Pattern pattern = Pattern.compile("mem\\[(?<address>\\d+)\\] = (?<value>\\d+)");


  @Override
  public String part1(List<String> input) {
    Map<Integer, Long> memory = new HashMap<>();
    long ANDmask = 0L;
    long ORmask = 0L;

    for (String s : input) {
      if (s.matches("mask = [X01]+")) {
        ANDmask = parseANDMask(s.substring(7));
        ORmask = parseORMask(s.substring(7));
      } else {
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
          int address = Integer.parseInt(matcher.group("address"));
          long value = Long.parseLong(matcher.group("value"));
          value = value & ANDmask;
          value = value | ORmask;

          memory.put(address, value);
        }
      }
    }

    Long sum = memory.values().stream().reduce(Long::sum).get();
    return String.valueOf(sum);
  }

  private long parseANDMask(String maskInput) {
    long maskValue = Double.valueOf(Math.pow(2, 36) - 1).longValue();

    int i = maskInput.indexOf("0");
    while (i > -1) {
      maskValue -= Double.valueOf(Math.pow(2, maskInput.length() - i - 1)).longValue();
      i = maskInput.indexOf("0", i + 1);
    }

    return maskValue;
  }

  private long parseORMask(String maskInput) {
    long maskValue = 0L;

    int i = maskInput.indexOf("1");
    while (i > -1) {
      maskValue += Double.valueOf(Math.pow(2, maskInput.length() - i - 1)).longValue();
      i = maskInput.indexOf("1", i + 1);
    }

    return maskValue;
  }

  @Override
  public String part2(List<String> input) {
    Map<Long, Long> memory = new HashMap<>();
    String mask = "";
    for (String s : input) {
      if (s.matches("mask = [X01]+")) {
        mask = s.substring(7);
      } else {
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
          long address = Long.parseLong(matcher.group("address"));
          long value = Long.parseLong(matcher.group("value"));
          List<Long> destinations = applyFloatingMask(mask, address);
          destinations.forEach((add) -> memory.put(add, value));
        }
      }
    }
    Long sum = memory.values().stream().reduce(Long::sum).get();
    return String.valueOf(sum);

  }

  private List<Long> applyFloatingMask(String mask, Long addressInput) {
    // get padded binary string
    String addressBinary = String.format("%36s", Long.toBinaryString(addressInput)).replace(' ', '0');
    List<Long> possibleAddresses = new ArrayList<>();
    possibleAddresses.add(0L);

    for (int i = 0; i <= 35; i++) {
      char addressC = addressBinary.charAt( 35 - i);
      char maskC = mask.charAt(35 - i);

      // if either mask or address bit is a 1, add 2^i to all elements in the list
      // if mask is an X, then create a new list from original list and add 2^i to it. append new list to old list
      // from example, this will result in something like
      // start: [0]
      // mask[0] == 'X' : [0] + [0 + 2^0] = [0, 1]
      // mask[1] == '1', address[1] == '1' : [0 + 2^1 , 1 + 2^1]
      // mask[2] == '0' : [2 , 3]
      // mask[3] == '0', address[1] == '1'  : [2 + 2^3, 3 + 2^3]
      // mask[4] == '1', address[1] == '0'  : [10 + 2^4, 11 + 2^4]
      // mask[5] == 'X'  : [26, 27] + [26 + 2^5, 27 + 2^5]
      // mask[35..6] == '0, address[1] == '0' : [26, 27, 58, 59]

      if (maskC == 'X') {
        long valForI = Double.valueOf(Math.pow(2, i)).longValue();
        List<Long> newValues = possibleAddresses.stream()
            .map(n -> n + valForI)
            .collect(Collectors.toList());
        possibleAddresses.addAll(newValues);

      } else if (maskC == '1' || addressC == '1') {
        long valForI = Double.valueOf(Math.pow(2, i)).longValue();

        possibleAddresses.replaceAll((n) -> n + valForI);

      }

    }
    return possibleAddresses;
  }

}


