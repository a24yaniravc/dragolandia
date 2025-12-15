package com.example;

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

    private static Dragon dragon1 = new Dragon("Draco", 200, 50);

    // Hechizos (para que funcionen las relaciones ManyToMany) [Solo deben crearse UNA vez]
    Hechizo bolaFuego = new Hechizo("Bola de Fuego");
    Hechizo bolaNieve = new Hechizo("Bola de Nieve");
    Hechizo rayo = new Hechizo("Rayo");
    Hechizo risaTasha = new Hechizo("Risa de Tasha");
    Hechizo agujeroNegro = new Hechizo("Agujero Negro");

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
        Controlador controlador = principal.controlador;

        // Añadir personajes a la base de datos
        controlador.getVista().imprimirMensaje("Desea añadir los personajes a la base de datos? (S/N)");
        try {
            int entrada = System.in.read();
            controlador.getVista().imprimirMensaje("");
            if (entrada == 's' || entrada == 'S') {
                principal.addCharacters();
            }
        } catch (Exception e) {
            controlador.getVista().imprimirMensaje("Error al leer la entrada: " + e.getMessage());
        }

        // Añadir personajes a las listas del modelo
        controlador.getModelo().addMagoToLista(mago1);
        controlador.getModelo().addMagoToLista(mago2);
        controlador.getModelo().addMagoToLista(mago3);

        controlador.getModelo().addMonstruoToLista(monstruo1);
        controlador.getModelo().addMonstruoToLista(monstruo2);
        controlador.getModelo().addMonstruoToLista(monstruo3);

        controlador.getModelo().addBosqueToLista(bosque1);
        controlador.getModelo().addBosqueToLista(bosque2);
        controlador.getModelo().addBosqueToLista(bosque3);

        // Iniciar el juego
        controlador.getModelo().inicializarJuego();
        controlador.ComenzarCombate();
    }

    /**
     * Método para añadir personajes a la base de datos.
     */
    public void addCharacters() {
        // Creación del SessionFactory
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();) {

            Session session = factory.getCurrentSession();
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

            session.persist(bolaFuego);
            session.persist(bolaNieve);
            session.persist(rayo);
            session.persist(risaTasha);
            session.persist(agujeroNegro);

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

            session.persist(mago1);
            session.persist(mago2);
            session.persist(mago3);

            
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
