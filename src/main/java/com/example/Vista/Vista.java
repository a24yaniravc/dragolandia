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

    public void seleccionMago() {
        controlador = Controlador.getInstancia();

        // Obtener los nombres de los magos disponibles
        List<String> opciones = controlador.getModelo().getListaMagos().stream()
                .map(Mago::getNombre)
                .toList();
        List<String> stats = controlador.getModelo().getListaMagos().stream()
                .map(m -> "[Vida: " + m.getVida() + ", Nivel de magia: " + m.getNivelMagia() + ", Conjuros: "
                        + m.getConjuros() + "]")
                .toList();
        String input = "";

        imprimirMensaje("---- Selección de personaje ----");

        imprimirMensaje("Magos disponibles:");
        for (String opcion : opciones) {
            imprimirMensaje("- " + opcion + " " + stats.get(opciones.indexOf(opcion)));
        }

        imprimirMensaje("");
        imprimirMensaje("¿Cuántos magos quieres seleccionar?");
        int cantidadMagos = 0;
        
        boolean validInput = false;
        
        while (!validInput) {
            try {
                cantidadMagos = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea pendiente
                validInput = true;
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine(); // Limpiar el buffer
                imprimirMensaje("Por favor, ingresa un número válido:");
            }
        }

        List<Mago> magosSeleccionados = new java.util.ArrayList<>();

        for (int i = 0; i < cantidadMagos; i++) {
            imprimirMensaje("");
            imprimirMensaje("Selecciona el mago número " + (i + 1) + " por su nombre:");
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

            if (magoSeleccionado != null) {
                magosSeleccionados.add(magoSeleccionado);
                imprimirMensaje("Has seleccionado a " + magoSeleccionado.getNombre() + " como tu mago.");
            }
        }

        controlador.getModelo().setMagos(magosSeleccionados);
    }
}
