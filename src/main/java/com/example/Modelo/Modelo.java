package com.example.Modelo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
    private static Modelo instancia; // Instancia única de la clase Modelo (SINGLETON)
    private static Vista view = new Vista(); // Vista asociada al modelo

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
     * Inicializa el juego seleccionando aleatoriamente un bosque y un monstruo,
     * 
     * @param nombreMago
     */
    public final void inicializarJuego() {
        // Selección aleatoria de las entidades para el juego
        if (!listaBosques.isEmpty() && !listaMonstruos.isEmpty()) {
            int num_bosque = (int) (Math.random() * listaBosques.size());
            this.bosque = listaBosques.get(num_bosque);
            this.monstruo = bosque.getMonstruoJefe();

            // Selección del mago por parte del usuario
            view.seleccionMago();            
        } else {
            view.imprimirMensaje("Error al inicializar el juego: Asegúrate de haber seleccionado un mago y de que las listas no estén vacías.");
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
     * Método para cargar los personajes desde la base de datos.
     */
    public void loadFromDatabase() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
                Session session = factory.getCurrentSession();) {
            Transaction tx = session.beginTransaction();

            List<Mago> magos = session.createQuery("SELECT a FROM Mago a", Mago.class).list();
            for (Mago m : magos) {
                addMagoToLista(m);
            }

            List<Dragon> dragones = session.createQuery("SELECT a FROM Dragon a", Dragon.class).list();
            for (Dragon d : dragones) {
                addDragonToLista(d);
            }

            List<Hechizo> hechizos = session.createQuery("SELECT a FROM Hechizo a", Hechizo.class).list();
            for (Hechizo h : hechizos) {
                addHechizoToLista(h);
            }

            List<Bosque> bosques = session.createQuery("SELECT a FROM Bosque a", Bosque.class).list();
            for (Bosque b : bosques) {
                addBosqueToLista(b);
            }

            List<Monstruo> monstruos = session.createQuery("SELECT a FROM Monstruo a", Monstruo.class).list();
            for (Monstruo m : monstruos) {
                addMonstruoToLista(m);
            }

            tx.commit();
        }
    }


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
}
