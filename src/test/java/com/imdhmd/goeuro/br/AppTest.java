package com.imdhmd.goeuro.br;

import com.google.gson.Gson;
import com.imdhmd.goeuro.br.response.DirectRouteResponse;
import com.imdhmd.goeuro.br.response.ErrorResponse;
import com.imdhmd.goeuro.br.service.QueryRoutes;
import org.junit.*;
import spark.Spark;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppTest {

  private static final int PORT = ThreadLocalRandom.current().nextInt(60001, 64999);
  private static final String BASE_URI = "http://localhost:" + PORT + "/api/direct?";

  private static final QueryRoutes queryRoutes = mock(QueryRoutes.class);
  private static final App app = new App(PORT, queryRoutes);
  private static final Gson gson = new Gson();
  private static final Client client = ClientBuilder.newClient();

  @BeforeClass
  public static void setup() {
    app.start();
  }

  @Test
  public void shouldGetSuccessResponseForAnExistingDirectRoute() {
    when(queryRoutes.doesDirectRouteExist(3, 6))
            .thenReturn(true);

    String result = client.target(BASE_URI + "dep_sid=3&arr_sid=6")
            .request()
            .get(String.class);

    assertThat(gson.fromJson(result, DirectRouteResponse.class))
            .isEqualToComparingFieldByField(
                    new DirectRouteResponse(3, 6, true)
            );
  }

  @Test
  public void shouldGetSuccessResponseForANonExistingDirectRoute() {
    when(queryRoutes.doesDirectRouteExist(6, 8))
            .thenReturn(false);

    String result = client.target(BASE_URI + "dep_sid=6&arr_sid=8")
            .request()
            .get(String.class);

    assertThat(gson.fromJson(result, DirectRouteResponse.class))
            .isEqualToComparingFieldByField(
                    new DirectRouteResponse(6, 8, false)
            );
  }

  @Test
  public void shouldGetErrorResponseForInvalidRequest() {
    Response result = client.target(BASE_URI + "dep_sid=invalid&arr_sid=8")
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
    when(queryRoutes.doesDirectRouteExist(4, 8))
            .thenThrow(new RuntimeException());

    Response result = client.target(BASE_URI + "dep_sid=4&arr_sid=8")
            .request()
            .get();

    assertThat(result.getStatus()).isEqualTo(500);
    assertThat(gson.fromJson(result.readEntity(String.class), ErrorResponse.class))
            .isEqualToComparingFieldByField(
                    new ErrorResponse("Internal server error")
            );
  }

  @AfterClass
  public static void tearDown() {
    Spark.stop();
  }
}