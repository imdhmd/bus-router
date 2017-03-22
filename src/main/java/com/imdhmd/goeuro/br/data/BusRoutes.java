package com.imdhmd.goeuro.br.data;

import java.util.*;

import static com.imdhmd.goeuro.br.data.StationPair.pair;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public class BusRoutes {
  private final Map<StationPair, Set<Integer>> directRoutes;

  BusRoutes() {
    directRoutes = new HashMap<>();
  }

  public Set<Integer> directRoutes(Integer dep, Integer arr) {
    return unmodifiableSet(
            Optional.ofNullable(directRoutes.get(pair(dep, arr)))
                    .orElse(emptySet()));
  }

  void addDirectRoute(StationPair stationPair, Integer routeId) {
    if (directRoutes.containsKey(stationPair)) {

      directRoutes.get(stationPair).add(routeId);

    } else {

      directRoutes.put(stationPair, new HashSet<Integer>() {{
        add(routeId);
      }});

    }
  }
}
