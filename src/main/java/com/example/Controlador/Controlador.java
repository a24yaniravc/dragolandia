package com.example.Controlador;

import com.example.Modelo.Modelo;
import com.example.Modelo.ClasesJuego.Bosque;
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

    /**
     * Inicia el combate entre el mago y el monstruo jefe del bosque.
     */
    public void comenzarCombate() {

        // Validaciones básicas
        if (modelo.getMagos() == null || modelo.getBosque() == null) {
            vista.imprimirMensaje("Error: el juego no ha sido inicializado.");
            return;
        }

        Mago mago = modelo.getMagos().get(0);
        Bosque bosque = modelo.getBosque();
        Monstruo monstruo = bosque.getMonstruoJefe();

        if (monstruo == null) {
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
                "\nEl mago " + mago.getNombre() +
                "\nVS\nEl monstruo " + monstruo.getNombre()
        );
        vista.imprimirMensaje("**********************************\n");

        // Bucle del combate
        while (!combateTerminado) {

            vista.imprimirMensaje("Turno: " + turno);

            // Turno del mago
            mago.lanzarHechizo(monstruo);

            if (monstruo.getVida() <= 0) {
                vista.imprimirMensaje("El mago es el vencedor en el " + bosque.getNombre());
                combateTerminado = true;
            } else {
                // Turno del monstruo
                monstruo.atacar(mago);

                if (mago.getVida() <= 0) {
                    vista.imprimirMensaje("El monstruo es el vencedor en el " + bosque.getNombre());
                    combateTerminado = true;
                }
            }

            vista.imprimirMensaje("HP mago: " + mago.getVida());
            vista.imprimirMensaje("HP monstruo: " + monstruo.getVida());
            vista.imprimirMensaje("----------------------------------");

            turno++;
        }
    }
}
