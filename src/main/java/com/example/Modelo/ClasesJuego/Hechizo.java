package com.example.Modelo.ClasesJuego;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Hechizos")

/**
 * Clase que representa un hechizo en el juego.
 */
public class Hechizo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    int id;

    private String nombre;

    // Constructor
    public Hechizo() {
    }

    public Hechizo(String nombre) {
        this.nombre = nombre;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void efecto(List<Monstruo> monstruos) {
        // Método a ser implementado por las subclases
    }
}
