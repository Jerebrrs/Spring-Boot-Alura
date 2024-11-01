package com.aluracurso.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporada(
       @JsonAlias("Season") Integer temporadas,
       @JsonAlias("Episodes") List<DatosEpisode> episodios
) {

}
