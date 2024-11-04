package com.aluracursos.DesafioBooks.principal;

import com.aluracursos.DesafioBooks.model.Datos;
import com.aluracursos.DesafioBooks.model.DatosLibro;
import com.aluracursos.DesafioBooks.services.ConsumoApi;
import com.aluracursos.DesafioBooks.services.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner log = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos convierteDatos= new ConvierteDatos();
    
    public void muestraElMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);

        var datos = convierteDatos.obtenerDatos(json, Datos.class);

        System.out.println(datos);


        System.out.println("Top 10 libros Descargados.");

        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibro::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        System.out.println("*++++++++++++++++++++++++++++++++++++++");
        //Busqueda por titulo
        System.out.println("Ingrese el nombre del libro que desa buscar.");
        var tituloLibro = log.nextLine();

        json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));

        var datosBusqueda = convierteDatos.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase())).findFirst();

        if(libroBuscado.isPresent()){
            System.out.println("Libro Encontrado.");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("El libro no a sidfo encontrado.");
        }
        System.out.println("*++++++++++++++++++++++++++++++++++++++");

        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d->d.numeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibro::numeroDeDescargas));

        System.out.println("Cantidad media de descargas: "+ est.getAverage());
        System.out.println("Cantidad maxima de descargas: "+ est.getMax());
        System.out.println("Cantidad minima de descargas: "+ est.getMin());
        System.out.println("Cantidad de registros para calcular las estadisticas: "+ est.getCount());
    }}