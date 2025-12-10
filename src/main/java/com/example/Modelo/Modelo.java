package com.example.Modelo;

/**
 * Clase Modelo que representa el modelo del juego.
 */
public class Modelo {
    private static Modelo instancia; // Instancia única de la clase Modelo (SINGLETON)

    private  Monstruo monstruo;
    private  Mago mago;
    private  Bosque bosque;

    // CONSTRUCTORES

    /**
     * Constructor de la clase Modelo.
     */
    public Modelo() {
        inicializarJuego();
    }
    
    /**
     * Obtiene la instancia única de la clase Modelo (SINGLETON).
     * @return
     */
    public static Modelo getInstancia() {
        if (instancia == null) {
            synchronized (Modelo.class) {
                instancia = new Modelo();
            }
        }
        return instancia;
    }

    /**
     * Inicializa el juego creando las entidades principales.
     */
    public final void inicializarJuego() {
        monstruo = new Monstruo("Troll Gigante", 100, "ogro", 30);
        mago = new Mago("Merlín", 100, 30);
        bosque = new Bosque(1, "Bosque encantado", 1, monstruo);
    }

    //GETTERS
    public Mago getMago() {
        return mago;
    }

    public Bosque getBosque() {
        return bosque;
    }
}
