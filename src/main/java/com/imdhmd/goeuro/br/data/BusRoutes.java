package com.imdhmd.goeuro.br.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class BusRoutes {

  private List<Set<String>> routes;

  private BusRoutes(List<Set<String>> routes) {
    this.routes = routes;
  }

  public static BusRoutes from(InputStream in) throws IOException {
    final List<Set<String>> routes = new ArrayList<>();
    final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    reader.readLine();
    String next = reader.readLine();

    while (next != null) {
      Set<String> nextRoute = stream(next.split("\\s"))
              .skip(1)
              .collect(toSet());

      routes.add(nextRoute);

      next = reader.readLine();
    }

    return new BusRoutes(routes);
  }

  public BusRoutes filterByStationId(Integer stationId) {
    return new BusRoutes(routes.stream()
            .filter(route -> route.contains(stationId.toString()))
            .collect(toList()));
  }

  public Integer count() {
    return routes.size();
  }
}
