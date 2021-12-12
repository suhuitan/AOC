package solutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import utils.Day;

public class Day12 implements Day {
  Map<String, Node> nodeMap = new HashMap<>();
  Set<String> paths = new HashSet<>();

  @Override
  public String part1(List<String> input) {
    input.stream()
        .forEach(this::parseInput);

    Node start = nodeMap.get("start");
    Node end = nodeMap.get("end");
    startFrom(start, end, (path1, connectedNode1) -> visitCriteria1(connectedNode1, path1));

    return String.valueOf(paths.size());
  }

  private void parseInput(String s) {
    String[] split = s.split("-");

    Node a = nodeMap.computeIfAbsent(split[0], Node::new);
    Node b = nodeMap.computeIfAbsent(split[1], Node::new);

    a.addConnectedNode(b);
    b.addConnectedNode(a);

  }

  private void startFrom(Node startingNode, Node target,
      BiFunction<List<Node>, Node, Boolean> visitCriteria) {
    Queue<List<Node>> queue = new LinkedList<>();
    List<Node> path = new ArrayList<>();
    path.add(startingNode);
    queue.add(path);

    while(!queue.isEmpty()) {
      path = queue.remove();
      Node lastNode = path.get(path.size() - 1);
      if (lastNode.equals(target)) {
        paths.add(path.stream().map(Node::getName).collect(Collectors.joining(",")));
      } else {
        for (Node connectedNode : lastNode.getConnectedNodes()) {
          if (visitCriteria.apply(path, connectedNode)) {
            List<Node> newPath = new ArrayList<>(path);
            newPath.add(connectedNode);
            queue.add(newPath);
          }
        }
      }
    }

  }

  private boolean visitCriteria1(Node node, List<Node> path) {
    if (node.isBigCave() && path.stream().filter((n) -> n.equals(node)).count() < 20) {
      return true;
    }

    return path.stream().noneMatch((n) -> n.equals(node));
  }

  @Override
  public String part2(List<String> input) {
    input.stream()
        .forEach(this::parseInput);

    Node start = nodeMap.get("start");
    Node end = nodeMap.get("end");
    startFrom(start, end, (path1, connectedNode1) -> visitCriteria2(connectedNode1, path1));

    return String.valueOf(paths.size());
  }

  private boolean visitCriteria2(Node node, List<Node> path) {
    if(path.stream().anyMatch((n) -> n.equals(nodeMap.get("start"))) && node.equals(nodeMap.get("start"))) {
      return false;
    }

    if (node.isBigCave() && path.stream().filter((n) -> n.equals(node)).count() < 20) {
      return true;
    }

    Map<Node, Long> currentCounts = path.stream()
        .filter((n) -> !n.isBigCave())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // if there is already a small cave visited twice
    if (currentCounts.containsValue(2L)) {
      return path.stream().noneMatch((n) -> n.equals(node));
    }

    return path.stream().filter((n) -> n.equals(node)).count() < 2;

  }

  private class Node {
    private Set<Node> connectedNodes = new HashSet<>();
    private String name;

    public Node(String name) {
      this.name = name;
    }

    public void addConnectedNode(Node node) {
      connectedNodes.add(node);
    }
    public Set<Node> getConnectedNodes() {
      return connectedNodes;
    }

    public String getName() {
      return name;
    }

    public boolean isBigCave() {
      return StringUtils.isAllUpperCase(name);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (!(o instanceof Node)) {
        return false;
      }

      Node node = (Node) o;

      return new EqualsBuilder().append(getName(), node.getName()).isEquals();
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 37).append(getName()).toHashCode();
    }
  }
}
