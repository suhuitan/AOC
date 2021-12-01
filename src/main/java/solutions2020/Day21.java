package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Day;

public class Day21 implements Day {

  private final Pattern pattern = Pattern.compile("(?<foodList>.+) \\(contains (?<allergens>.+)\\)");

  @Override
  public String part1(List<String> input) {
    Map<String, Integer> ingredients = new HashMap<>();
    Map<String, Set<String>> possibleAllergens = findAllergens(input, ingredients);

    for (String allergen : possibleAllergens.keySet()) {
      System.out.println("allergen : " + allergen);
      for (String s : possibleAllergens.get(allergen)) {
        System.out.println("  " + s);
      }
    }

    for (String allergen : possibleAllergens.keySet()) {
      for (String poss : possibleAllergens.get(allergen)) {
        ingredients.remove(poss);
      }
    }

    Integer integer = ingredients.values()
        .stream()
        .reduce(Integer::sum)
        .get();
    return String.valueOf(integer);
  }

  public Map<String, Set<String>> findAllergens(List<String> input, Map<String, Integer> ingredientCount) {
    Map<String, Set<String>> possibleAllergens = new HashMap<>();

    for (String in : input) {
      Matcher matcher = pattern.matcher(in);
      if (matcher.matches()) {
        String foodList = matcher.group("foodList");
        List<String> ingredientList = new ArrayList(Arrays.asList(foodList.split(" ")));
        ingredientList.stream()
            .forEach((ingredient) -> ingredientCount.compute(ingredient, (k, v) -> v == null ? 1 : v + 1));

        String[] allergens = matcher.group("allergens").split(", ");
        for (int i = 0; i < allergens.length; i++) {
          possibleAllergens.compute(allergens[i], (k, v) -> {
            if (v == null) {
              v = new HashSet<>();
              v.addAll(ingredientList);
            }
            v.retainAll(ingredientList);
            return v;
          });
        }
      }
    }
    return possibleAllergens;
  }

  @Override
  public String part2(List<String> input) {
    Map<String, Integer> ingredients = new HashMap<>();
    Map<String, Set<String>> allergenToPossibleMap = findAllergens(input, ingredients);
    Map<String, String> allergenToIngredient = new HashMap<>();

    while (allergenToIngredient.size() < allergenToPossibleMap.size()) {
      for (Map.Entry<String, Set<String>> entrySet : allergenToPossibleMap.entrySet()) {
        if (entrySet.getValue().size() == 1) {
          String ingredient = entrySet.getValue().stream().findAny().get();
          allergenToIngredient.put(entrySet.getKey(), ingredient);
          allergenToPossibleMap.values().forEach((set) -> set.remove(ingredient));
        }
      }
    }

    StringJoiner joiner = new StringJoiner(",");
    allergenToIngredient.keySet()
        .stream().sorted()
        .forEach((allergen) -> joiner.add(allergenToIngredient.get(allergen)));

    return joiner.toString();
  }
}
