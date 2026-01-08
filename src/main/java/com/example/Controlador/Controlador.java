package com.example.Controlador;

import java.util.ArrayList;
import java.util.List;

import com.example.Modelo.Modelo;
import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Dragon;
import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;
import com.example.Vista.Vista;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Clase Controlador para manejar la lógica del juego.
 */
public class Controlador {
    private static EntityManager em = ControladorSesion.getInstancia().getHybernateUtil().getSesion(); // Sesión de
                                                                                                       // Hibernate
    private static Controlador instancia;
    private final Modelo modelo;
    private final Vista vista;

    /**
     * Constructor privado (Singleton).
     */
    private Controlador() {
        this.vista = new Vista();
        this.modelo = Modelo.getInstancia();
    }

    /**
     * Devuelve la instancia única del controlador.
     */
    public static Controlador getInstancia() {
        if (instancia == null) {
            instancia = new Controlador();
        }
        return instancia;
    }

    // GETTERS
    public Modelo getModelo() {
        return modelo;
    }

    public Vista getVista() {
        return vista;
    }

    // MÉTODOS
    /**
     * Método para cargar los personajes desde la base de datos.
     */
    public void loadFromDatabase() {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            List<Mago> magos = em.createQuery("SELECT a FROM Mago a", Mago.class).getResultList();
            for (Mago m : magos) {
                modelo.addMagoToLista(m);
            }

            List<Dragon> dragones = em.createQuery("SELECT a FROM Dragon a", Dragon.class).getResultList();
            for (Dragon d : dragones) {
                modelo.addDragonToLista(d);
            }

            List<Hechizo> hechizos = em.createQuery("SELECT a FROM Hechizo a", Hechizo.class).getResultList();
            for (Hechizo h : hechizos) {
                modelo.addHechizoToLista(h);
            }

            List<Bosque> bosques = em.createQuery("SELECT a FROM Bosque a", Bosque.class).getResultList();
            for (Bosque b : bosques) {
                modelo.addBosqueToLista(b);
            }

            List<Monstruo> monstruos = em.createQuery("SELECT a FROM Monstruo a", Monstruo.class).getResultList();
            for (Monstruo m : monstruos) {
                modelo.addMonstruoToLista(m);
            }

            tx.commit();
        } catch (Exception e) {
            vista.imprimirMensaje("Error al cargar los personajes desde la base de datos: " + e.getMessage());
        }
    }

    /**
     * Método para añadir personajes a la base de datos.
     */
    public void addCharacters() {
        try {
            // Creación del SessionFactory
            EntityTransaction tx = em.getTransaction();
            tx.begin(); // Iniciar la transacción

            // Guardado de las entidades en la base de datos
            // Dragones
            Dragon dragon1 = new Dragon("Draco", 200, 50);
            em.persist(dragon1);

            vista.imprimirMensaje("Se ha insertado correctamente: ");
            vista.imprimirMensaje("Dragones: " + dragon1.getNombre());

            // Monstruos
            Monstruo monstruo1 = new Monstruo("Casper", 150, "espectro", 25);
            Monstruo monstruo2 = new Monstruo("Shrek", 1000, "ogro", 500);
            Monstruo monstruo3 = new Monstruo("Fionna", 300, "troll", 45);
            Monstruo monstruo4 = new Monstruo("Princesa llama", 100, "espectro", 30);
            Monstruo monstruo5 = new Monstruo("Gorgo el Terrible", 150, "ogro", 40);
            Monstruo monstruo6 = new Monstruo("Pepe el Troll", 400, "troll", 50);

            em.persist(monstruo1);
            em.persist(monstruo2);
            em.persist(monstruo3);
            em.persist(monstruo4);
            em.persist(monstruo5);
            em.persist(monstruo6);

            vista.imprimirMensaje("Se ha insertado correctamente: ");
            vista.imprimirMensaje("Monstruos: " + monstruo1.getNombre() + ", " + monstruo2.getNombre()
                            + ", " + monstruo3.getNombre());

            // Bosques
            Bosque bosque1 = new Bosque("Bosque maldito", 1,
                    new ArrayList<>(List.of(monstruo5, monstruo2, monstruo3)),
                    monstruo1, null);
            Bosque bosque2 = new Bosque("Selva oscura", 2, new ArrayList<>(List.of(monstruo4, monstruo6)),
                    monstruo2, null);
            Bosque bosque3 = new Bosque("Pantano tenebroso", 5,
                    new ArrayList<>(List.of(monstruo1, monstruo5, monstruo6, monstruo2)),
                    monstruo3, dragon1);

            em.persist(bosque1);
            em.persist(bosque2);
            em.persist(bosque3);
            vista.imprimirMensaje("Se ha insertado correctamente: ");
            vista.imprimirMensaje(
                    "Bosques: " + bosque1.getNombre() + ", " + bosque2.getNombre() + ", "
                            + bosque3.getNombre());

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

            em.persist(bolaFuego);
            em.persist(bolaNieve);
            em.persist(rayo);
            em.persist(risaTasha);
            em.persist(agujeroNegro);
            vista.imprimirMensaje("Se ha insertado correctamente: ");
            vista.imprimirMensaje("Hechizos: " + bolaFuego.getNombre() + ", " + bolaNieve.getNombre()
                            + ", " + rayo.getNombre() + ", " + risaTasha.getNombre() + ", "
                            + agujeroNegro.getNombre());

            // Magos
            em.persist(mago1);
            em.persist(mago2);
            em.persist(mago3);
            em.persist(mago4);
            em.persist(mago5);
            em.persist(mago6);

            vista.imprimirMensaje("Se ha insertado correctamente: ");
            vista.imprimirMensaje(
                    "Magos: " + mago1.getNombre() + ", " + mago2.getNombre() + ", " + mago3.getNombre());

            tx.commit(); // Confirmar la transacción para guardar los cambios
        } catch (Exception e) {
            vista.imprimirMensaje("Error al insertar los personajes: " + e.getMessage());
        }
    }

    /**
     * Inicia el combate entre el mago y el monstruo jefe del bosque.
     */
    public void comenzarCombate() {

        // Validaciones básicas
        if (modelo.getMagos() == null || modelo.getBosque() == null) {
            vista.imprimirMensaje("Error: el juego no ha sido inicializado.");
            return;
        }

        List<Mago> magos = modelo.getMagos();
        List<Monstruo> monstruos = modelo.getBosque().getMonstruos();
        List<Monstruo> monstruosVivos = monstruos.stream()
                .filter(m -> m.getVida() > 0)
                .toList();

        Bosque bosque = modelo.getBosque();
        Monstruo monstruoJefe = bosque.getMonstruoJefe();

        if (monstruoJefe == null) {
            vista.imprimirMensaje("Error: el bosque no tiene monstruo jefe.");
            return;
        }

        int turno = 1;
        boolean combateTerminado = false;

        vista.imprimirMensaje("");
        vista.imprimirMensaje("**********************************");
        vista.imprimirMensaje("¡Encuentro randomizado obtenido!");
        vista.imprimirMensaje("**********************************");

        vista.imprimirMensaje("\n**********************************");
        vista.imprimirMensaje(
                "Comienza el combate en el " + bosque.getNombre() +
                        "\nLos magos " + magos.stream().map(Mago::getNombre).reduce((a, b) -> a + ", " + b).orElse("") +
                        "\nVS\nEl monstruo Jefe " + monstruoJefe.getNombre());
        vista.imprimirMensaje("**********************************\n");

        // Bucle del combate
        while (!combateTerminado) {
            vista.imprimirMensaje("Turno: " + turno);

            // Verificar si el monstruo jefe ha sido derrotado
            if (monstruoJefe.getVida() <= 0) {
                vista.imprimirMensaje("¡El monstruo jefe ha caído!");

                // Cambiar monstruo jefe si es derrotado
                int indexNuevoJefe = (int) (Math.random() * monstruosVivos.size());

                monstruoJefe = monstruosVivos.get(indexNuevoJefe);
                vista.imprimirMensaje("¡El nuevo monstruo jefe es " + monstruoJefe.getNombre() + "!");
            }

            // Turno de los magos
            for (Mago mago : magos) {
                if (monstruoJefe.getVida() <= 0) {
                    break; // Salir si el monstruo ya ha sido derrotado
                }

                // Hechizo aleatorio
                Hechizo hechizo = modelo.getListaHechizos()
                        .get((int) (Math.random() * modelo.getListaHechizos().size()));
                mago.lanzarHechizo(monstruos, hechizo);
            }

            // Turno de los monstruos
            for (Monstruo m : monstruos) {
                if (m.getVida() > 0) {
                    // Atacar mago aleatorio
                    int indexMago = (int) (Math.random() * magos.size());
                    Mago mago = magos.get(indexMago);

                    m.atacar(mago);

                    if (magos.get(0).getVida() <= 0) {
                        vista.imprimirMensaje("El mago " + magos.get(0).getNombre() + " ha sido derrotado.");
                        break;
                    }
                }
            }

            // Turno dragón (si el bosque tiene dragón)
            if (bosque.getDragon() != null && bosque.getDragon().getResistencia() > 0) {
                // Atacar monstruo jefe
                bosque.getDragon().exhalar(monstruoJefe);
            }

            // Stats
            for (Mago mago : magos) {
                if (mago.getVida() <= 0) {
                    vista.imprimirMensaje("Mago: " + mago.getNombre() + " ha sido derrotado.");
                } else {
                    vista.imprimirMensaje("Mago: " + mago.getNombre() + " - Vida: " + mago.getVida());
                }
            }

            vista.imprimirMensaje("Monstruo Jefe: " + monstruoJefe.getNombre() + " - Vida: " + monstruoJefe.getVida());

            for (Monstruo m : monstruos) {
                if (m != monstruoJefe) { // No repetir el jefe
                    if (m.getVida() <= 0) {
                        vista.imprimirMensaje("Monstruo: " + m.getNombre() + " ha sido derrotado.");
                    } else {
                        vista.imprimirMensaje("Monstruo: " + m.getNombre() + " - Vida: " + m.getVida());
                    }
                }
            }

            if (bosque.getDragon() != null) {
                if (bosque.getDragon().getResistencia() <= 0) {
                    vista.imprimirMensaje("El dragón " + bosque.getDragon().getNombre() + " ha sido derrotado.");
                } else {
                    vista.imprimirMensaje("Dragón: " + bosque.getDragon().getNombre() + " - Resistencia: "
                            + bosque.getDragon().getResistencia());
                }
            }

            vista.imprimirMensaje("----------------------------------");

            turno++;

            if (monstruos.stream().allMatch(m -> m.getVida() <= 0)) {
                vista.imprimirMensaje(
                        "¡Los magos han derrotado al monstruo jefe " + monstruoJefe.getNombre() + "y a sus lacayos!");
                combateTerminado = true;
            } else if (magos.stream().allMatch(m -> m.getVida() <= 0)) {
                vista.imprimirMensaje(
                        "¡El monstruo jefe " + monstruoJefe.getNombre() + " ha derrotado a todos los magos!");
                combateTerminado = true;
            }
        }
    }
}
