package com.example.Vista;

import java.util.List;
import java.util.Scanner;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Mago;

/**
 * Clase Vista para manejar la presentación de mensajes.
 */
public class Vista {
    Controlador controlador;
    Scanner scanner = new Scanner(System.in);

    public void imprimirMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void seleccionMago(){
        controlador = new Controlador();

        // Obtener los nombres de los magos disponibles
        List<String> opciones = controlador.getModelo().getListaMagos().stream()
                .map(Mago::getNombre)
                .toList();
        List<String> stats = controlador.getModelo().getListaMagos().stream()
                .map(m -> "[Vida: " + m.getVida() + ", Nivel de magia: " + m.getNivelMagia() + ", Conjuros: " + m.getConjuros() + "]")
                .toList();
        String input = "";

        imprimirMensaje("---- Selección de personaje ----");

        imprimirMensaje("Magos disponibles:");
        for (String opcion : opciones) {
            imprimirMensaje("- " + opcion + " " + stats.get(opciones.indexOf(opcion)));
        }

        imprimirMensaje("");
        imprimirMensaje("Seleccione un mago por su nombre:");
        scanner.nextLine(); // Limpiar el buffer
        input = scanner.nextLine();

        while (input.isEmpty() || !opciones.contains(input)) {
            imprimirMensaje("");
            imprimirMensaje("Selección inválida. Por favor, elige un mago válido:");
            input = scanner.nextLine();
        }

        String seleccion = input;
        Mago magoSeleccionado = controlador.getModelo().getListaMagos().stream()
                .filter(m -> m.getNombre().equals(seleccion))
                .findFirst()
                .orElse(null);

        controlador.getModelo().setMago(magoSeleccionado);
    }
}
