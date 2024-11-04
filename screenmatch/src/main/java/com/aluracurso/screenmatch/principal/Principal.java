package com.aluracurso.screenmatch.principal;

import com.aluracurso.screenmatch.model.DatosEpisode;
import com.aluracurso.screenmatch.model.DatosSerie;
import com.aluracurso.screenmatch.model.DatosTemporada;

import com.aluracurso.screenmatch.model.Episodio;
import com.aluracurso.screenmatch.service.ConsumoApi;
import com.aluracurso.screenmatch.service.ConvierteDatos;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

//        temporadas.forEach(t ->t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//        List<DatosEpisode> datosEpisodios= temporadas.stream().flatMap(t -> t.episodios().stream()).toList();

        //Top 5 Episodios
//        System.out.println("Top 5 Episodios");
//        datosEpisodios.stream().filter(t-> !t.evaluaciones().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primer Filtro (N/A)" + e))
//                .sorted(Comparator.comparing(DatosEpisode::evaluaciones).reversed()).peek(e-> System.out.println("Segundo Filtro (M>m)" + e)).map(e->e.titulo().toUpperCase())
//                .limit(5).peek(e-> System.out.println("Tercer Filtro (Limit)" + e))
//                .forEach(System.out::println);

        //Convirtiendo los Datos a una lista de tipo Episodios.


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t-> t.episodios().stream()
                        .map(d-> new Episodio(t.temporadas(),d)))
                .collect(Collectors.toList());

//        episodios.forEach(System.out::println);



        //Busqueda de episodios a partir de X años
//        System.out.println("Indica el año a partir del cual deseas ver los Episodios.");
//        var fechaEpisodio= scanner.nextInt();
//        scanner.nextLine();

//        LocalDate fechabusqueda = LocalDate.of(fechaEpisodio, 1,1);
//
//        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("dd/MM/yyyy");
////
//        episodios.stream()
//                .filter(t-> t.getFechaDeLanzamiento() != null && t.getFechaDeLanzamiento().isAfter(fechabusqueda))
//                .forEach(t-> System.out.println(
//                        "Tempoorada: "+ t.getTemporada() +
//                                " Episodio: " + t.getTitulo() +
//                                " Fecha de Lanzamiento: "+ t.getFechaDeLanzamiento().format(dtf)
//                ));


        //Busca episodios por pedazo del titulo.

//        System.out.println("Por favor escriba el Titulo del Episodio que desea ver: ");
//        var pedazoTitulo = scanner.nextLine();
//
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("Los datos son: "+ episodioBuscado.get());
//        }else{
//            System.out.println("Episodio no encontrado!!");
//        }


        Map<Integer, Double> evalucionesPorTemporadas = episodios.stream().filter(e->e.getEvaluacion()> 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getEvaluacion)));

        System.out.println(evalucionesPorTemporadas);


        DoubleSummaryStatistics est = episodios.stream()
                .filter(e->e.getEvaluacion()>0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));

        System.out.println("Media de Evaluciones: "+ est.getAverage());
        System.out.println("Episodio mejor evaluado: "+ est.getMax());
        System.out.println("Episodio peor evaluado: "+ est.getMin());
    }









}
