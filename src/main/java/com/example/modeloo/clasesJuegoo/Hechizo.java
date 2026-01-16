package com.example.modeloo.clasesJuegoo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase Hechizo que representa un hechizo en el juego.
 */
@Entity
@Table(name = "hechizos")
public class Hechizo implements Serializable {
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String nombre;

    /**
     * Constructor por defecto
     */
    public Hechizo() {}

    /**
     * Constructor de la clase Hechizo.
     * @param nombre
     */
    public Hechizo(String nombre) {
        this.nombre = nombre;
    }

    // GETTERS Y SETTERS
    /**
     * Obtiene el id del hechizo.
     * @return
     */
    public int getId() { return id; }

    /**
     * Obtiene el nombre del hechizo.
     * @return
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el daño que hace este hechizo por monstruo.
     */
    public int getDanho() {
        return 0;
    }

    /**
     * Establece el nombre del hechizo.
     * @param nombre
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    // MÉTODOS
    /**
     * Aplica el efecto del hechizo sobre los monstruos.
     * Devuelve un Map donde la clave es el monstruo afectado
     * y el valor es el daño infligido.
     * 
     * @param monstruos
     * @return
     */
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos){
        return Map.of();
    }


    // IMPRESCINDIBLE para contains()
    /**
     * Compara este hechizo con otro objeto.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hechizo)) return false;
        Hechizo hechizo = (Hechizo) o;
        return nombre.equals(hechizo.nombre);
    }

    /**
     * Genera el código hash del hechizo.
     * @return
     */
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    /**
     * Representación en cadena del hechizo.
     * @return
     */
    @Override
    public String toString() {
        return nombre;
    }

}
