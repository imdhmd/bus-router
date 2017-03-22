package com.imdhmd.goeuro.br;

import com.google.gson.Gson;
import com.imdhmd.goeuro.br.response.DirectRouteResponse;
import com.imdhmd.goeuro.br.response.ErrorResponse;
import com.imdhmd.goeuro.br.service.QueryRoutes;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class App {
  private final Integer port;
  private final QueryRoutes queryRoutes;
  private final Gson gson;

  public App(Integer port, QueryRoutes queryRoutes) {
    this.port = port;
    this.queryRoutes = queryRoutes;
    this.gson = new Gson();
  }

  public void start() {
    port(port);

    get(
      "/api/direct",

      (request, response) -> {
        final Integer departureStationId = parseInt(request.queryParams("dep_sid"));
        final Integer arrivalStationId = parseInt(request.queryParams("arr_sid"));
        final boolean routeExists = queryRoutes.routeExists(departureStationId, arrivalStationId);

        response.type("application/json");

        return new DirectRouteResponse(departureStationId, arrivalStationId, routeExists);
      },

      gson::toJson
    );

    exception(NumberFormatException.class, (e, request, response) -> {
      response.type("application/json");
      response.status(400);
      response.body(gson.toJson(new ErrorResponse("Invalid request")));
    });
  }
}
