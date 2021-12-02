package solutions;

import java.util.List;
import java.util.stream.Collectors;

import utils.Day;

public class Day02 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Command> commands = extract(input);

    Integer hor = 0;
    Integer depth = 0;

    for (Command c : commands) {
      switch (c.getDirection()) {
        case FORWARD: hor += c.getValue(); break;
        case DOWN: depth += c.getValue(); break;
        case UP: depth -= c.getValue(); break;
      }
    }

    return String.valueOf(hor * depth);
  }

  private List<Command> extract(List<String> input) {
    return input.stream()
        .map((s) -> s.split(" "))
        .map((arr) -> new Command(DIRECTION.valueOf(arr[0].toUpperCase()), Integer.parseInt(arr[1])))
        .collect(Collectors.toList());
  }


  @Override
  public String part2(List<String> input) {
    List<Command> commands = extract(input);

    long hor = 0;
    long depth = 0;
    long aim = 0;

    for (Command c : commands) {
      switch (c.getDirection()) {
        case FORWARD:
          hor += c.getValue();
          depth += aim * c.getValue();
        break;
        case DOWN: aim += c.getValue(); break;
        case UP: aim -= c.getValue(); break;
      }
    }
    return String.valueOf(hor * depth);
  }

  public enum DIRECTION {
    FORWARD,
    DOWN,
    UP;
  }

  private class Command {

    public final DIRECTION direction;

    public final int value;
    private Command(DIRECTION direction, int value) {
      this.direction = direction;
      this.value = value;
    }

    public DIRECTION getDirection() {
      return direction;
    }

    public int getValue() {
      return value;
    }
  }
}
