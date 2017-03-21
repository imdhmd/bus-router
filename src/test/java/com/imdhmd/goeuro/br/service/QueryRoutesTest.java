package com.imdhmd.goeuro.br.service;

import com.imdhmd.goeuro.br.data.BusRoutes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
  public void shouldSayThatRouteExistsIfThereIsAtleastOneRouteWithBothTheStations() {
    final BusRoutes busRoutesContainingStation3 = mock(BusRoutes.class);
    final BusRoutes busRoutesContainingStation3And6 = mock(BusRoutes.class);

    when(busRoutes.filterByStationId(3))
            .thenReturn(busRoutesContainingStation3);
    when(busRoutesContainingStation3.filterByStationId(6))
            .thenReturn(busRoutesContainingStation3And6);
    when(busRoutesContainingStation3And6.count())
            .thenReturn(1);


    assertThat(queryRoutes.routeExists(3, 6))
            .isTrue();
  }
}