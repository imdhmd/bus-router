package com.imdhmd.goeuro.br;

import com.imdhmd.goeuro.br.data.BusRoutes;
import com.imdhmd.goeuro.br.service.QueryRoutes;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
  private static final Logger LOG = Logger.getLogger(Main.class);

  public static void main(String... args) {

    String dataFile = args[0];

    try {

      final BusRoutes busRoutes = BusRoutes.from(new FileInputStream(dataFile));
      final QueryRoutes queryRoutes = new QueryRoutes(busRoutes);
      final App app = new App(8080, queryRoutes);

      app.start();

    } catch (IOException e) {
      LOG.error("Unable to read from data file: " + dataFile, e);
    } catch (NumberFormatException e) {
      LOG.error("Data file contains invalid data", e);
    }
  }
}
