package com.example.Modelo.ClasesJuego;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    
    @OneToMany(cascade = CascadeType.ALL) // Relación uno a N con Monstruo
    private List<Monstruo> monstruosJefes;

    @ManyToOne(cascade = CascadeType.ALL) // Relación muchos a uno con Monstruo
    private Monstruo monstruoJefe;
    
    // CONSTRUCTORES
    // Constructor vacío requerido por Hibernate
    public Bosque(){}

    // Constructor con parámetros
    public Bosque(int id, String nombre, int nivelPeligro, List<Monstruo> monstruosJefes) {
        this.id = id;
        this.nombre = nombre;
        this.nivelPeligro = nivelPeligro;
        this.monstruosJefes = monstruosJefes;

        // Monstruo jefe aleatorio del bosque
        this.monstruoJefe = monstruosJefes.get((int)(Math.random() * monstruosJefes.size()));
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

    public List<Monstruo> getMonstruosJefes() {
        return monstruosJefes;
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

    /**
     * Establece la lista de monstruos jefes del bosque.
     * Si el monstruo jefe actual no está en la nueva lista, se selecciona uno aleatoriamente.
     * @param monstruosJefes
     */
    public void setMonstruosJefes(List<Monstruo> monstruosJefes) {
        this.monstruosJefes = monstruosJefes;
        if (!monstruosJefes.contains(monstruoJefe)) {
            this.monstruoJefe = monstruosJefes.get((int)(Math.random() * monstruosJefes.size()));
        }
    }

    /**
     * Establece el monstruo jefe del bosque.
     * Si el monstruo jefe no está en la lista de monstruos jefes, se añade a la lista.
     * @param monstruoJefe
     */
    public void setMonstruoJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;

        if (!monstruosJefes.contains(monstruoJefe)) {
            this.monstruosJefes.add(monstruoJefe);
        }
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
