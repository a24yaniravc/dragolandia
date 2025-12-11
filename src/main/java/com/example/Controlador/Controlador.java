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
    // Atributos
    private final Modelo modelo;
    private final Vista vista;

    /**
     * Constructor de la clase Controlador.
     */
    public Controlador() {
        this.modelo = new Modelo();
        this.vista = new Vista();
    }

    /**
     * Obtiene el modelo asociado al controlador.
     * @return
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * Obtiene la vista asociada al controlador.
     * @return
     */
    public Vista getVista() {
        return vista;
    }

    /**
     * Inicia el combate entre el mago y el monstruo jefe del bosque.
     */
    public void ComenzarCombate() {
        int turno = 1;
        Boolean juegan = false;

        Mago mago = modelo.getMago();
        Bosque bosque = modelo.getBosque();
        Monstruo monstruo = bosque.getMonstruoJefe();

        vista.imprimirMensaje("\n**********************************");
        vista.imprimirMensaje("Comienza el combate en el " + bosque.getNombre() + ": \nEl mago " + mago.getNombre() + "\nVS\nEl monstruo " + monstruo.getNombre());
        vista.imprimirMensaje("**********************************\n");

        // Bucle del combate
        while (!juegan) {
            vista.imprimirMensaje("\n**********************************");
            vista.imprimirMensaje("Turno: " + turno);
            mago.lanzarHechizo(monstruo);

            if (monstruo.getVida() <= 0) {
                vista.imprimirMensaje("El mago es el vencedor en el " + bosque.getNombre() + "\n");
                juegan = true;
            } else {
                monstruo.atacar(mago);

                if (mago.getVida() <= 0) {
                    vista.imprimirMensaje("El monstruo es el vencedor en el " + bosque.getNombre() + "\n");
                    juegan = true; // también afecta la condición
                }
            }

            turno++;
            vista.imprimirMensaje("HP mago: " + mago.getVida());
            vista.imprimirMensaje("HP monstruo: " + monstruo.getVida());
            vista.imprimirMensaje("**********************************\n");
        }
    }
}
