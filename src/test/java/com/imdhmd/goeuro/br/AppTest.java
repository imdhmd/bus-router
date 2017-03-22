package com.imdhmd.goeuro.br;

import com.google.gson.Gson;
import com.imdhmd.goeuro.br.response.DirectRouteResponse;
import com.imdhmd.goeuro.br.response.ErrorResponse;
import com.imdhmd.goeuro.br.service.QueryRoutes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Spark;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppTest {

  private final Gson gson = new Gson();
  private final QueryRoutes queryRoutes = mock(QueryRoutes.class);
  private final App app = new App(8080, queryRoutes);
  private final Client client = ClientBuilder.newClient();

  @Before
  public void setup() {
    app.start();
  }

  @Test
  public void shouldGetSuccessResponseForAnExistingDirectRoute() {
    when(queryRoutes.routeExists(3, 6))
            .thenReturn(true);

    String result = client.target("http://localhost:8080/api/direct?dep_sid=3&arr_sid=6")
            .request()
            .get(String.class);

    assertThat(gson.fromJson(result, DirectRouteResponse.class))
            .isEqualToComparingFieldByField(
                    new DirectRouteResponse(3, 6, true)
            );
  }

  @Test
  public void shouldGetSuccessResponseForANonExistingDirectRoute() {
    when(queryRoutes.routeExists(6, 8))
            .thenReturn(false);

    String result = client.target("http://localhost:8080/api/direct?dep_sid=6&arr_sid=8")
            .request()
            .get(String.class);

    assertThat(gson.fromJson(result, DirectRouteResponse.class))
            .isEqualToComparingFieldByField(
                    new DirectRouteResponse(6, 8, false)
            );
  }

  @Test
  public void shouldGetErrorResponseForInvalidRequest() {
    Response result =  client.target("http://localhost:8080/api/direct?dep_sid=invalid&arr_sid=8")
            .request()
            .get();

    assertThat(result.getStatus()).isEqualTo(400);
    assertThat(gson.fromJson(result.readEntity(String.class), ErrorResponse.class))
            .isEqualToComparingFieldByField(
                    new ErrorResponse("Invalid request")
            );
  }

  @Test
  public void shouldGetErrorResponseForInternalError() {
    when(queryRoutes.routeExists(4, 8))
            .thenThrow(new RuntimeException());

    Response result =  client.target("http://localhost:8080/api/direct?dep_sid=4&arr_sid=8")
            .request()
            .get();

    assertThat(result.getStatus()).isEqualTo(500);
    assertThat(gson.fromJson(result.readEntity(String.class), ErrorResponse.class))
            .isEqualToComparingFieldByField(
                    new ErrorResponse("Internal server error")
            );
  }


  @After
  public void teardown() {
    Spark.stop();
  }
}