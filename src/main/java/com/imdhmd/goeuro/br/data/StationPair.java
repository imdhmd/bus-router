package com.imdhmd.goeuro.br.data;

public class StationPair {

  private final Integer dep;
  private final Integer arr;

  public StationPair(Integer dep, Integer arr) {
    this.dep = dep;
    this.arr = arr;
  }

  public static StationPair pair(Integer dep, Integer arr) {
    return new StationPair(dep, arr);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StationPair that = (StationPair) o;

    if (dep != null ? !dep.equals(that.dep) : that.dep != null) return false;
    return !(arr != null ? !arr.equals(that.arr) : that.arr != null);

  }

  @Override
  public int hashCode() {
    int result = dep != null ? dep.hashCode() : 0;
    result = 31 * result + (arr != null ? arr.hashCode() : 0);
    return result;
  }
}
