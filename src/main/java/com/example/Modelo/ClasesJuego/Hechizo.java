package com.example.Modelo.ClasesJuego;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hechizos")
public class Hechizo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String nombre;

    public Hechizo() {}

    public Hechizo(String nombre) {
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public void efecto(List<Monstruo> monstruos) {
        // implementación en subclases
    }

    // IMPRESCINDIBLE para contains()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hechizo)) return false;
        Hechizo hechizo = (Hechizo) o;
        return nombre.equals(hechizo.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    @Override
    public String toString() {
        return nombre;
    }

}
