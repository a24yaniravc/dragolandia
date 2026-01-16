package com.example.modeloo;

import java.util.List;

import com.example.modeloo.clasesJuegoo.Bosque;
import com.example.modeloo.clasesJuegoo.Dragon;
import com.example.modeloo.clasesJuegoo.Hechizo;
import com.example.modeloo.clasesJuegoo.Mago;
import com.example.modeloo.clasesJuegoo.Monstruo;
import com.example.vistaa.Vista;

/**
 * Clase Modelo que representa el modelo del juego.
 */
public class Modelo {
    // Atributos
    private static Modelo instancia; // Instancia única de la clase Modelo (SINGLETON)
    private Vista view; // Vista asociada al modelo

    // Listas de entidades disponibles en el juego
    private List<Monstruo> listaMonstruos = new java.util.ArrayList<>();
    private List<Mago> listaMagos = new java.util.ArrayList<>();
    private List<Bosque> listaBosques = new java.util.ArrayList<>();
    private List<Hechizo> listaHechizos = new java.util.ArrayList<>();
    private List<Dragon> listaDragones = new java.util.ArrayList<>();

    // Entidades seleccionadas para el juego
    private Monstruo monstruoJefe;
    private List<Mago> magos = new java.util.ArrayList<>();
    private List<Monstruo> monstruos = new java.util.ArrayList<>();
    private Bosque bosque;
    private Dragon dragon;
    private Hechizo hechizo;

    // CONSTRUCTORES

    /**
     * Constructor de la clase Modelo.
     */
    public Modelo() {
        // Inicialización de listas
        this.listaMonstruos = new java.util.ArrayList<>();
        this.listaMagos = new java.util.ArrayList<>();
        this.listaBosques = new java.util.ArrayList<>();
        this.listaHechizos = new java.util.ArrayList<>();
        this.listaDragones = new java.util.ArrayList<>();

        // Inicialización de Vista
        this.view = new Vista();
    }

    /**
     * Obtiene la instancia única de la clase Modelo (SINGLETON).
     * 
     * @return
     */
    public static Modelo getInstancia() {
        if (instancia == null) {
            synchronized (Modelo.class) {
                if (instancia == null) {
                    instancia = new Modelo();
                }
            }
        }
        return instancia;
    }

    /**
     * Establece la vista asociada al modelo.
     * 
     * @param view
     */
    public void setVista(Vista view) {
        this.view = view;
    }

    /**
     * Inicializa el juego seleccionando el bosque, los magos y los monstruos.
     */
    public final void inicializarJuego() {
    if (bosque != null && view != null) {

        this.monstruoJefe = bosque.getMonstruoJefe();

        this.magos = view.seleccionMago(listaMagos);

        // ya es una lista normal
        this.monstruos = bosque.getMonstruos();

    } else {
        view.imprimirMensaje(
            "Error: No se puede inicializar el juego."
        );
    }
}

    /**
     * Establece los magos del juego.
     * 
     * @param magos
     */
    public void setMagos(List<Mago> magos) {
        this.magos = magos != null ? List.copyOf(magos) : null;
    }

    // GETTERS
    /**
     * Obtiene los magos seleccionados para el juego.
     * 
     * @return
     */
    public List<Mago> getMagos() {
        return magos;
    }

    /**
     * Obtiene el bosque seleccionado para el juego.
     * 
     * @return
     */
    public Bosque getBosque() {
        return bosque;
    }

    /**
     * Obtiene el monstruo seleccionado para el juego.
     * 
     * @return
     */
    public Monstruo getMonstruoJefe() {
        return monstruoJefe;
    }

    /**
     * Obtiene la lista de monstruos seleccionados para el juego.
     * 
     * @return
     */
    public List<Monstruo> getMonstruos() {
        return monstruos;
    }

    /**
     * Obtiene el dragón seleccionado para el juego.
     * 
     * @return
     */
    public Dragon getDragon() {
        return dragon;
    }

    /**
     * Obtiene el hechizo seleccionado para el juego.
     * 
     * @return
     */
    public Hechizo getHechizo() {
        return hechizo;
    }

    /**
     * Obtiene la lista de monstruos disponibles en el juego.
     * 
     * @return
     */
    public List<Monstruo> getListaMonstruos() {
        return listaMonstruos;
    }

    /**
     * Obtiene la lista de magos disponibles en el juego.
     * 
     * @return
     */
    public List<Mago> getListaMagos() {
        return listaMagos;
    }

    // SETTERS
    public void setBosque(Bosque bosque) {
        this.bosque = bosque;
    }

    /**
     * Obtiene la lista de bosques disponibles en el juego.
     * 
     * @return
     */
    public List<Bosque> getListaBosques() {
        return listaBosques;
    }

    /**
     * Obtiene la lista de hechizos disponibles en el juego.
     * 
     * @return
     */
    public List<Hechizo> getListaHechizos() {
        return listaHechizos;
    }

    /**
     * Obtiene la lista de dragones disponibles en el juego.
     * 
     * @return
     */
    public List<Dragon> getListaDragones() {
        return listaDragones;
    }

    // SETTERS
    /**
     * Establece el dragón seleccionado para el juego.
     * @param dragon
     */
    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }

    /**
     * Establece el hechizo seleccionado para el juego.
     * @param hechizo
     */
    public void setHechizo(Hechizo hechizo) {
        this.hechizo = hechizo;
    }

    /**
     * Establece el monstruo seleccionado para el juego.
     * @param monstruoJefe
     */
    public void setMonstruoJefe(Monstruo monstruoJefe) {
        this.monstruoJefe = monstruoJefe;
    }

    // MÉTODOS
    /**
     * Añade un hechizo a la lista de hechizos del juego.
     * 
     * @param mago
     */
    public void addMagoToLista(Mago mago) {
        this.listaMagos.add(mago);
    }

    /**
     * Añade un monstruo a la lista de monstruos del juego.
     * 
     * @param monstruo
     */
    public void addMonstruoToLista(Monstruo monstruo) {
        this.listaMonstruos.add(monstruo);
    }

    /**
     * Añade un bosque a la lista de bosques del juego.
     * 
     * @param bosque
     */
    public void addBosqueToLista(Bosque bosque) {
        this.listaBosques.add(bosque);
    }

    /**
     * Añade un hechizo a la lista de hechizos del juego.
     * 
     * @param hechizo
     */
    public void addHechizoToLista(Hechizo hechizo) {
        this.listaHechizos.add(hechizo);
    }

    /**
     * Añade un dragón a la lista de dragones del juego.
     * 
     * @param dragon
     */
    public void addDragonToLista(Dragon dragon) {
        this.listaDragones.add(dragon);
    }

    /**
     * Limpia todas las listas del modelo.
     */
    public void limpiarListas() {
        listaMagos.clear();
        listaDragones.clear();
        listaBosques.clear();
        listaMonstruos.clear();
        listaHechizos.clear();
    }
}
