package com.aluracurso.screenmatch.principal;


import java.util.Arrays;
import java.util.List;

public class EjemploStreams {

    public void muestraEjemplos() {
        List<String> nombres = Arrays.asList("Brenda","Simon","pepe","Maria");

        nombres.stream().sorted()
                .filter(n->n.startsWith("S"))
                .limit(2).map(n-> n.toUpperCase()).forEach(System.out::println);


    }
}
