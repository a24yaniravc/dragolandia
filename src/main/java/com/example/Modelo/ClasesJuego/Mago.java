package com.example.Modelo.ClasesJuego;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Magos")

/**
 * Clase Mago que representa un mago en el juego.
 */
public class Mago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID

    private  int id;
    private  String nombre;
    private  int vida;
    private  int nivelMagia;

    // Constructor
    // Constructor vacío requerido por Hibernate
    public Mago(){}

    // Constructor con parámetros
    public Mago(String nombre, int vida, int nivelMagia) {
        this.nombre = nombre;

        // Asegurar que la vida no sea menor o igual a 0
        if (vida <= 0) {
            this.vida = 1;
        } else this.vida = vida;

        this.nivelMagia = nivelMagia;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getNivelMagia() {
        return nivelMagia;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setNivelMagia(int nivelMagia) {
        this.nivelMagia = nivelMagia;
    }
    
    // MÉTODOS
    /**
     * Lanza un hechizo contra un monstruo, reduciendo su vida.
     * @param monstruo
     */
    public void lanzarHechizo(Monstruo monstruo) {
        monstruo.setVida(monstruo.getVida()-nivelMagia);
    }

    // TO STRING
    @Override
    public String toString() {
        return "Mago [id=" + id + ", nombre=" + nombre + ", vida=" + vida + ", nivelMagia=" + nivelMagia + "]";
    }
}
