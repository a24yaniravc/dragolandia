package com.example.Controlador;

import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.example.Modelo.ClasesJuego.Mago;
import com.example.Vista.Vista;

/**
 * Clase ControladorMago para controlar inserciones, modificaciones y eliminaciones de magos.
 */
public class ControladorMago {
    private static ControladorMago instancia;
    private final Vista vista;
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ControladorMago() {
        this.vista = new Vista();
    }

    /**
     * Obtiene la instancia única de ControladorMago.
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
    private void insertarMago() {
        System.out.println("---- Insertar nuevo mago ----");
        System.out.print("Nombre del mago: ");
        String nombre = sc.nextLine();
        System.out.print("Vida del mago: ");
        int vida = Integer.parseInt(sc.nextLine());
        System.out.print("Nivel de magia del mago: ");
        int nivelMagia = Integer.parseInt(sc.nextLine());
        System.out.println("Opciones de hechizo:");
        System.out.println("1. Bola de Fuego");
        System.out.println("2. Rayo");
        System.out.println("3. Bola de Nieve");
        System.out.println("4. Risa de Tasha");
        System.out.println("5. Agujero Negro");
        System.out.print("Seleccione un máximo de 3 hechizos: ");
        System.out.println();
        
        Mago nuevoMago = new Mago(nombre, vida, nivelMagia);

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();) {
            Session session = factory.getCurrentSession();

            session.getTransaction().begin();
            session.persist(nuevoMago);
            session.getTransaction().commit();

            System.out.println("Mago insertado correctamente: " + nuevoMago.getNombre());
        } catch(HibernateException e) {
            System.out.println("Error al insertar el mago: " + e.getMessage());
        }
    }

    /**
     * Modifica un mago existente en la base de datos.
     */
    private void modificarMago() {
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
     * Elimina un mago de la base de datos.
     */
    private void eliminarMago() {
        // Lógica para eliminar un mago de la base de datos
        System.out.println("---- Eliminar mago ----");
        System.out.println("Magos disponibles:");
        for (Mago m : Controlador.getInstancia().getModelo().getListaMagos()) {
            System.out.println("- " + m.getNombre());
        }
        System.out.print("Seleccione el mago a eliminar por su nombre: ");
        String nombreSeleccionado = sc.nextLine();
        Mago magoEliminar = Controlador.getInstancia().getModelo().getListaMagos().stream()
                .filter(m -> m.getNombre().equals(nombreSeleccionado))
                .findFirst()
                .orElse(null);
        if (magoEliminar == null) {
            System.out.println("Mago no encontrado.");
            return;
        }

        // Eliminar de la base de datos
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();) {
            Session session = factory.getCurrentSession();
            session.getTransaction().begin();
            session.remove(magoEliminar);
            session.getTransaction().commit();
            System.out.println("Mago eliminado correctamente: " + magoEliminar.getNombre());
        } catch(HibernateException e) {
            System.out.println("Error al eliminar el mago: " + e.getMessage());
        }
    }
}
