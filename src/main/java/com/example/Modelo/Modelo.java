package com.example.Modelo;

import java.util.List;

import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Dragon;
import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;
import com.example.Vista.Vista;

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

    private Monstruo monstruo;
    private List<Mago> magos = null;
    private Bosque bosque;
    private Dragon dragon;
    private Hechizo hechizo;

    // CONSTRUCTORES

    /**
     * Constructor de la clase Modelo.
     */
    public Modelo() {
        inicializarJuego();
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
     * @param view
     */
    public void setVista(Vista view) {
        this.view = view;
    }

    /**
     * Inicializa el juego seleccionando aleatoriamente un bosque y un monstruo,
     * 
     * @param nombreMago
     */
    public final void inicializarJuego() {
        // Selección aleatoria de las entidades para el juego
         if (!listaBosques.isEmpty() && !listaMonstruos.isEmpty() && view != null) {
            int indexBosque = (int) (Math.random() * listaBosques.size());
            this.bosque = listaBosques.get(indexBosque);
            this.monstruo = bosque.getMonstruoJefe();

            // Selección de magos usando la vista
            this.magos = view.seleccionMago(listaMagos);
        } else {
            if (view != null) {
                view.imprimirMensaje("Error: No se puede inicializar el juego. Listas vacías o vista no configurada.");
            }
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
    public List<Mago> getMagos() {
        return magos;
    }

    public Bosque getBosque() {
        return bosque;
    }

    public Monstruo getMonstruo() {
        return monstruo;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public Hechizo getHechizo() {
        return hechizo;
    }

    public List<Monstruo> getListaMonstruos() {
        return listaMonstruos;
    }

    public List<Mago> getListaMagos() {
        return listaMagos;
    }

    public List<Bosque> getListaBosques() {
        return listaBosques;
    }

    public List<Hechizo> getListaHechizos() {
        return listaHechizos;
    }

    public List<Dragon> getListaDragones() {
        return listaDragones;
    }

    // MÉTODOS
    /**
     * Añade un hechizo a la lista de hechizos del juego.
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
     * @param hechizo
     */
    public void addHechizoToLista(Hechizo hechizo) {
        this.listaHechizos.add(hechizo);
    }

    /**
     * Añade un dragón a la lista de dragones del juego.
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
