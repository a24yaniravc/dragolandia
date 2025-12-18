package com.example;

import java.util.ArrayList;
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
    private static final Controlador controlador = Controlador.getInstancia();
    private static Scanner sc = new Scanner(System.in);

    private static Dragon dragon1 = new Dragon("Draco", 200, 50);

    public static void main(String[] args) {
        controlador.getVista().imprimirMensaje("¿Desea añadir los personajes a la base de datos? (S/N)");

        if (sc.nextLine().equalsIgnoreCase("s")) {
            addCharacters();
        }

        controlador.getModelo().loadFromDatabase();

        controlador.getModelo().inicializarJuego();
        controlador.comenzarCombate();
    }

    /**
     * Método para añadir personajes a la base de datos.
     */
    public static void addCharacters() {
        // Creación del SessionFactory
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
                Session session = factory.getCurrentSession();) {
            Transaction tx = session.beginTransaction();

            // Guardado de las entidades en la base de datos
            // Dragones
            session.persist(dragon1);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje("Dragones: " + dragon1.getNombre());

            // Monstruos
            Monstruo monstruo1 = new Monstruo("Casper", 150, "espectro", 25);
            Monstruo monstruo2 = new Monstruo("Shrek", 1000, "ogro", 500);
            Monstruo monstruo3 = new Monstruo("Fionna", 300, "troll", 45);
            Monstruo monstruo4 = new Monstruo("Princesa llama", 100, "espectro", 30);
            Monstruo monstruo5 = new Monstruo("Gorgo el Terrible", 150, "ogro", 40);
            Monstruo monstruo6 = new Monstruo("Pepe el Troll", 400, "troll", 50);

            session.persist(monstruo1);
            session.persist(monstruo2);
            session.persist(monstruo3);
            session.persist(monstruo4);
            session.persist(monstruo5);
            session.persist(monstruo6);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje("Monstruos: " + monstruo1.getNombre() + ", " + monstruo2.getNombre()
                    + ", " + monstruo3.getNombre());

            // Bosques
            Bosque bosque1 = new Bosque("Bosque maldito", 1, new ArrayList<>(List.of(monstruo5, monstruo2, monstruo3)),
                    monstruo1, null);
            Bosque bosque2 = new Bosque("Selva oscura", 2, new ArrayList<>(List.of(monstruo4, monstruo6)), monstruo2, null);
            Bosque bosque3 = new Bosque("Pantano tenebroso", 5,
                    new ArrayList<>(List.of(monstruo1, monstruo5, monstruo6, monstruo2)),
                    monstruo3, dragon1);
            
            session.persist(bosque1);
            session.persist(bosque2);
            session.persist(bosque3);
            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje(
                    "Bosques: " + bosque1.getNombre() + ", " + bosque2.getNombre() + ", " + bosque3.getNombre());

            // Hechizos
            Mago mago1 = new Mago("Patosaurio", 500, 100);
            Mago mago2 = new Mago("Fenixdor", 120, 40);
            Mago mago3 = new Mago("Lunargenta", 90, 25);
            Mago mago4 = new Mago("Gandalf", 110, 35);
            Mago mago5 = new Mago("Ling Wei", 95, 28);
            Mago mago6 = new Mago("Frieren", 250, 60);

            Hechizo bolaFuego = new Hechizo("Bola de Fuego");
            Hechizo bolaNieve = new Hechizo("Bola de Nieve");
            Hechizo rayo = new Hechizo("Rayo");
            Hechizo risaTasha = new Hechizo("Risa de Tasha");
            Hechizo agujeroNegro = new Hechizo("Agujero Negro");

            mago1.aprenderHechizo(bolaFuego);
            mago1.aprenderHechizo(agujeroNegro);
            mago1.aprenderHechizo(bolaNieve);
            mago1.aprenderHechizo(risaTasha);
            mago1.aprenderHechizo(rayo);

            mago2.aprenderHechizo(rayo);
            mago3.aprenderHechizo(risaTasha);

            mago4.aprenderHechizo(risaTasha);
            mago4.aprenderHechizo(bolaFuego);

            mago5.aprenderHechizo(bolaNieve);
            mago5.aprenderHechizo(rayo);

            mago6.aprenderHechizo(risaTasha);
            mago6.aprenderHechizo(bolaNieve);
            mago6.aprenderHechizo(rayo);

            session.persist(bolaFuego);
            session.persist(bolaNieve);
            session.persist(rayo);
            session.persist(risaTasha);
            session.persist(agujeroNegro);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje("Hechizos: " + bolaFuego.getNombre() + ", " + bolaNieve.getNombre()
                    + ", " + rayo.getNombre() + ", " + risaTasha.getNombre() + ", " + agujeroNegro.getNombre());

            // Magos
            session.persist(mago1);
            session.persist(mago2);
            session.persist(mago3);
            session.persist(mago4);
            session.persist(mago5);
            session.persist(mago6);


            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje(
                    "Magos: " + mago1.getNombre() + ", " + mago2.getNombre() + ", " + mago3.getNombre());

            tx.commit(); // Confirmar la transacción para guardar los cambios
        } catch (HibernateException e) {
            System.out.println("Hibernate ha dado un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
