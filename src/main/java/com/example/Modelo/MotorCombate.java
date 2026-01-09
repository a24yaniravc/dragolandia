package com.example.Modelo;

import java.util.List;

import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;
import com.example.Vista.Vista;

public class MotorCombate {
    private final Modelo modelo;
    private final Vista vista;

    public MotorCombate(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void comenzarCombate() {

        if (!estadoInicialValido()) {
            vista.imprimirMensaje("Error: el juego no ha sido inicializado correctamente.");
            return;
        }

        List<Mago> magos = modelo.getMagos();
        List<Monstruo> monstruos = modelo.getMonstruos();
        Bosque bosque = modelo.getBosque();

        Monstruo monstruoJefe = modelo.getMonstruoJefe();

        imprimirInicioCombate(magos, bosque, monstruoJefe);

        int turno = 1;

        while (true) {

            vista.imprimirMensaje("----------------------------------\nTurno: " + turno);

            List<Monstruo> monstruosVivos = obtenerMonstruosVivos(monstruos);
            List<Mago> magosVivos = obtenerMagosVivos(magos);

            if (monstruosVivos.isEmpty()) {
                victoriaMagos(monstruoJefe);
                break;
            }

            if (magosVivos.isEmpty()) {
                victoriaMonstruos(monstruoJefe);
                break;
            }

            // Cambiar jefe si ha muerto
            if (monstruoJefe.getVida() <= 0) {
                monstruoJefe = elegirNuevoJefe(monstruosVivos);
                modelo.setMonstruoJefe(monstruoJefe);
            }

            turnoMagos(magosVivos, monstruosVivos);
            turnoMonstruos(monstruosVivos, magosVivos);
            turnoDragon(bosque, monstruoJefe);

            imprimirEstado(magos, monstruos, monstruoJefe, bosque);

            turno++;
        }
    }

    /* ===================== MÉTODOS AUXILIARES ===================== */

    private boolean estadoInicialValido() {
        return modelo.getBosque() != null && !modelo.getMagos().isEmpty();
    }

    private List<Monstruo> obtenerMonstruosVivos(List<Monstruo> monstruos) {
        return monstruos.stream().filter(m -> m.getVida() > 0).toList();
    }

    private List<Mago> obtenerMagosVivos(List<Mago> magos) {
        return magos.stream().filter(m -> m.getVida() > 0).toList();
    }

    private Monstruo elegirNuevoJefe(List<Monstruo> monstruosVivos) {
        Monstruo nuevoJefe = monstruosVivos
                .get((int) (Math.random() * monstruosVivos.size()));

        vista.imprimirMensaje("¡El nuevo monstruo jefe es " + nuevoJefe.getNombre() + "!");
        return nuevoJefe;
    }

    private void turnoMagos(List<Mago> magos, List<Monstruo> monstruos) {
        for (Mago mago : magos) {
            Hechizo hechizo = modelo.getListaHechizos()
                    .get((int) (Math.random() * modelo.getListaHechizos().size()));

            mago.lanzarHechizo(monstruos, hechizo);

            vista.imprimirMensaje(
                    "El mago " + mago.getNombre() +
                            " lanza " + hechizo.getNombre());
        }
    }

    private void turnoMonstruos(List<Monstruo> monstruos, List<Mago> magos) {
        for (Monstruo monstruo : monstruos) {
            Mago objetivo = magos.get((int) (Math.random() * magos.size()));
            monstruo.atacar(objetivo);

            if (objetivo.getVida() <= 0) {
                vista.imprimirMensaje("El mago " + objetivo.getNombre() + " ha sido derrotado.");
            }
        }
    }

    private void turnoDragon(Bosque bosque, Monstruo jefe) {
        if (bosque.getDragon() != null && bosque.getDragon().getResistencia() > 0) {
            bosque.getDragon().exhalar(jefe);
        }
    }

    private void imprimirInicioCombate(List<Mago> magos, Bosque bosque, Monstruo jefe) {

        String nombresMagos = magos.stream()
                .map(Mago::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        String mensaje = "\n**********************************\n" +
                "¡Encuentro randomizado obtenido!\n" +
                "**********************************\n\n**********************************\n" +
                "Comienza el combate en el " + bosque.getNombre() + "\n" +
                "Los magos " + nombresMagos + "\n" +
                "VS\n" +
                "El monstruo jefe " + jefe.getNombre() + " y sus lacayos\n" +
                "**********************************\n";

        vista.imprimirMensaje(mensaje);
    }

    private void imprimirEstado(List<Mago> magos, List<Monstruo> monstruos, Monstruo jefe, Bosque bosque) {

        vista.imprimirMensaje("\n--- Estado al final del turno ---");

        magos.forEach(m -> vista.imprimirMensaje(
                "Mago: " + m.getNombre() + " - Vida: " + m.getVida()));

        vista.imprimirMensaje(
                "Monstruo Jefe: " + jefe.getNombre() + " - Vida: " + jefe.getVida());

        monstruos.stream()
                .filter(m -> m != jefe)
                .forEach(m -> vista.imprimirMensaje(
                        "Monstruo: " + m.getNombre() + " - Vida: " + m.getVida()));

        if (bosque.getDragon() != null) {
            vista.imprimirMensaje(
                    "Dragón: " + bosque.getDragon().getNombre() +
                            " - Resistencia: " + bosque.getDragon().getResistencia());
        }

        vista.imprimirMensaje("----------------------------------");
    }

    private void victoriaMagos(Monstruo jefe) {
        vista.imprimirMensaje(
                "¡Los magos han derrotado al monstruo jefe " + jefe.getNombre() + "!");
    }

    private void victoriaMonstruos(Monstruo jefe) {
        vista.imprimirMensaje(
                "¡El monstruo jefe " + jefe.getNombre() +
                        " ha derrotado a todos los magos!");
    }
}
