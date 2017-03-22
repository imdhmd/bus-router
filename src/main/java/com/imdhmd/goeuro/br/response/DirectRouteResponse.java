package com.imdhmd.goeuro.br.response;

import com.google.gson.annotations.SerializedName;

public class DirectRouteResponse {
  @SerializedName("dep_sid")
  private final Integer departureStationId;
  @SerializedName("arr_sid")
  private final Integer arrivalStationId;
  @SerializedName("direct_bus_route")
  private final boolean routeExists;

  public DirectRouteResponse(Integer departureStationId, Integer arrivalStationId, boolean routeExists) {
    this.departureStationId = departureStationId;
    this.arrivalStationId = arrivalStationId;
    this.routeExists = routeExists;
  }
}
