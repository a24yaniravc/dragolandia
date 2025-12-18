package com.example.Controlador.ControladorTablas;

import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Hechizo;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Hechizo.
 */
public class ControladorHechizo {
    // Singleton
    private static ControladorHechizo instancia;

    // Atributos
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructor privado para el patrón Singleton.
     */
    private ControladorHechizo() {}

    /**
     * Obtiene la instancia única del controlador de Hechizo.
     * @return
     */
    public static ControladorHechizo getInstancia() {
        if (instancia == null) {
            instancia = new ControladorHechizo();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo hechizo en la base de datos.
     */
    public void insertarHechizo() {
        System.out.print("Nombre del hechizo: ");
        String nombre = sc.nextLine();

        Hechizo hechizo = new Hechizo(nombre);

        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            session.beginTransaction();
            session.persist(hechizo);
            session.getTransaction().commit();
        }
    }

    /**
     * Modifica un hechizo existente en la base de datos.
     */
    public void modificarHechizo() {
        System.out.println("---- Modificar hechizo ----");
        System.out.println("Hechizos disponibles:");
        for (Hechizo h : Controlador.getInstancia().getModelo().getListaHechizos()) {
            System.out.println("- " + h.getNombre());
        }

        System.out.print("Seleccione el hechizo a modificar por su nombre: ");
        String nombreSeleccionado = sc.nextLine();

        Hechizo hechizoModificar = Controlador.getInstancia().getModelo().getListaHechizos().stream()
                .filter(h -> h.getNombre().equals(nombreSeleccionado))
                .findFirst()
                .orElse(null);

        if (hechizoModificar == null) {
            System.out.println("Hechizo no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre del hechizo: ");
        hechizoModificar.setNombre(sc.nextLine());

        // Guardar los cambios en la base de datos
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();
            session.getTransaction().begin();
            session.merge(hechizoModificar);
            session.getTransaction().commit();
            System.out.println("Hechizo modificado correctamente: " + hechizoModificar.getNombre());
        } catch (HibernateException e) {
            System.out.println("Error al modificar el hechizo: " + e.getMessage());
        }
    }


    /**
     * Elimina un hechizo existente en la base de datos.
     */
    public void eliminarHechizo() {
        System.out.print("Nombre del hechizo a eliminar: ");
        String nombre = sc.nextLine();

        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            Hechizo hechizo = session.createQuery(
                    "FROM Hechizo WHERE nombre = :nombre", Hechizo.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            session.beginTransaction();
            session.remove(hechizo);
            session.getTransaction().commit();
        }
    }

    public void seleccionarTodosHechizos() {
        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            System.out.println("---- Lista de Hechizos ----");
            for (Hechizo h : session.createQuery("FROM Hechizo", Hechizo.class).list()) {
                System.out.println("- " + h.getNombre());
            }
        }
    }
}
