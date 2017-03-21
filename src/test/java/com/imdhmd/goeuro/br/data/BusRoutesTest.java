package com.imdhmd.goeuro.br.data;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BusRoutesTest {
  @Test
  public void shouldBuildBusRoutesWithTheDataFromInputAndGiveUsTheCorrectCountOfRoutes() {
    InputStream in = new ByteArrayInputStream(("" +
            "3\n" +
            "0 0 1 2 3 4\n" +
            "1 3 1 6 5\n" +
            "2 0 6 4").getBytes());

    assertThat(BusRoutes
            .from(in)
            .count()).isEqualTo(3);
  }
}