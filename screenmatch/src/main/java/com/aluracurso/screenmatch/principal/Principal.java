package com.aluracurso.screenmatch.principal;

import com.aluracurso.screenmatch.model.DatosEpisode;
import com.aluracurso.screenmatch.model.DatosSerie;
import com.aluracurso.screenmatch.model.DatosTemporada;

import com.aluracurso.screenmatch.model.Episodio;
import com.aluracurso.screenmatch.service.ConsumoApi;
import com.aluracurso.screenmatch.service.ConvierteDatos;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();

    private final String URL_BASE= "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8c276616";

    public void  muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la Serie que deseas Buscar: ");
        var nombreSerie = scanner.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+API_KEY);


        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println("estos son los datos"+datos);

        List<DatosTemporada> temporadas = new ArrayList<>();


        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {

            json= consumoApi.obtenerDatos(URL_BASE+ nombreSerie.replace(" ","+")+"&Season="+i+API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);

            temporadas.add(datosTemporadas);
        }

//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisode> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        //Expreciones Lambdas

        temporadas.forEach(t ->t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DatosEpisode> datosEpisodios= temporadas.stream().flatMap(t -> t.episodios().stream()).toList();

        //Top 5 Episodios
        System.out.println("Top 5 Episodios");
        datosEpisodios.stream().filter(t-> !t.evaluaciones().equalsIgnoreCase("N/A")).sorted(Comparator.comparing(DatosEpisode::evaluaciones).reversed()).limit(5).forEach(System.out::println);

        //Convirtiendo los Datos a una lista de tipo Episodios.


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t-> t.episodios().stream()
                        .map(d-> new Episodio(t.temporadas(),d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);



    }









}
