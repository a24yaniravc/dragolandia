package com.example;

import java.util.Scanner;

import com.example.Controlador.Controlador;
import com.example.Controlador.HybernateUtil;

/**
 * Clase principal para iniciar la aplicación.
 */
public final class Principal {

    private static final Controlador controlador = Controlador.getInstancia();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        controlador.getVista()
                .imprimirMensaje("¿Desea añadir los personajes a la base de datos? (S/N)");

        controlador.loadFromDatabase();
        controlador.getModelo().inicializarJuego();
        controlador.comenzarCombate();

        HybernateUtil.cerrarSesion();
        sc.close();
    }
}
