package com.example.modelo.clasesJuego;
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
    /**
     * Devuelve el id del bosque.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre del bosque.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el nivel de peligro del bosque.
     * @return
     */
    public int getNivelPeligro() {
        return nivelPeligro;
    }

    /**
     * Devuelve el monstruo jefe del bosque.
     * @return
     */
    public Monstruo getMonstruoJefe() {
        return monstruoJefe;
    }

    /**
     * Devuelve la lista de monstruos del bosque.
     * @return
     */
    public List<Monstruo> getMonstruos() {
        return monstruos;
    }

    /**
     * Devuelve el dragón asociado al bosque.
     * @return
     */
    public Dragon getDragon() {
        return dragon;
    }

    // SETTERS
    /**
     * Establece el nombre del bosque.
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el nivel de peligro del bosque.
     * @param nivelPeligro
     */
    public void setNivelPeligro(int nivelPeligro) {
        this.nivelPeligro = nivelPeligro;
    }

    /**
     * Establece el dragón asociado al bosque.
     * @param dragon
     */
    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
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

    // MÉTODOS

    /**
     * Muestra información del jefe del bosque.
     * @return
     */
    public String mostrarJefe() {
        return "El jefe del bosque es " + monstruoJefe.getNombre() + " y su vida es " + monstruoJefe.getVida();
    }

    /**
     * Añade un monstruo a la lista de monstruos del bosque.
     * @param monstruo
     */
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

    // TO STRING
    @Override
    public String toString() {
        return "Bosque [id=" + id + ", nombre=" + nombre + ", nivelPeligro=" + nivelPeligro + ", monstruoJefe="
                + monstruoJefe + "]";
    }

}
