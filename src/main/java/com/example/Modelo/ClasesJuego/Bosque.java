package com.example.Modelo.ClasesJuego;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bosques")

/**
 * Clase Bosque que representa un bosque en el juego.
 */
public class Bosque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int id;
    
    @Column(unique = true, nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private int nivelPeligro;
    
    @ManyToMany
    @JoinTable(
        name = "bosque_monstruo",
        joinColumns = @JoinColumn(name = "bosque_id"),
        inverseJoinColumns = @JoinColumn(name = "monstruo_id")
    )
    private List<Monstruo> monstruos = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE) // Relación muchos a uno con Monstruo
    private Monstruo monstruoJefe;
    
    @OneToOne (cascade = CascadeType.MERGE) // Relación uno a uno con Dragon
    private Dragon dragon;

    // CONSTRUCTORES
    // Constructor vacío requerido por Hibernate
    public Bosque(){}

    // Constructor con parámetros
    public Bosque(String nombre, int nivelPeligro, List<Monstruo> monstruos, Monstruo monstruoJefe, Dragon dragon) {
        this.nombre = nombre;
        this.nivelPeligro = nivelPeligro;
        this.monstruos = monstruos;
        this.monstruoJefe = monstruoJefe;
        this.dragon = dragon;
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

    public List<Monstruo> getMonstruos() {
        return monstruos;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNivelPeligro(int nivelPeligro) {
        this.nivelPeligro = nivelPeligro;
    }

    /**
     * Establece la lista de monstruos del bosque.
     * Si el monstruo jefe no está en la lista de monstruos, se añade a la lista.
     * @param monstruos
     */
    public void setMonstruos(List<Monstruo> monstruos) {
        this.monstruos = monstruos;
        if (!monstruos.contains(monstruoJefe)) {
            monstruos.add(monstruoJefe);
        }
    }

    /**
     * Establece el monstruo jefe del bosque.
     * Si el monstruo jefe no está en la lista de monstruos, se añade a la lista.
     * @param monstruoJefe
     */
    public void setMonstruoJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;

        if (monstruos != null && !monstruos.contains(monstruoJefe)) {
            monstruos.add(monstruoJefe);
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

    public void addMonstruo(Monstruo monstruo) {
        this.monstruos.add(monstruo);
    }

    /**
     * Cambia el monstruo jefe del bosque.
     * @param monstruo
     */
    public void cambiarJefe(Monstruo monstruo) {
        setMonstruoJefe(monstruo);
    }
}
