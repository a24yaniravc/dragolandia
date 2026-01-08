package com.example;

import java.util.Scanner;

import com.example.Controlador.Controlador;

/**
 * Clase principal para iniciar la aplicación.
 */
public final class Principal {
        private static final Controlador controlador = Controlador.getInstancia();
        private static Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {
                controlador.getVista().imprimirMensaje("¿Desea añadir los personajes a la base de datos? (S/N)");

                if (sc.nextLine().equalsIgnoreCase("s")) {
                        controlador.addCharacters();
                }

                controlador.loadFromDatabase();

                controlador.getModelo().inicializarJuego();
                controlador.comenzarCombate();
        }
}
