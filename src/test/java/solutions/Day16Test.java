package solutions;

import static utils.Utils.splitByNewLine;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Day16Test {

  private Day16 underTest;
  private String literalValue = "D2FE28";
  private String operatorPacket = "38006F45291200";
  private String operatorPacket2 = "EE00D40C823060";

  @BeforeMethod
  public void setup() {
    underTest = new Day16();
  }

  @Test
  public void parseLiteralPacket() {
    String bin = underTest.hex2Binary(literalValue);
    Pair<Day16.Packet, String> pair = underTest.parsePacket(bin);
    Day16.LiteralPacket packet = (Day16.LiteralPacket) pair.getLeft();
    Assert.assertEquals(packet.version, 6);
    Assert.assertEquals(packet.type, 4);
    Assert.assertEquals(packet.value, 2021);
    Assert.assertEquals(pair.getRight(), "000");
  }

  @Test
  public void parseOperatorPacket() {
    String bin = underTest.hex2Binary(operatorPacket);
    Pair<Day16.Packet, String> pair = underTest.parsePacket(bin);

    Day16.OperatorPacket packet = (Day16.OperatorPacket) pair.getLeft();
    Assert.assertEquals(packet.version, 1);
    Assert.assertEquals(packet.type, 6);
    Assert.assertEquals(packet.packets.size(), 2);
  }

  @Test
  public void parseOperatorPacket2() {
    String bin = underTest.hex2Binary(operatorPacket2);
    Pair<Day16.Packet, String> pair = underTest.parsePacket(bin);

    Day16.OperatorPacket packet = (Day16.OperatorPacket) pair.getLeft();
    Assert.assertEquals(packet.version, 7);
    Assert.assertEquals(packet.type, 3);
    Assert.assertEquals(packet.packets.size(), 3);
  }

  @Test
  public void testPart1() {
    String result = underTest.part1(Collections.singletonList("8A004A801A8002F478"));
    Assert.assertEquals(result, "16");
    result = underTest.part1(Collections.singletonList("620080001611562C8802118E34"));
    Assert.assertEquals(result, "12");
    result = underTest.part1(Collections.singletonList("C0015000016115A2E0802F182340"));
    Assert.assertEquals(result, "23");
    result = underTest.part1(Collections.singletonList("A0016C880162017C3686B18A3D4780"));
    Assert.assertEquals(result, "31");
  }

  @Test
  public void testPart2() {
    String result = underTest.part2(Collections.singletonList("C200B40A82"));
    Assert.assertEquals(result, "3");
    result = underTest.part2(Collections.singletonList("04005AC33890"));
    Assert.assertEquals(result, "54");
    result = underTest.part2(Collections.singletonList("880086C3E88112"));
    Assert.assertEquals(result, "7");
    result = underTest.part2(Collections.singletonList("CE00C43D881120"));
    Assert.assertEquals(result, "9");
    result = underTest.part2(Collections.singletonList("D8005AC2A8F0"));
    Assert.assertEquals(result, "1");
    result = underTest.part2(Collections.singletonList("F600BC2D8F"));
    Assert.assertEquals(result, "0");
    result = underTest.part2(Collections.singletonList("9C005AC2F8F0"));
    Assert.assertEquals(result, "0");
    result = underTest.part2(Collections.singletonList("9C0141080250320F1802104A08"));
    Assert.assertEquals(result, "1");
  }
}