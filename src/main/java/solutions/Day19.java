package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utils.Coordinate;
import utils.Day;

public class Day19 implements Day {
  List<Coordinate> scannerPositions = new ArrayList<>();

  @Override
  public String part1(List<String> input) {
    List<List<Coordinate>> scanners = parseInput(input);
    boolean[] matched = new boolean[scanners.size()];
    for (int i = 0; ; ) {
      for (int j = 0; j < scanners.size(); j++) {
        if (i == j) {
          continue;
        }

        Optional<List<Coordinate>> match = findMatch(scanners.get(i), scanners.get(j));

        if (match.isPresent()) {
          matched[i] = true;
          matched[j] = true;
          scanners.set(j, match.get());
          //System.out.println("matched");
        }
      }

      int matches = 0;
      for (int k = 0; k < matched.length; k++) {
        if (matched[k]) {
          matches++;
        }
      }

      if (matches == scanners.size()) {
        break;
      } else {
        do {
          i = i + 1 == matched.length ? 0 : i + 1;
        } while(!matched[i]);
      }
    }

    Set<Coordinate> finalCoordinates = scanners.stream()
        .flatMap(List::stream)
        .collect(Collectors.toSet());

    return String.valueOf(finalCoordinates.size());
  }

  private Optional<List<Coordinate>> findMatch(List<Coordinate> anchorScanner, List<Coordinate> testScanner) {
    Map<Integer, List<Coordinate>> orientations = getMapOfPossibleOrientations(testScanner);
    for (int i = 0; i < orientations.size(); i++) {
      //for each orientation
      List<Coordinate> coordinatesForOrientation = orientations.get(i);
      //check numberOfOverlapBeaconsAssumingFirstBeaconIsTheSame;
      for (int j = 0; j < anchorScanner.size(); j++) {
        Coordinate anchor = anchorScanner.get(j);
        for (int k = 0; k < coordinatesForOrientation.size(); k++) {
          Coordinate candidateCoordinate = coordinatesForOrientation.get(k);
          int originX = anchor.getX() - candidateCoordinate.getX();
          int originY = anchor.getY() - candidateCoordinate.getY();
          int originZ = anchor.getZ() - candidateCoordinate.getZ();

          List<Coordinate> testCoordinates = coordinatesForOrientation.stream()
              .map((c) -> new Coordinate(originX + c.getX(), originY + c.getY(), originZ + c.getZ()))
              .collect(Collectors.toList());
          List<Coordinate> overlaps = testCoordinates.stream()
              .filter(anchorScanner::contains)
              .collect(Collectors.toList());

          if (overlaps.size() > 11) {
            scannerPositions.add(new Coordinate(originX, originY, originZ));
            return Optional.of(testCoordinates);
          }
        }
      }
    }
    return Optional.empty();
  }

  private Map<Integer, List<Coordinate>> getMapOfPossibleOrientations(List<Coordinate> scanner) {
    Map<Integer, List<Coordinate>> orientationToCoordinateMap = new HashMap<>();
    for (int i = 0; i < scanner.size(); i++) {
      List<Coordinate> rotations = getOrientations(scanner.get(i));
      for (int j = 0; j < rotations.size(); j++) {
        if (orientationToCoordinateMap.containsKey(j)) {
          orientationToCoordinateMap.get(j).add(rotations.get(j));
        } else {
          List<Coordinate> coordinates = new ArrayList<>();
          coordinates.add(rotations.get(j));
          orientationToCoordinateMap.put(j, coordinates);
        }

      }
    }
    return orientationToCoordinateMap;
  }

  public List<Coordinate> getOrientations(Coordinate c) {
    List<Coordinate> coordinates = new ArrayList<>();
    coordinates.add(new Coordinate(c.getX(), c.getY(), c.getZ()));
    coordinates.add(new Coordinate(-c.getY(), c.getX(), c.getZ()));
    coordinates.add(new Coordinate(-c.getX(), -c.getY(), c.getZ()));
    coordinates.add(new Coordinate(c.getY(), -c.getX(), c.getZ()));

    coordinates.add(new Coordinate(-c.getX(), c.getY(), -c.getZ()));
    coordinates.add(new Coordinate(c.getY(), c.getX(), -c.getZ()));
    coordinates.add(new Coordinate(c.getX(), -c.getY(), -c.getZ()));
    coordinates.add(new Coordinate(-c.getY(), -c.getX(), -c.getZ()));

    coordinates.add(new Coordinate(-c.getZ(), c.getY(), c.getX()));
    coordinates.add(new Coordinate(-c.getZ(), c.getX(), -c.getY()));
    coordinates.add(new Coordinate(-c.getZ(), -c.getY(), -c.getX()));
    coordinates.add(new Coordinate(-c.getZ(), -c.getX(), c.getY()));

    coordinates.add(new Coordinate(c.getZ(), c.getY(), -c.getX()));
    coordinates.add(new Coordinate(c.getZ(), c.getX(), c.getY()));
    coordinates.add(new Coordinate(c.getZ(), -c.getY(), c.getX()));
    coordinates.add(new Coordinate(c.getZ(), -c.getX(), -c.getY()));

    coordinates.add(new Coordinate(c.getX(), -c.getZ(), c.getY()));
    coordinates.add(new Coordinate(-c.getY(), -c.getZ(), c.getX()));
    coordinates.add(new Coordinate(-c.getX(), -c.getZ(), -c.getY()));
    coordinates.add(new Coordinate(c.getY(), -c.getZ(), -c.getX()));

    coordinates.add(new Coordinate(c.getX(), c.getZ(), -c.getY()));
    coordinates.add(new Coordinate(-c.getY(), c.getZ(), -c.getX()));
    coordinates.add(new Coordinate(-c.getX(), c.getZ(), c.getY()));
    coordinates.add(new Coordinate(c.getY(), c.getZ(), c.getX()));

    return new ArrayList<>(coordinates);
  }

  private List<List<Coordinate>> parseInput(List<String> input) {
    List<List<Coordinate>> scanners = new ArrayList<>();
    List<Coordinate> scannedInput = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      if (input.get(i).startsWith("--- ")) {
        scannedInput = new ArrayList<>();
        continue;
      }

      if (input.get(i).isBlank()) {
        scanners.add(scannedInput);
        continue;
      }

      Pattern p = Pattern.compile("(?<first>-?\\d+),(?<second>-?\\d+),(?<third>-?\\d+)");
      Matcher m = p.matcher(input.get(i));
      if (m.matches()) {
        int first = Integer.parseInt(m.group("first"));
        int second = Integer.parseInt(m.group("second"));
        int third = Integer.parseInt(m.group("third"));
        scannedInput.add(new Coordinate(first, second, third));
      }
    }

    scanners.add(scannedInput);
    return scanners;
  }

  @Override
  public String part2(List<String> input) {
    int max = 0;
    for (int i = 0; i < scannerPositions.size(); i++) {
      for (int j = 1; j < scannerPositions.size(); j++) {
        int distance = Math.abs(scannerPositions.get(i).getX() - scannerPositions.get(j).getX())
            + Math.abs(scannerPositions.get(i).getY() - scannerPositions.get(j).getY())
            + Math.abs(scannerPositions.get(i).getZ() - scannerPositions.get(j).getZ());
        max = Math.max(distance, max);
      }

    }
    return String.valueOf(max);
  }
}
