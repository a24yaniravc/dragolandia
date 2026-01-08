package com.example.Controlador;

import java.util.ArrayList;
import java.util.List;

import com.example.Controlador.GestorTablas.GestorBosque;
import com.example.Controlador.GestorTablas.GestorDragon;
import com.example.Controlador.GestorTablas.GestorHechizo;
import com.example.Controlador.GestorTablas.GestorMago;
import com.example.Controlador.GestorTablas.GestorMonstruo;
import com.example.Modelo.Modelo;
import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Dragon;
import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;
import com.example.Vista.Vista;

/**
 * Clase Controlador para manejar la lógica del juego.
 */
public class Controlador {
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
            modelo.getListaMagos().addAll(
                    GestorMago.getInstancia().obtenerTodos());

            modelo.getListaDragones().addAll(
                    GestorDragon.getInstancia().obtenerTodos());

            modelo.getListaBosques().addAll(
                    GestorBosque.getInstancia().obtenerTodos());

            modelo.getListaMonstruos().addAll(
                    GestorMonstruo.getInstancia().obtenerTodos());

            modelo.getListaHechizos().addAll(
                    GestorHechizo.getInstancia().obtenerTodos());

        } catch (Exception e) {
            vista.imprimirMensaje(
                    "Error al cargar datos: " + e.getMessage());
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
