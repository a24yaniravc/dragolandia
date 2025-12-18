package com.example.Controlador.ControladorTablas;

import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Bosque;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Bosque.
 */
public class ControladorBosque {
    // Singleton
    private static ControladorBosque instancia;

    // Atributos
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructor privado para el patrón Singleton.
     */
    private ControladorBosque() {}

    /**
     * Obtiene la instancia única del controlador de Bosque.
     * @return
     */
    public static ControladorBosque getInstancia() {
        if (instancia == null) {
            instancia = new ControladorBosque();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo bosque en la base de datos.
     */
    public void insertarBosque() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nivel de peligro: ");
        int nivel = Integer.parseInt(sc.nextLine());

        Bosque bosque = new Bosque(nombre, nivel, null, null);

        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            session.beginTransaction();
            session.persist(bosque);
            session.getTransaction().commit();
        }
    }

    /**
     * Modifica un bosque existente en la base de datos.
     */
    public void modificarBosque() {
        System.out.println("---- Modificar bosque ----");
        System.out.println("Bosques disponibles:");
        for (Bosque b : Controlador.getInstancia().getModelo().getListaBosques()) {
            System.out.println("- " + b.getNombre());
        }

        System.out.print("Seleccione el bosque a modificar por su nombre: ");
        String nombreSeleccionado = sc.nextLine();

        Bosque bosqueModificar = Controlador.getInstancia().getModelo().getListaBosques().stream()
                .filter(b -> b.getNombre().equals(nombreSeleccionado))
                .findFirst()
                .orElse(null);

        if (bosqueModificar == null) {
            System.out.println("Bosque no encontrado.");
            return;
        }

        System.out.println("Qué desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Nivel de peligro");
        System.out.print("Seleccione una opción: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                System.out.print("Nuevo nombre: ");
                bosqueModificar.setNombre(sc.nextLine());
                break;
            case "2":
                System.out.print("Nuevo nivel de peligro: ");
                bosqueModificar.setNivelPeligro(Integer.parseInt(sc.nextLine()));
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        // Actualizar en la base de datos
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();
            session.getTransaction().begin();
            session.merge(bosqueModificar);
            session.getTransaction().commit();
            System.out.println("Bosque modificado correctamente: " + bosqueModificar.getNombre());
        } catch (HibernateException e) {
            System.out.println("Error al modificar el bosque: " + e.getMessage());
        }
    }


    /**
     * Elimina un bosque existente en la base de datos.
     */
    public void eliminarBosque() {
        System.out.print("Nombre del bosque a eliminar: ");
        String nombre = sc.nextLine();

        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            Bosque bosque = session.createQuery(
                    "FROM Bosque WHERE nombre = :nombre", Bosque.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            session.beginTransaction();
            session.remove(bosque);
            session.getTransaction().commit();
        }
    }

    /**
     * Selecciona y muestra todos los bosques de la base de datos.
     */
    public void seleccionarTodosBosques() {
        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            System.out.println("---- Lista de Bosques ----");
            for (Bosque b : session.createQuery("FROM Bosque", Bosque.class).list()) {
                System.out.println("- " + b.getNombre() + " (Nivel de peligro: " + b.getNivelPeligro() + ")");
            }
        }
    }
}
