package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Monstruo;

public class Hechizo {
    public String nombre;
    public void efecto(List<Monstruo> monstruos){};

    public Hechizo(String nombre) {
        this.nombre = nombre;
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}