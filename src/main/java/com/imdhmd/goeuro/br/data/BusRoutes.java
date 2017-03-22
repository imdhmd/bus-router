package com.imdhmd.goeuro.br.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

import static com.imdhmd.goeuro.br.data.Pair.pair;
import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public class BusRoutes {

  private final Map<Pair<Integer>, Set<Integer>> directRoutes;

  private BusRoutes(Map<Pair<Integer>, Set<Integer>> directRoutes) {
    this.directRoutes = directRoutes;
  }

  public static BusRoutes from(InputStream in) throws IOException {
    final Map<Pair<Integer>, Set<Integer>> directRoutes = new HashMap<>();
    final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    reader.readLine();
    String next = reader.readLine();

    while (next != null) {
      Integer[] routeIdAndStationIds = stream(next.split("\\s")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
      Integer routeId = routeIdAndStationIds[0];
      Integer[] stationIds = stream(routeIdAndStationIds).skip(1).toArray(Integer[]::new);

      stream(stationIds)
              .flatMap(nextStationId -> makePairs(nextStationId, stationIds))
              .forEach(pair -> addDirectRoute(pair, routeId, directRoutes));

      next = reader.readLine();
    }

    return new BusRoutes(directRoutes);
  }

  public Set<Integer> directRoutes(Integer dep, Integer arr) {
    return unmodifiableSet(
            Optional.ofNullable(directRoutes.get(pair(dep, arr)))
                    .orElse(emptySet()));
  }

  private static Stream<Pair<Integer>> makePairs(Integer stationId, Integer[] stationIds) {
    return stream(stationIds)
            .map(otherStationId -> pair(otherStationId, stationId))
            .filter(p -> !p.equals(pair(stationId, stationId)));
  }

  private static void addDirectRoute(Pair<Integer> pair, Integer routeId, Map<Pair<Integer>, Set<Integer>> directRoutes) {
    if (directRoutes.containsKey(pair)) {

      directRoutes.get(pair).add(routeId);

    } else {

      directRoutes.put(pair, new HashSet<Integer>() {{
        add(routeId);
      }});

    }
  }
}
