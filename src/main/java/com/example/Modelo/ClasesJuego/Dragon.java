package com.example.Modelo.ClasesJuego;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Dragones")

/**
 * Clase Dragon que representa un dragón en el juego.
 */
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID

    private int id;
    private String nombre;
    private int intensidadFuego; // Poder de ataque del dragón
    private int resistencia; // Vida del dragón

    // Constructor
    /**
     * Constructor vacío requerido por Hibernate
     */
    public Dragon() {
    }

    /**
     * Constructor con parámetros
     * @param nombre
     * @param intensidadFuego
     * @param resistencia
     */
    public Dragon(String nombre, int intensidadFuego, int resistencia) {
        this.nombre = nombre;
        this.intensidadFuego = intensidadFuego;
        this.resistencia = resistencia;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIntensidadFuego() {
        return intensidadFuego;
    }

    public int getResistencia() {
        return resistencia;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIntensidadFuego(int intensidadFuego) {
        this.intensidadFuego = intensidadFuego;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    // MÉTODOS

    /**
     * El dragón exhala fuego sobre un monstruo, 
     * reduciendo su vida según la intensidad del fuego.
     * @param monstruo
     */
    public void exhalar(Monstruo monstruo) {
        monstruo.setVida(monstruo.getVida() - intensidadFuego);
    }
}
