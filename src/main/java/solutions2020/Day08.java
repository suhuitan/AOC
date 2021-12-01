package solutions2020;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import utils.Day;

public class Day08 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Inst> instructions = input.stream()
        .map(Inst::new)
        .collect(Collectors.toList());

    int instIndex = returnAccumulatorWhenHitPredicate(instructions, Inst::encountered);
    return String.valueOf(instIndex);
  }

  private int returnAccumulatorWhenHitPredicate(List<Inst> instructions, Predicate<Inst> predicate) {
    int executedCount = 0;
    int index = 0;
    int accumulator = 0;

    while(executedCount <= instructions.size()) { // might be a little conservative but what the hell.

      //hacking to use predicate coz lazy to restructure
      Inst curr = index >= instructions.size() ? null : instructions.get(index);

      if (predicate.test(curr)) {
        return accumulator;
      }

      switch (curr.operation) {
        case JMP:
          index += curr.arg;
          break;
        case ACC:
          accumulator += curr.arg; // deliberately fallthrough so index gets incremented
        case NOP:
          index++;
      }

      curr.setEncountered();
      executedCount++;
    }

    throw new IllegalArgumentException("causes infinite recursion");
  }

  @Override
  public String part2(List<String> input) {
    List<Inst> instructions = input.stream()
        .map(Inst::new)
        .collect(Collectors.toList());

    for(Inst inst : instructions) {
      if (inst.operation != OPERATION.ACC) {
        // flip and try one by one. there's probably a more efficient way
        inst.flipOperation();
        try {
          int instIndex = returnAccumulatorWhenHitPredicate(instructions, Objects::isNull);
          return String.valueOf(instIndex);

        } catch (IllegalArgumentException e) {
          inst.flipOperation(); // remember to flip instruction back
        }
      }
    }

    return "didn't get anywhere";
  }

  private static class Inst {
    OPERATION operation;
    int arg;
    private boolean encountered;

    Inst(String instruction) {
      String[] input = instruction.split(" ");
      operation = OPERATION.valueOf(input[0].toUpperCase());
      arg = Integer.parseInt(input[1]);
    }

    public void flipOperation(){
      if (operation == OPERATION.NOP) {
        operation = OPERATION.JMP;
        return;
      }

      if (operation == OPERATION.JMP) {
        operation = OPERATION.NOP;
      }
    }

    public boolean encountered() {
      return encountered;
    }

    public void setEncountered() {
      this.encountered = true;
    }
  }
  public enum OPERATION {
    NOP,
    ACC,
    JMP;
  }
}
