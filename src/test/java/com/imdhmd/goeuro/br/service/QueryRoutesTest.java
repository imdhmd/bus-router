package com.imdhmd.goeuro.br.service;

import com.imdhmd.goeuro.br.data.BusRoutes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryRoutesTest {

  private QueryRoutes queryRoutes;

  @Mock
  private BusRoutes busRoutes;

  @Before
  public void setup() {
    queryRoutes = new QueryRoutes(busRoutes);
  }

  @Test
  public void shouldSayThatDirectRouteDoesNotExistWhenThereAreNoDirectRoutesBetweenTheStations() {
    when(busRoutes.directRoutes(3, 6))
            .thenReturn(emptySet());

    assertThat(queryRoutes.routeExists(3, 6))
            .isFalse();
  }

  @Test
  public void shouldSayThatRouteExistsIfThereIsAtleastOneDirectRouteBetweenTheStations() {
    when(busRoutes.directRoutes(3, 6))
            .thenReturn(singleton(1));

    assertThat(queryRoutes.routeExists(3, 6))
            .isTrue();
  }
}