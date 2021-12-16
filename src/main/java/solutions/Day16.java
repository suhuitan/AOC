package solutions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.Day;

public class Day16 implements Day {

  @Override
  public String part1(List<String> input) {
    String in = hex2Binary(input.get(0));

    Pair<Packet, String> outer = parsePacket(in);

    return String.valueOf(outer.getLeft().getVersionSum());
  }

  String hex2Binary(String input) {
    String in = input.chars()
        .mapToObj((c) -> String.valueOf((char) c))
        .map((s) -> Integer.parseInt(s, 16))
        .map(Integer::toBinaryString)
        .map((s) -> String.format("%4s", s).replace(' ','0'))
        .collect(Collectors.joining());
    return in;
  }

  Pair<Packet, String> parsePacket(String in) {
    int version = Integer.parseInt(in.substring(0,3), 2);
    int type = Integer.parseInt(in.substring(3,6), 2);

    if (type == 4) {
      in = in.substring(6);
      boolean isLastGroup = false;
      StringBuilder builder = new StringBuilder();
      while(!isLastGroup) {
        String bitsToConsider =  in.substring(0, 5);
        in = in.substring(5);
        if (bitsToConsider.charAt(0) == '0') {
          isLastGroup = true;
        }
        builder.append(bitsToConsider.substring(1));
      }
      long value = Long.parseLong(builder.toString(), 2);
      return Pair.of(new LiteralPacket(version, type, value), in);
    }

    int lengthType = Character.getNumericValue(in.charAt(6));
    in = in.substring(7);
    List<Packet> containingPackets = new ArrayList<>();

    if (lengthType == 0) {
      int numberOfBitsToRead = Integer.parseInt(in.substring(0, 15), 2);
      String toConsider = in.substring(15, 15 + numberOfBitsToRead);
      do {
        Pair<Packet, String> containingPacket = parsePacket(toConsider);
        containingPackets.add(containingPacket.getLeft());
        toConsider = containingPacket.getRight();
      } while(!toConsider.replace("0", "").isEmpty());
      in = in.substring(15 + numberOfBitsToRead);

    } else if (lengthType == 1) {
      int numberOfPackets = Integer.parseInt(in.substring(0, 11), 2);
      in = in.substring(11);
      for (int i = 0; i < numberOfPackets; i++) {
        Pair<Packet, String> containingPacket = parsePacket(in);
        containingPackets.add(containingPacket.getLeft());
        in = containingPacket.getRight();
      }
    }

    return Pair.of(new OperatorPacket(version, type, containingPackets), in);
  }

  @Override
  public String part2(List<String> input) {
    String in = hex2Binary(input.get(0));

    Pair<Packet, String> outer = parsePacket(in);

    return String.valueOf(outer.getLeft().evaluate());
  }

  abstract class Packet {
    int version;
    int type;

    public Packet(int version, int type) {
      this.version = version;
      this.type = type;
    }

    abstract long getVersionSum();

    abstract BigInteger evaluate();
  }

  class LiteralPacket extends Packet {
    long value;

    public LiteralPacket(int version, int type, long value) {
      super(version, type);
      this.value = value;
    }

    @Override
    long getVersionSum() {
      return version;
    }

    @Override
    BigInteger evaluate() {
      return BigInteger.valueOf(value);
    }
  }

  class OperatorPacket extends Packet {
    List<Packet> packets;

    public OperatorPacket(int version, int type, List<Packet> packets) {
      super(version, type);
      this.packets = packets;
    }

    @Override
    long getVersionSum() {
      return version + packets.stream().map(Packet::getVersionSum).reduce(Long::sum).get();
    }

    BiFunction<BigInteger, BigInteger, BigInteger> getOperator() {
      switch (type) {
        case 0: return (x, y) -> x.add(y);
        case 1: return (x, y) -> x.multiply(y);
        case 2: return (x, y) -> x.min(y);
        case 3: return (x, y) -> x.max(y);
        case 5: return (x, y) -> x.compareTo(y) > 0 ? BigInteger.ONE : BigInteger.ZERO;
        case 6: return (x, y) -> x.compareTo(y) < 0 ? BigInteger.ONE : BigInteger.ZERO;
        case 7: return (x, y) -> x.compareTo(y) == 0 ? BigInteger.ONE : BigInteger.ZERO;
      }
      throw new IllegalArgumentException("wrong type");
    }


    @Override
    BigInteger evaluate() {
      BiFunction<BigInteger, BigInteger, BigInteger> operator = getOperator();
      BigInteger result = packets.stream()
          .map(Packet::evaluate)
          .reduce(operator::apply)
          .get();
      return result;
    }
  }
}
