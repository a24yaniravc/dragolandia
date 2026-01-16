package com.example.modeloo.clasesJuegoo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "monstruos")

/**
 * Clase Monstruo que representa un monstruo en el juego.
 */
public class Monstruo {
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int vida;
    
    @Column(nullable = false)
    private int fuerza;
    
    public enum Tipo { ogro, troll, espectro } // Tipos de monstruos
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
    public Monstruo(String nombre, int vida, String tipo, int fuerza) {
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
    /**
     * Devuelve el ID del monstruo.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre del monstruo.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la vida del monstruo.
     * @return
     */
    public int getVida() {
        return vida;
    }

    /**
     * Devuelve la fuerza del monstruo.
     * @return
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * Devuelve el tipo de monstruo.
     * @return
     */
    public String getTipo() {
        return tipo.name();
    }

    // SETTERS
    /**
     * Establece el nombre del monstruo.
     * @param nombre
     */
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

    /**
     * Establece la fuerza del monstruo.
     * @param fuerza
     */
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
