package solutions2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;

import utils.Day;

public class Day10 implements Day {

  @Override
  public String part1(List<String> input) {
    TreeSet<Integer> sortedInputs = getListOfJoltages(input);
    Map<Integer, Integer> differences = new HashMap<>();

    for (Integer sortedInput : sortedInputs) {
      Integer lower = sortedInputs.lower(sortedInput) == null ? 0 : sortedInputs.lower(sortedInput);
      int diff = sortedInput - lower;
      differences.compute(diff, (k, v) -> v == null ? 1 : v + 1);
    }

    return String.valueOf(differences.get(1) * (differences.get(3)));
  }

  private TreeSet<Integer> getListOfJoltages(List<String> input) {
    TreeSet<Integer> sortedInputs = input.stream()
        .map(Integer::valueOf)
        .collect(Collectors.toCollection(TreeSet::new));
    sortedInputs.add(0);

    int deviceJoltage = sortedInputs.last() + 3;
    sortedInputs.add(deviceJoltage);
    return sortedInputs;
  }

  @Override
  public String part2(List<String> input) {
    TreeSet<Integer> sortedInputs = getListOfJoltages(input);
    List<SortedSet<Integer>> partitions = new ArrayList<>();
    int startInt = sortedInputs.first();

    for (Integer integer : sortedInputs) {
      Integer higher = sortedInputs.higher(integer) == null ? integer : sortedInputs.higher(integer);
      int compare = Integer.compare(higher, integer + 3);
      if (compare == 0) {
        partitions.add(sortedInputs.subSet(startInt, higher));
        startInt = higher;
      }
    }

    long totalPaths = 1L;

    for (SortedSet<Integer> currentSearch : partitions) {
      if (currentSearch.size() <= 2) {
        continue;
      }
      int paths = 0;
      paths = getPathsDFS(currentSearch, paths);

      totalPaths *= paths;
    }

    return String.valueOf(totalPaths);
  }

  private int getPathsDFS(SortedSet<Integer> currentSearch, int paths) {
    Stack<Integer> stack = new Stack<>();
    int max = currentSearch.last();
    stack.push(currentSearch.first());

    while(!stack.isEmpty()){
      int start = stack.pop();
      for (int j = 1; j < 4; j++) {
        if (start == max) {
          paths++;
          break;
        } else if (currentSearch.contains(start + j)) {
          stack.push(start + j);
        }
      }
    }
    return paths;
  }

}
