package com.imdhmd.goeuro.br.data;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.imdhmd.goeuro.br.data.StationPair.pair;
import static java.util.Arrays.stream;
import static org.apache.log4j.Logger.getLogger;

public class BusRoutesFactory {

  private static final Logger LOG = getLogger(BusRoutes.class);

  public static BusRoutes from(InputStream in) throws IOException {
    LOG.info("Loading data file for bus routes");

    final BusRoutes busRoutes = new BusRoutes();
    final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    reader.readLine();
    String next = reader.readLine();

    while (next != null) {
      Integer[] routeIdAndStationIds = stream(next.split("\\s")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
      Integer routeId = routeIdAndStationIds[0];
      Integer[] stationIds = stream(routeIdAndStationIds).skip(1).toArray(Integer[]::new);

      stream(stationIds)
              .flatMap(nextStationId -> makePairs(nextStationId, stationIds))
              .forEach(pair -> busRoutes.addDirectRoute(pair, routeId));

      next = reader.readLine();
    }

    LOG.info("Finished loading data file for bus routes");
    return busRoutes;
  }

  private static Stream<StationPair> makePairs(Integer stationId, Integer[] stationIds) {
    return stream(stationIds)
            .filter(otherStationId -> !otherStationId.equals(stationId))
            .map(otherStationId -> pair(otherStationId, stationId));
  }
}
