package com.example.Controlador.GestorTablas;

import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Monstruo.
 */
public class GestorMonstruo {
    // Singleton
    private static GestorMonstruo instancia;
    
    // Atributos
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructor privado para el patrón Singleton.
     */
    private GestorMonstruo() {}

    /**
     * Obtiene la instancia única del controlador de Monstruo.
     * @return
     */
    public static GestorMonstruo getInstancia() {
        if (instancia == null) {
            instancia = new GestorMonstruo();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo monstruo en la base de datos.
     */
    public void insertarMonstruo() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Vida: ");
        int vida = Integer.parseInt(sc.nextLine());

        System.out.print("Fuerza: ");
        int fuerza = Integer.parseInt(sc.nextLine());

        System.out.print("Tipo (ogro/troll/espectro): ");
        Monstruo.Tipo tipo = Monstruo.Tipo.valueOf(sc.nextLine());

        Monstruo monstruo = new Monstruo(nombre, vida, tipo.name(), fuerza);

        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            session.beginTransaction();
            session.persist(monstruo);
            session.getTransaction().commit();
        }
    }

    /**
     * Modifica un monstruo existente en la base de datos.
     */
    public void modificarMonstruo() {
        System.out.println("---- Modificar monstruo ----");
        System.out.println("Monstruos disponibles:");
        for (Monstruo m : Controlador.getInstancia().getModelo().getListaMonstruos()) {
            System.out.println("- " + m.getNombre());
        }

        System.out.print("Seleccione el monstruo a modificar por su nombre: ");
        String nombreSeleccionado = sc.nextLine();

        Monstruo monstruoModificar = Controlador.getInstancia().getModelo().getListaMonstruos().stream()
                .filter(m -> m.getNombre().equals(nombreSeleccionado))
                .findFirst()
                .orElse(null);

        if (monstruoModificar == null) {
            System.out.println("Monstruo no encontrado.");
            return;
        }

        System.out.println("Qué desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Vida");
        System.out.println("3. Fuerza");
        System.out.print("Seleccione una opción: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                System.out.print("Nuevo nombre: ");
                monstruoModificar.setNombre(sc.nextLine());
                break;
            case "2":
                System.out.print("Nueva vida: ");
                monstruoModificar.setVida(Integer.parseInt(sc.nextLine()));
                break;
            case "3":
                System.out.print("Nueva fuerza: ");
                monstruoModificar.setFuerza(Integer.parseInt(sc.nextLine()));
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();
            session.getTransaction().begin();
            session.merge(monstruoModificar);
            session.getTransaction().commit();
            System.out.println("Monstruo modificado correctamente: " + monstruoModificar.getNombre());
        } catch (HibernateException e) {
            System.out.println("Error al modificar el monstruo: " + e.getMessage());
        }
    }

    /**
     * Elimina un monstruo existente en la base de datos.
     */
     public void eliminarMonstruo() {
        System.out.print("Nombre del monstruo a eliminar: ");
        String nombre = sc.nextLine();

        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            Monstruo monstruo = session.createQuery(
                    "FROM Monstruo WHERE nombre = :nombre", Monstruo.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            session.beginTransaction();
            session.remove(monstruo);
            session.getTransaction().commit();
        }
    }

    public void seleccionarTodosMonstruos() {
        try (Session session = new Configuration().configure()
                .buildSessionFactory().openSession()) {

            System.out.println("---- Lista de Monstruos ----");
            for (Monstruo m : session.createQuery("FROM Monstruo", Monstruo.class).list()) {
                System.out.println("- " + m.getNombre());
            }
        }
    }
}
