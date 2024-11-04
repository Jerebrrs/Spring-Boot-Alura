package com.aluracursos.DesafioBooks.services;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
