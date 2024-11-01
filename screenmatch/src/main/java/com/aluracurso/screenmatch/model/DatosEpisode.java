package com.aluracurso.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisode(
       @JsonAlias("Title") String titulo,
       @JsonAlias("Episode")  String numeroDeEpisodio,
       @JsonAlias("imdbRating")  String evaluaciones,
       @JsonAlias("Released")  String fechaDeLanzamiento
) {
}
