package com.imdhmd.goeuro.br.service;

import com.imdhmd.goeuro.br.data.BusRoutes;

public class QueryRoutes {
  final private BusRoutes busRoutes;

  public QueryRoutes(BusRoutes busRoutes) {
    this.busRoutes = busRoutes;
  }

  public boolean doesDirectRouteExist(final Integer departureStationId, final Integer arrivalStationId) {
    return busRoutes
            .directRoutes(departureStationId, arrivalStationId)
            .size() > 0;
  }
}
