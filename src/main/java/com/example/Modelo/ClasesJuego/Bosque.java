package com.example.Modelo.ClasesJuego;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Bosques")

/**
 * Clase Bosque que representa un bosque en el juego.
 */
public class Bosque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int id;
    private String nombre;
    private int nivelPeligro;
    
    @OneToOne(cascade = CascadeType.ALL) // Relación uno a uno con Monstruo
    private Monstruo monstruoJefe;
    
    // CONSTRUCTORES
    // Constructor vacío requerido por Hibernate
    public Bosque(){}

    // Constructor con parámetros
    public Bosque(int id, String nombre, int nivelPeligro, Monstruo monstruoJefe) {
        this.id = id;
        this.nombre = nombre;
        this.nivelPeligro = nivelPeligro;
        this.monstruoJefe = monstruoJefe;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivelPeligro() {
        return nivelPeligro;
    }

    public Monstruo getMonstruoJefe() {
        return monstruoJefe;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNivelPeligro(int nivelPeligro) {
        this.nivelPeligro = nivelPeligro;
    }

    public void setMonstruoJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Bosque [id=" + id + ", nombre=" + nombre + ", nivelPeligro=" + nivelPeligro + ", monstruoJefe="
                + monstruoJefe + "]";
    }

    // MÉTODOS

    /**
     * Muestra información del jefe del bosque.
     * @return
     */
    public String mostrarJefe() {
        return "El jefe del bosque es " + monstruoJefe.getNombre() + " y su vida es " + monstruoJefe.getVida();
    }

    /**
     * Cambia el monstruo jefe del bosque.
     * @param monstruo
     */
    public void cambiarJefe(Monstruo monstruo) {
        setMonstruoJefe(monstruo);
    }
    
}
