package com.imdhmd.goeuro.br.data;

import java.util.HashSet;
import java.util.Set;

public class Pair<A> {
  private final Set<A> val;

  public Pair(A one, A two) {
    this.val = new HashSet<A>() {{
      add(one);
      add(two);
    }};
  }

  public static <A> Pair<A> pair(A one, A two) {
    return new Pair<>(one, two);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Pair<?> pair = (Pair<?>) o;

    return !(val != null ? !val.equals(pair.val) : pair.val != null);

  }

  @Override
  public int hashCode() {
    return val != null ? val.hashCode() : 0;
  }
}
