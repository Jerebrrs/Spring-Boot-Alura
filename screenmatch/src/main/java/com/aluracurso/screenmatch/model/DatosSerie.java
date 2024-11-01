package com.aluracurso.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias("Title")String title,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluaciones) {
}
