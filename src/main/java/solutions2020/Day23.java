package solutions2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import utils.Day;

public class Day23 implements Day {

  @Override
  public String part1(List<String> inputs) {
    String[] input = inputs.get(0).split("");
    Map<Integer, Node> nodes = new HashMap<>();

    Node head = getCircularLL2(input, nodes, input.length);

    head = crabPlays(head, 100, nodes);

    while (head.value != 1) {
      head = head.next;
    }

    return getOrderFromHead(head.next).substring(0, 8);
  }

  public Node crabPlays(Node head, int totalMoves, Map<Integer, Node> nodes) {
    for (int i = 0; i < totalMoves; i++) {
      Node destination = getDestination(head, nodes, nodes.size());
      Node nodeAfterDestination = destination.next;
      Node pickupStart = head.next;
      Node pickupEnd = pickupStart.next.next;

      head.next = pickupEnd.next;
      pickupEnd.next.prev = head;

      destination.next = pickupStart;
      pickupStart.prev = destination;

      pickupEnd.next = nodeAfterDestination;
      nodeAfterDestination.prev = pickupEnd;
      head = head.next;

//      System.out.println(String.format("-- move %d --", i + 2));
//      System.out.println("cups: " + getOrderFromHead(head));
    }
    return head;
  }

  private Node getDestination(Node head, Map<Integer, Node> nodes, int maxValue) {
    int currentLabel = head.value;
    Node current = head;
    int i = 0;
    int target = currentLabel - 1 == 0 ? maxValue : currentLabel - 1;

    while (true) {
      Node maybeTarget = nodes.get(target);
      if (within3OfHead(head, maybeTarget)) {
        target = target - 1 == 0 ? maxValue : target - 1;
        continue;
      }
      return maybeTarget;
    }
  }

  private boolean within3OfHead(Node head, Node maybeTarget) {
    for (int i = 0; i < 3; i++) {
      head = head.next;
      if (head == maybeTarget) {
        return true;
      }
    }
    return false;
  }

  private String getOrderFromHead(Node head) {
    Node current = head;
    StringJoiner join = new StringJoiner("");
    do {
      join.add(String.valueOf(current.value));
      current = current.next;
    } while (current != head);

    return join.toString();
  }

  @Override
  public String part2(List<String> inputs) {
    String[] input = inputs.get(0).split("");
    Map<Integer, Node> nodes = new HashMap<>();
    Node head = getCircularLL2(input, nodes, 1000001);

    head = crabPlays(head, 10000000, nodes);

    head = nodes.get(1);
    long v1 = head.next.value;
    long v2 = head.next.next.value;

    return String.valueOf(v1 * v2);
  }

  public Node getCircularLL2(String[] input, Map<Integer, Node> nodes, int maxVal) {
    Node head = new Node(input[0]);
    Node current = head;
    nodes.put(current.value, current);
    for (int i = 1; i < input.length; i++) {
      current.next = new Node(input[i]);
      current.next.prev = current;
      current = current.next;
      nodes.put(current.value, current);
    }

    for (int i = 10; i < maxVal; i++) {
      current.next = new Node(i);
      current.next.prev = current;
      current = current.next;
      nodes.put(current.value, current);
    }

    current.next = head;
    return head;
  }

  public class Node {

    Node next;
    Node prev;
    int value;

    public Node(String value) {
      this.value = Integer.parseInt(value);
    }

    public Node(int i) {
      this.value = i;
    }
  }
}
