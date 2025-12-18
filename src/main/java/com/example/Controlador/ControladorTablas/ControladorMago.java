package com.example.Controlador.ControladorTablas;

import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Vista.Vista;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Mago.
 */
public class ControladorMago {
    // Singleton
    private static ControladorMago instancia;

    // Atributos
    private final Vista vista;
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructor privado para el patrón Singleton.
     */
    private ControladorMago() {
        this.vista = new Vista();
    }

    /**
     * Obtiene la instancia única del controlador de Mago.
     * @return
     */
    public static ControladorMago getInstancia() {
        if (instancia == null) {
            instancia = new ControladorMago();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo mago en la base de datos.
     */
    public void insertarMago() {
        vista.imprimirMensaje("---- Insertar nuevo mago ----");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Vida: ");
        int vida = Integer.parseInt(sc.nextLine());

        System.out.print("Nivel de magia: ");
        int nivelMagia = Integer.parseInt(sc.nextLine());

        Mago mago = new Mago(nombre, vida, nivelMagia);

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();
            session.persist(mago);
            session.getTransaction().commit();

            vista.imprimirMensaje("Mago insertado correctamente.");
        } catch (HibernateException e) {
            vista.imprimirMensaje("Error al insertar mago: " + e.getMessage());
        }
    }

    
    /**
     * Modifica un mago existente en la base de datos.
     */
    public void modificarMago() {
        // Lógica para modificar un mago en la base de datos
        System.out.println("---- Modificar mago ----");
        System.out.println("Magos disponibles:");
        for (Mago m : Controlador.getInstancia().getModelo().getListaMagos()) {
            System.out.println("- " + m.getNombre());
        }
        System.out.print("Seleccione el mago a modificar por su nombre: ");
        String nombreSeleccionado = sc.nextLine();
        Mago magoModificar = Controlador.getInstancia().getModelo().getListaMagos().stream()
                .filter(m -> m.getNombre().equals(nombreSeleccionado))
                .findFirst()
                .orElse(null);
        if (magoModificar == null) {
            System.out.println("Mago no encontrado.");
            return;
        }

        System.out.println("Qué desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Vida");
        System.out.println("3. Nivel de magia");
        System.out.print("Seleccione una opción: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                System.out.print("Nuevo nombre: ");
                String nuevoNombre = sc.nextLine();
                magoModificar.setNombre(nuevoNombre);
                break;
            case "2":
                System.out.print("Nueva vida: ");
                int nuevaVida = Integer.parseInt(sc.nextLine());
                magoModificar.setVida(nuevaVida);
                break;
            case "3":
                System.out.print("Nuevo nivel de magia: ");
                int nuevoNivelMagia = Integer.parseInt(sc.nextLine());
                magoModificar.setNivelMagia(nuevoNivelMagia);
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        // Actualizar en la base de datos
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();) {
            Session session = factory.getCurrentSession();

            session.getTransaction().begin();
            session.merge(magoModificar);
            session.getTransaction().commit();

            System.out.println("Mago modificado correctamente: " + magoModificar.getNombre());
        } catch(HibernateException e) {
            System.out.println("Error al modificar el mago: " + e.getMessage());
        }
    }

    /**
     * Elimina un mago existente en la base de datos.
     * @param mago
     */
    public void eliminarMago(Mago mago) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();
            session.remove(session.merge(mago));
            session.getTransaction().commit();
        }
    }

    /**
     * Lista todos los magos en la base de datos.
     */
    public void seleccionarTodosMagos() {
        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            System.out.println("---- Lista de Magos ----");
            for (Mago m : session.createQuery("FROM Mago", Mago.class).list()) {
                System.out.println("- " + m.getNombre());
            }
        }
    }
}
