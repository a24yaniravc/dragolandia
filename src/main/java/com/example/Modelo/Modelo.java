package com.example.Modelo;

import java.util.List;

import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase Modelo que representa el modelo del juego.
 */
public class Modelo {
    private static Modelo instancia; // Instancia única de la clase Modelo (SINGLETON)

    // Listas de entidades disponibles en el juego
    private List<Monstruo> listaMonstruos = List.of(new Monstruo("Espectro de fuego", 100, "espectro", 30),
            new Monstruo("Gorgo el Terrible", 150, "ogro", 40), new Monstruo("Pepe el Troll", 80, "troll", 20));
    private List<Mago> listaMagos = List.of(new Mago("Patosaurio", 100, 30), new Mago("Fenixdor", 120, 40),
            new Mago("Lunargenta", 90, 25));
    private List<Bosque> listaBosques = List.of(new Bosque(1, "Bosque maldito", 1, listaMonstruos), new Bosque(2, "Selva oscura", 2, listaMonstruos), new Bosque(3, "Pantano tenebroso", 3, listaMonstruos));

    private Monstruo monstruo;
    private Mago mago;
    private Bosque bosque;

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
     * Inicializa el juego creando las entidades principales.
     */
    public final void inicializarJuego() {
        // Selección aleatoria de las entidades para el juego
        int num_mago = (int) (Math.random() * listaMagos.size());
        int num_bosque = (int) (Math.random() * listaBosques.size());

        this.mago = listaMagos.get(num_mago);
        this.bosque = listaBosques.get(num_bosque);
        this.monstruo = bosque.getMonstruoJefe();
    }

    // GETTERS
    public Mago getMago() {
        return mago;
    }

    public Bosque getBosque() {
        return bosque;
    }

    public Monstruo getMonstruo() {
        return monstruo;
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
}
