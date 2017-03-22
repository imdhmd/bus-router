package com.imdhmd.goeuro.br;

import com.imdhmd.goeuro.br.data.BusRoutes;
import com.imdhmd.goeuro.br.service.QueryRoutes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
  public static void main(String... args) throws FileNotFoundException {

    final BusRoutes busRoutes = BusRoutes.from(new FileInputStream(args[0]));
    final QueryRoutes queryRoutes = new QueryRoutes(busRoutes);
    final App app = new App(8080, queryRoutes);

    app.start();
  }
}
