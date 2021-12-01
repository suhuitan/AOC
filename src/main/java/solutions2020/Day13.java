package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import utils.Day;

public class Day13 implements Day {

  @Override
  public String part1(List<String> input) {
    int timestamp = Integer.valueOf(input.get(0));
    List<Integer> buses = Arrays.stream(input.get(1).split(","))
        .filter(s -> !s.equals("x"))
        .mapToInt(Integer::valueOf)
        .boxed()
        .collect(Collectors.toList());

    int i = 0;
    while (true) {
      int currentTime = timestamp + i;
      Optional<Integer> desiredBus = buses.stream()
          .filter((bus) -> currentTime % bus == 0)
          .findFirst();
      if (desiredBus.isPresent()) {
        return String.valueOf((currentTime - timestamp) * desiredBus.get());
      }
      i++;
    }

  }

  @Override
  public String part2(List<String> input) {
    List<Long> moduli = new ArrayList<>();
    List<Integer> residue = new ArrayList<>();

    int i = 0;
    for (String s : input.get(1).split(",")) {
      if (!s.equals("x")) {
        moduli.add(Long.valueOf(s));
        residue.add(Math.toIntExact(Long.valueOf(s) - i));
      }
      i++;
    }

    // something something chinese remainder theorem: http://homepages.math.uic.edu/~leon/mcs425-s08/handouts/chinese_remainder.pdf
    Long product = moduli.stream()
        .reduce(1L, (a, b) -> a * b);

    List<Long> productOfOtherInputs = moduli.stream()
        .map((n) -> product / n)
        .collect(Collectors.toList());

    List<Long> moduloInverses = new ArrayList<>();
    for (int j = 0; j < moduli.size(); j++) {
      Long inv = findModularInverse(productOfOtherInputs.get(j), moduli.get(j));
      moduloInverses.add(inv);
    }

    long result = 0;
    for (int j = 0; j < moduli.size(); j++) {
      result += Math
          .multiplyExact(Math.multiplyExact(moduloInverses.get(j), productOfOtherInputs.get(j)), residue.get(j));
    }

    return String.valueOf(result % product);
  }

  // find x where (a * x) % m == 1
  private Long findModularInverse(Long a, Long m) {
    a = a % m;
    for (long x = 1; x < m; x++) {
      if ((a * x) % m == 1) {
        return x;
      }
    }
    return 1L;
  }

}
