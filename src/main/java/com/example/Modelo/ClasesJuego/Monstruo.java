package com.example.Modelo.ClasesJuego;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Monstruos")

/**
 * Clase Monstruo que representa un monstruo en el juego.
 */
public class Monstruo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int id;

    private String nombre;
    private int vida;
    private int fuerza;
    private enum Tipo { ogro, troll, espectro }
    private Tipo tipo;

    // CONSTRUCTORES

    /**
     * Constructor de la clase Monstruo.
     */
    public Monstruo(){}

    /**
     * Constructor con parámetros
     * @param nombre
     * @param vida
     * @param tipo
     * @param fuerza
     */
    public Monstruo(String nombre, int vida ,String tipo, int fuerza) {
        this.nombre = nombre;

        // Asegurar que la vida sea mayor a 0
        if (vida > 0) {
            this.vida = vida;
        } else {
            throw new IllegalArgumentException("La vida debe ser mayor a 0");
        }
        
        try {
            this.tipo = Tipo.valueOf(tipo.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de monstruo inválido: " + tipo);
        }
        
        this.fuerza = fuerza;
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

    public int getFuerza() {
        return fuerza;
    }

    public String getTipo() {
        return tipo.name();
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la vida del monstruo, asegurando que no sea menor a 0.
     * @param vida
     */
    public void setVida(int vida) {
        if (vida < 0) {
            this.vida = 0;
        } else {
            this.vida = vida;
        }
    }

    /**
     * Establece el tipo de monstruo.
     * @param tipo
     */
    public void setTipo(String tipo) {
        try {
            this.tipo = Tipo.valueOf(tipo.toLowerCase());
        } catch(IllegalStateException e) {
            System.out.println("Error en el tipo de monstruo" + e.getMessage());
        }
        
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    //Métodos
    /**
     * Ataca a un mago, reduciendo su vida.
     * @param mago
     */
    public void atacar(Mago mago) {
        mago.setVida(mago.getVida()-fuerza);
    }

    // TO STRING 
    @Override
    public String toString() {
        return "Monstruo [id=" + id + ", nombre=" + nombre + ", vida=" + vida + ", tipo=" + tipo + ", fuerza=" + fuerza
                + "]";
    }

}
