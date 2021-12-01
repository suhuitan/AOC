package solutions2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Day;

public class Day04 implements Day {

  @Override
  public String part1(List<String> input) {
    List<Passport> passports = readPassports(input);
    long count = passports.stream()
        .filter(Passport::hasAllRequiredFields)
        .count();
    return String.valueOf(count);
  }

  @Override
  public String part2(List<String> input) {
    List<Passport> passports = readPassports(input);
    long count = passports.stream()
        .filter(Passport::hasAllRequiredFields)
        .filter(Passport::hasValidFields)
        .count();
    return String.valueOf(count);
  }

  private List<Passport> readPassports(List<String> input) {
    StringJoiner joiner = new StringJoiner(" ", "", "");
    List<Passport> passports = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      String in = input.get(i);
      joiner.add(in);
      
      if (in.isEmpty() || i == input.size() - 1) {
        Passport passport = new Passport(joiner.toString());
        joiner = new StringJoiner(" ", "", "");
        passports.add(passport);
      }

    }
    return passports;
  }

  private static class Passport {

    Map<String, Boolean> fields = new HashMap<>();
    Set<String> requiredFields = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    public Passport(String input) {
      Matcher matcher = Pattern.compile(String.format("(?<field>(\\S+)):(?<value>(\\S+))"))
          .matcher(input);

      while (matcher.find()) {
        String f = matcher.group("field");
        String v = matcher.group("value");
        fields.put(f, validate(f, v));
      }
    }

    public boolean hasAllRequiredFields() {
      return fields.keySet().containsAll(requiredFields);
    }

    public boolean hasValidFields() {
      return fields.values().stream().allMatch(Boolean::booleanValue);
    }


    private boolean validate(String name, String value) {
      switch (name) {
        case "byr":
          return validateYear(Integer.valueOf(value), 1920, 2002);
        case "iyr":
          return validateYear(Integer.valueOf(value), 2010, 2020);
        case "eyr":
          return validateYear(Integer.valueOf(value), 2020, 2030);
        case "hgt":
          return validateHeight(value);
        case "hcl":
          return validateHairColor(value);
        case "ecl":
          return validateEyeColor(value);
        case "pid":
          return validatePID(value);

        default:
          return true;
      }
    }

    private boolean validatePID(String value) {
      return value.matches("\\d{9}");
    }

    private boolean validateEyeColor(String value) {
      return value.matches("amb|blu|brn|gry|grn|hzl|oth");
    }

    private boolean validateHairColor(String value) {
      return value.matches("#[a-f0-9]{6}");
    }

    private boolean validateHeight(String value) {
      Integer numericalValue;

      try {
        numericalValue = Integer.valueOf(value.substring(0, value.length() - 2));
      } catch (NumberFormatException e) {
        return false;
      }

      String unit = value.substring(value.length() - 2);
      if (unit.equals("cm")) {
        return numericalValue >= 150 & numericalValue <= 193;
      }
      if (unit.equals("in")) {
        return numericalValue >= 59 & numericalValue <= 76;
      }
      return false;
    }

    private boolean validateYear(Integer year, int min, int max) {
      return year >= min & year <= max;
    }

  }
}

