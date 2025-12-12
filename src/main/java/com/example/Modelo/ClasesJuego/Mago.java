package com.example.Modelo.ClasesJuego;

import java.util.List;

import com.example.Modelo.ClasesJuego.Hechizos.Hechizo;

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

    private int id;
    private String nombre;
    private int vida;
    private int nivelMagia;
    private List<Hechizo> conjuros = new java.util.ArrayList<>();

    // Constructor
    // Constructor vacío requerido por Hibernate
    public Mago() {
    }

    // Constructor con parámetros
    public Mago(String nombre, int vida, int nivelMagia) {
        this.nombre = nombre;

        // Asegurar que la vida no sea menor o igual a 0
        if (vida <= 0) {
            this.vida = 1;
        } else
            this.vida = vida;

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

    public List<Hechizo> getConjuros() {
        return conjuros;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la vida del mago, asegurando que no sea menor a 0.
     * @param vida
     */
    public void setVida(int vida) {
        if (vida <= 0) {
            this.vida = 0;
        } else {
            this.vida = vida;
        }
    }

    public void setNivelMagia(int nivelMagia) {
        this.nivelMagia = nivelMagia;
    }

    public void setConjuros(List<Hechizo> conjuros) {
        this.conjuros = conjuros;
    }

    // MÉTODOS
    /**
     * Lanza un hechizo contra un monstruo, reduciendo su vida.
     * 
     * @param monstruo
     */
    public void lanzarHechizo(Monstruo monstruo) {
        monstruo.setVida(monstruo.getVida() - nivelMagia);
    }

    /**
     * Lanza un hechizo específico contra un monstruo, reduciendo su vida si el mago conoce el hechizo.
     * De lo contrario, el mago pierde 1 punto de vida.
     * @param monstruo
     * @param hechizo
     */
    public void lanzarHechizo(List<Monstruo> monstruos, Hechizo hechizo) {
        if (conjuros.contains(hechizo)) {
            hechizo.efecto(monstruos);
        } else {
            setVida(getVida()-1);
        }
    }

    // TO STRING
    @Override
    public String toString() {
        return "Mago [id=" + id + ", nombre=" + nombre + ", vida=" + vida + ", nivelMagia=" + nivelMagia + "]";
    }
}
