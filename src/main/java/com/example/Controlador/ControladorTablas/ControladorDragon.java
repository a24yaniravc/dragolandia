package com.example.Controlador.ControladorTablas;

import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Dragon;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Dragon.
 */
public class ControladorDragon {
    // Singleton
    private static ControladorDragon instancia;
    
    // Atributos
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructor privado para el patrón Singleton.
     */
    private ControladorDragon() {}

    /**
     * Obtiene la instancia única del controlador de Dragón.
     * @return
     */
    public static ControladorDragon getInstancia() {
        if (instancia == null) {
            instancia = new ControladorDragon();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo dragón en la base de datos.
     */
    public void insertarDragon() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Intensidad de fuego: ");
        int fuego = Integer.parseInt(sc.nextLine());

        System.out.print("Resistencia: ");
        int resistencia = Integer.parseInt(sc.nextLine());

        Dragon dragon = new Dragon(nombre, resistencia, fuego);

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();
            session.persist(dragon);
            session.getTransaction().commit();
        }
    }

    /**
     * Modifica un dragón existente en la base de datos.
     */
    public void modificarDragon() {
        System.out.println("---- Modificar dragón ----");
        System.out.println("Dragones disponibles:");
        for (Dragon d : Controlador.getInstancia().getModelo().getListaDragones()) {
            System.out.println("- " + d.getNombre());
        }

        System.out.print("Seleccione el dragón a modificar por su nombre: ");
        String nombreSeleccionado = sc.nextLine();

        Dragon dragonModificar = Controlador.getInstancia().getModelo().getListaDragones().stream()
                .filter(d -> d.getNombre().equals(nombreSeleccionado))
                .findFirst()
                .orElse(null);

        if (dragonModificar == null) {
            System.out.println("Dragón no encontrado.");
            return;
        }

        System.out.println("Qué desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Intensidad de fuego");
        System.out.println("3. Resistencia");
        System.out.print("Seleccione una opción: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                System.out.print("Nuevo nombre: ");
                dragonModificar.setNombre(sc.nextLine());
                break;
            case "2":
                System.out.print("Nueva intensidad de fuego: ");
                dragonModificar.setIntensidadFuego(Integer.parseInt(sc.nextLine()));
                break;
            case "3":
                System.out.print("Nueva resistencia: ");
                dragonModificar.setResistencia(Integer.parseInt(sc.nextLine()));
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();
            session.getTransaction().begin();
            session.merge(dragonModificar);
            session.getTransaction().commit();
            System.out.println("Dragón modificado correctamente: " + dragonModificar.getNombre());
        } catch (HibernateException e) {
            System.out.println("Error al modificar el dragón: " + e.getMessage());
        }
    }


    /**
     * Elimina un dragón de la base de datos.
     */
    public void eliminarDragon() {
        System.out.print("Nombre del dragón a eliminar: ");
        String nombre = sc.nextLine();

        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            Dragon dragon = session.createQuery(
                    "FROM Dragon WHERE nombre = :nombre", Dragon.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            session.beginTransaction();
            session.remove(dragon);
            session.getTransaction().commit();
        }
    }


    /**
     * Selecciona e imprime todos los dragones de la base de datos.
     */
    public void seleccionarTodosDragones() {
        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            List<Dragon> listaDragones = session.createQuery("FROM Dragon", Dragon.class).list();

            System.out.println("---- Lista de Dragones ----");
            for (Dragon d : listaDragones) {
                System.out.println("Nombre: " + d.getNombre() +
                                   ", Intensidad de fuego: " + d.getIntensidadFuego() +
                                   ", Resistencia: " + d.getResistencia());
            }
        } catch (HibernateException e) {
            System.out.println("Error al seleccionar los dragones: " + e.getMessage());
        }
    }
}
