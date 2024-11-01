package com.aluracurso.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {
    private Integer numeroTemporada;
    private  String titulo;
    private Integer numeroEpisodio;
    private  Double evaluacion;
    private LocalDate fechaDeLanzamiento;

    public Episodio(Integer temporadas, DatosEpisode d) {
        this.numeroTemporada =temporadas;
        this.titulo= d.titulo();
        this.numeroEpisodio= Integer.valueOf(d.numeroDeEpisodio());
        try {
            this.evaluacion= Double.valueOf(d.evaluaciones());
        }catch (NumberFormatException e){
            this.evaluacion=0.0;
        }
        try {
            this.fechaDeLanzamiento= LocalDate.parse(d.fechaDeLanzamiento());
        }catch (DateTimeException e){
            this.fechaDeLanzamiento =  null;
        }

    }

    public Integer getTemporada() {
        return numeroTemporada;
    }

    public void setTemporada(Integer temporada) {
        this.numeroTemporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }


    @Override
    public String toString() {
        return
                "numeroTemporada=" + numeroTemporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaDeLanzamiento=" + fechaDeLanzamiento ;
    }
}
