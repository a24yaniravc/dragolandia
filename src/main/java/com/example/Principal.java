package com.example;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase principal para iniciar la aplicación.
 */
public final class Principal {
    private final Controlador controlador = new Controlador();

    // Datos de prueba
    private static Monstruo monstruo1 = new Monstruo("Espectro de fuego", 100, "espectro", 30);
    private static Monstruo monstruo2 = new Monstruo("Gorgo el Terrible", 150, "ogro", 40);
    private static Monstruo monstruo3 = new Monstruo("Pepe el Troll", 80, "troll", 20);

    private static Mago mago1 = new Mago("Patosaurio", 100, 30);
    private static Mago mago2 = new Mago("Fenixdor", 120, 40);
    private static Mago mago3 = new Mago("Lunargenta", 90, 25);

    private static Bosque bosque1 = new Bosque("Bosque maldito", 1, List.of(monstruo1, monstruo2), monstruo1);
    private static Bosque bosque2 = new Bosque("Selva oscura", 2, List.of(monstruo2, monstruo3), monstruo2);
    private static Bosque bosque3 = new Bosque("Pantano tenebroso", 3, List.of(monstruo1, monstruo3), monstruo3);

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
            // Magos
            session.merge(mago1);
            session.merge(mago2);
            session.merge(mago3);

            controlador.getVista().imprimirMensaje("Se ha insertado correctamente: ");
            controlador.getVista().imprimirMensaje(
                    "Magos: " + mago1.getNombre() + ", " + mago2.getNombre() + ", " + mago3.getNombre());

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

            tx.commit(); // Confirmar la transacción para guardar los cambios
        } catch (HibernateException e) {
            System.out.println("Hibernate ha dado un error: " + e.getMessage());
        }
    }
}
