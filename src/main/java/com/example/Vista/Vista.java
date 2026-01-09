package com.example.Vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.Modelo.ClasesJuego.Mago;

/**
 * Clase Vista para manejar la presentación de mensajes.
 */
public class Vista {
    private final Scanner scanner;

    public Vista() {
        this.scanner = new Scanner(System.in);
    }

    public void imprimirMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Permite al usuario seleccionar magos de la lista disponible.
     *
     * @param listaMagos Lista de magos disponibles para selección
     * @return Lista de magos seleccionados
     */
    public List<Mago> seleccionMago(List<Mago> listaMagos) {
        List<String> nombres = listaMagos.stream()
                                         .map(Mago::getNombre)
                                         .toList();

        List<String> stats = listaMagos.stream()
                                       .map(m -> "[Vida: " + m.getVida() + ", Magia: " + m.getNivelMagia() + ", Conjuros: " + m.getConjuros() + "]")
                                       .toList();

        imprimirMensaje("---- Selección de personajes ----");
        imprimirMensaje("Magos disponibles:");
        for (int i = 0; i < nombres.size(); i++) {
            imprimirMensaje("- " + nombres.get(i) + " " + stats.get(i));
        }

        int cantidadMagos = pedirCantidadMagos();
        List<Mago> seleccionados = new ArrayList<>();

        for (int i = 1; i <= cantidadMagos; i++) {
            Mago mago = pedirMagoPorNombre("Selecciona el mago número " + i + ":", listaMagos, nombres);
            seleccionados.add(mago);
            imprimirMensaje("Has seleccionado a " + mago.getNombre() + " como tu mago.");
        }

        return seleccionados;
    }

    // ---------------- Métodos privados de ayuda ----------------

    private int pedirCantidadMagos() {
        int cantidad = 0;
        boolean valido = false;

        imprimirMensaje("\n¿Cuántos magos quieres seleccionar? (min 2, max 4):");

        while (!valido) {
            try {
                cantidad = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea
                if (cantidad >= 2 && cantidad <= 4) {
                    valido = true;
                } else {
                    imprimirMensaje("Cantidad inválida. Ingresa un número entre 2 y 4:");
                }
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine(); // Limpiar buffer
                imprimirMensaje("Por favor, ingresa un número válido:");
            }
        }

        return cantidad;
    }

    private Mago pedirMagoPorNombre(String mensaje, List<Mago> listaMagos, List<String> nombres) {
        String input = "";
        Mago seleccionado = null;

        imprimirMensaje("\n" + mensaje);

        while (seleccionado == null) {
            input = scanner.nextLine().trim();
            if (nombres.contains(input)) {
                int index = nombres.indexOf(input);
                seleccionado = listaMagos.get(index);
            } else {
                imprimirMensaje("Selección inválida. Por favor, elige un mago válido:");
            }
        }

        return seleccionado;
    }
}
