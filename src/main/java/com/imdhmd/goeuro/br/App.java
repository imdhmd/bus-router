package com.imdhmd.goeuro.br;

import com.google.gson.Gson;
import com.imdhmd.goeuro.br.response.DirectRouteResponse;
import com.imdhmd.goeuro.br.response.ErrorResponse;
import com.imdhmd.goeuro.br.service.QueryRoutes;
import org.apache.log4j.Logger;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.apache.log4j.LogManager.getLogger;
import static spark.Spark.*;

public class App {
  private static Logger LOG = getLogger(App.class);

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
        LOG.info(format("Direct bus route request received for dep_sid [%s] and arr_sid [%s]",
                request.queryParams("dep_sid"), request.queryParams("arr_sid")));

        final Integer departureStationId = parseInt(request.queryParams("dep_sid"));
        final Integer arrivalStationId = parseInt(request.queryParams("arr_sid"));
        final boolean routeExists = queryRoutes.routeExists(departureStationId, arrivalStationId);

        LOG.info(format("Sending direct bus route response for dep_sid [%s] and arr_sid [%s] as %s",
                departureStationId, arrivalStationId, routeExists));

        response.type("application/json");
        return new DirectRouteResponse(departureStationId, arrivalStationId, routeExists);
      },

      gson::toJson
    );

    exception(NumberFormatException.class, (e, request, response) -> {
      LOG.warn(format("Failed handling direct bus route request for dep_sid [%s] and arr_sid [%s]",
              request.queryParams("dep_sid"), request.queryParams("arr_sid")), e);

      response.type("application/json");
      response.status(400);
      response.body(gson.toJson(new ErrorResponse("Invalid request")));
    });

    exception(Exception.class, (e, request, response) -> {
      LOG.error("Internal server error while handling request", e);

      response.type("application/json");
      response.status(500);
      response.body(gson.toJson(new ErrorResponse("Internal server error")));
    });
  }
}
