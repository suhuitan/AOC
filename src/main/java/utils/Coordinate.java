package utils;

import java.util.Comparator;
import java.util.Objects;

public class Coordinate implements Comparable<Coordinate> {

  int x;
  int y;
  int z;

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coordinate(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Coordinate)) {
      return false;
    }
    Coordinate that = (Coordinate) o;
    return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY(), getZ());
  }

  @Override
  public int compareTo(Coordinate o) {
    int result = Integer.compare(x, o.x);
    if (result == 0) {
      result = Integer.compare(y, o.y);
      if (result == 0) {
        result = Integer.compare(z, o.z);
      }
    }
    return result;
  }
}
