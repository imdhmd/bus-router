package com.imdhmd.goeuro.br.data;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BusRoutesTest {

  private BusRoutes busRoutes;

  @Before
  public void setup() throws IOException {
    InputStream in = new ByteArrayInputStream(("" +
            "3\n" +
            "0 0 1 2 3 4\n" +
            "1 3 1 6 5\n" +
            "2 0 6 4").getBytes());

    busRoutes = BusRoutes.from(in);
  }

  @Test
  public void shouldReturnAllDirectRoutesConnectingGivenTwoStations() {
    assertThat(busRoutes
            .directRoutes(1, 3))
            .containsExactly(0, 1);

    assertThat(busRoutes
            .directRoutes(0, 6))
            .containsExactly(2);
  }

  @Test
  public void shouldReturnAnEmptyResultWhenNoRoutesPresent() {
    assertThat(busRoutes
            .directRoutes(6, 2))
            .isEmpty();
  }
}