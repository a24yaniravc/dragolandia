package com.example;

import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Dragon;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;

import com.example.Modelo.ClasesJuego.Hechizo;

/**
 * Clase principal para iniciar la aplicación.
 */
public final class Principal {
    private final Controlador controlador = Controlador.getInstancia();
    private static Scanner sc = new Scanner(System.in);

    private static Dragon dragon1 = new Dragon("Draco", 200, 50);

    // Hechizos (para que funcionen las relaciones ManyToMany) [Solo deben crearse
    // UNA vez]
    private static Hechizo bolaFuego = new Hechizo("Bola de Fuego");
    private static Hechizo bolaNieve = new Hechizo("Bola de Nieve");
    private static Hechizo rayo = new Hechizo("Rayo");
    private static Hechizo risaTasha = new Hechizo("Risa de Tasha");
    private static Hechizo agujeroNegro = new Hechizo("Agujero Negro");

    // Datos de prueba
    private static Monstruo monstruo1 = new Monstruo("Espectro de fuego", 100, "espectro", 30);
    private static Monstruo monstruo2 = new Monstruo("Gorgo el Terrible", 150, "ogro", 40);
    private static Monstruo monstruo3 = new Monstruo("Pepe el Troll", 400, "troll", 50);

    private static Mago mago1 = new Mago("Patosaurio", 100, 30);
    private static Mago mago2 = new Mago("Fenixdor", 120, 40);
    private static Mago mago3 = new Mago("Lunargenta", 90, 25);

    private static Bosque bosque1 = new Bosque("Bosque maldito", 1, null, monstruo1);
    private static Bosque bosque2 = new Bosque("Selva oscura", 2, null, monstruo2);
    private static Bosque bosque3 = new Bosque("Pantano tenebroso", 5, null, monstruo3);

    public static void main(String[] args) {
        Principal principal = new Principal();
        Controlador controlador = Controlador.getInstancia();

        controlador.getVista().imprimirMensaje("¿Desea añadir los personajes a la base de datos? (S/N)");

        if (sc.nextLine().equalsIgnoreCase("s")) {
            principal.addCharacters();
        }

        principal.loadFromDatabase();

        controlador.getModelo().inicializarJuego();
        controlador.comenzarCombate();
    }

    /**
     * Método para cargar los personajes desde la base de datos.
     */
    public void loadFromDatabase() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
                Session session = factory.getCurrentSession();) {
            Transaction tx = session.beginTransaction();

            List<Mago> magos = session.createQuery("SELECT a FROM Mago a", Mago.class).list();
            for (Mago m : magos) {
                controlador.getModelo().addMagoToLista(m);
            }

            List<Dragon> dragones = session.createQuery("SELECT a FROM Dragon a", Dragon.class).list();
            for (Dragon d : dragones) {
                controlador.getModelo().addDragonToLista(d);
            }

            List<Hechizo> hechizos = session.createQuery("SELECT a FROM Hechizo a", Hechizo.class).list();
            for (Hechizo h : hechizos) {
                controlador.getModelo().addHechizoToLista(h);
            }

            List<Bosque> bosques = session.createQuery("SELECT a FROM Bosque a", Bosque.class).list();
            for (Bosque b : bosques) {
                controlador.getModelo().addBosqueToLista(b);
            }

            List<Monstruo> monstruos = session.createQuery("SELECT a FROM Monstruo a", Monstruo.class).list();
            for (Monstruo m : monstruos) {
                controlador.getModelo().addMonstruoToLista(m);
            }

            tx.commit();
        }
    }

    /**
     * Método para añadir personajes a la base de datos.
     */
    public void addCharacters() {
        // Creación del SessionFactory
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
                Session session = factory.getCurrentSession();) {
            Transaction tx = session.beginTransaction();

            // Guardado de las entidades en la base de datos
            // Monstruos
            session.merge(monstruo1);
            session.merge(monstruo2);
            session.merge(monstruo3);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje("Monstruos: " + monstruo1.getNombre() + ", " + monstruo2.getNombre()
                    + ", " + monstruo3.getNombre());

            // Bosques
            session.merge(bosque1);
            session.merge(bosque2);
            session.merge(bosque3);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje(
                    "Bosques: " + bosque1.getNombre() + ", " + bosque2.getNombre() + ", " + bosque3.getNombre());

            // Hechizos
            mago1.aprenderHechizo(bolaFuego);
            mago1.aprenderHechizo(bolaNieve);

            mago2.aprenderHechizo(rayo);
            mago3.aprenderHechizo(risaTasha);

            session.merge(bolaFuego);
            session.merge(bolaNieve);
            session.merge(rayo);
            session.merge(risaTasha);
            session.merge(agujeroNegro);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje("Hechizos: " + bolaFuego.getNombre() + ", " + bolaNieve.getNombre()
                    + ", " + rayo.getNombre() + ", " + risaTasha.getNombre() + ", " + agujeroNegro.getNombre());

            // Magos
            session.merge(mago1);
            session.merge(mago2);
            session.merge(mago3);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje(
                    "Magos: " + mago1.getNombre() + ", " + mago2.getNombre() + ", " + mago3.getNombre());

            // Dragones
            session.merge(dragon1);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje("Dragones: " + dragon1.getNombre());

            tx.commit(); // Confirmar la transacción para guardar los cambios
        } catch (HibernateException e) {
            System.out.println("Hibernate ha dado un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
