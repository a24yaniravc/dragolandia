package com.example.modeloo.clasesJuegoo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dragones")

/**
 * Clase Dragon que representa un dragón en el juego.
 */
public class Dragon {
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID

    private int id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int intensidadFuego; // Poder de ataque del dragón
    
    @Column(nullable = false)
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
    /**
     * Devuelve el ID del dragón.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre del dragón.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la intensidad del fuego del dragón.
     * @return
     */
    public int getIntensidadFuego() {
        return intensidadFuego;
    }

    /**
     * Devuelve la resistencia del dragón.
     * @return
     */
    public int getResistencia() {
        return resistencia;
    }

    /**
     * Establece el nombre del dragón.
     * @param nombre
     */
    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la intensidad del fuego del dragón.
     * @param intensidadFuego
     */
    public void setIntensidadFuego(int intensidadFuego) {
        this.intensidadFuego = intensidadFuego;
    }

    /**
     * Establece la resistencia del dragón.
     * @param resistencia
     */
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
