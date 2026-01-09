package com.example.Modelo;

import java.util.List;

import com.example.Controlador.ControladorSesion;
import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;
import com.example.Vista.Vista;

public class MotorCombate {
    private final Modelo modelo;
    private final Vista vista;

    /**
     * Constructor del Motor de Combate.
     * @param modelo
     * @param vista
     */
    public MotorCombate(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

     /**
     * Inicia el combate entre el mago y el monstruo jefe del bosque.
     */
    public void comenzarCombate() {
        // Validaciones iniciales
        if (modelo.getMagos().isEmpty() || modelo.getBosque() == null) {
            vista.imprimirMensaje("Error: el juego no ha sido inicializado correctamente.");
        } else {

            // Preparación del combate
            List<Mago> magos = modelo.getMagos();
            Bosque bosque = modelo.getBosque();

            ControladorSesion.getInstancia().getHybernateUtil().getSesion();
            List<Monstruo> monstruos = modelo.getMonstruos();
            Monstruo monstruoJefe = modelo.getMonstruoJefe();

            // Validación del monstruo jefe
            if (monstruoJefe != null) {
                int turno = 1;
                boolean combateTerminado = false;

                vista.imprimirMensaje("");
                vista.imprimirMensaje("**********************************");
                vista.imprimirMensaje("¡Encuentro randomizado obtenido!");
                vista.imprimirMensaje("**********************************");

                vista.imprimirMensaje("\n**********************************");
                vista.imprimirMensaje(
                        "Comienza el combate en el " + bosque.getNombre() +
                                "\nLos magos " + magos.stream()
                                        .map(Mago::getNombre)
                                        .reduce((a, b) -> a + ", " + b)
                                        .orElse("")
                                +
                                "\nVS\nEl monstruo Jefe " + monstruoJefe.getNombre() + " y sus lacayos!");
                vista.imprimirMensaje("**********************************\n");

                // Bucle principal del combate
                while (!combateTerminado) {

                    vista.imprimirMensaje("Turno: " + turno);

                    // Recalcular monstruos vivos
                    /*List<Monstruo> monstruosVivos = monstruos.stream()
                            .filter(m -> m.getVida() > 0)
                            .toList();

                    if (monstruosVivos.isEmpty()) {
                        vista.imprimirMensaje("¡Todos los monstruos han sido derrotados!");
                        break;
                    }

                    // Si el jefe ha muerto, elegir otro
                    if (monstruoJefe.getVida() <= 0) {
                        int indexNuevoJefe = (int) (Math.random() * monstruosVivos.size());
                        monstruoJefe = monstruosVivos.get(indexNuevoJefe);
                        vista.imprimirMensaje(
                                "¡El nuevo monstruo jefe es " + monstruoJefe.getNombre() + "!");
                    }*/

                    // Turno de los magos
                    for (Mago mago : magos) {
                        if (mago.getVida() <= 0 || monstruoJefe.getVida() <= 0) {
                            continue;
                        }

                        Hechizo hechizo = modelo.getListaHechizos()
                                .get((int) (Math.random() * modelo.getListaHechizos().size()));

                        mago.lanzarHechizo(monstruos, hechizo);
                        vista.imprimirMensaje(
                                "El mago " + mago.getNombre() +
                                        " lanza el hechizo " + hechizo.getNombre() +
                                        " contra los monstruos.");
                    }

                    // Turno de los monstruos
                    for (Monstruo monstruo : monstruos) {
                        if (monstruo.getVida() > 0) {

                            List<Mago> magosVivos = magos.stream()
                                    .filter(m -> m.getVida() > 0)
                                    .toList();

                            if (magosVivos.isEmpty()) {
                                combateTerminado = true;
                                break;
                            }

                            Mago magoAtacado = magosVivos
                                    .get((int) (Math.random() * magosVivos.size()));

                            monstruo.atacar(magoAtacado);

                            if (magoAtacado.getVida() <= 0) {
                                vista.imprimirMensaje(
                                        "El mago " + magoAtacado.getNombre() + " ha sido derrotado.");
                            }
                        }
                    }

                    // Turno del dragón (si existe)
                    if (bosque.getDragon() != null && bosque.getDragon().getResistencia() > 0) {
                        bosque.getDragon().exhalar(monstruoJefe);
                    }

                    // Mostrar estado de los magos
                    for (Mago mago : magos) {
                        if (mago.getVida() <= 0) {
                            vista.imprimirMensaje(
                                    "Mago: " + mago.getNombre() + " ha sido derrotado.");
                        } else {
                            vista.imprimirMensaje(
                                    "Mago: " + mago.getNombre() + " - Vida: " + mago.getVida());
                        }
                    }

                    // Estado del monstruo jefe
                    vista.imprimirMensaje(
                            "Monstruo Jefe: " + monstruoJefe.getNombre() +
                                    " - Vida: " + monstruoJefe.getVida());

                    // Estado del resto de monstruos
                    for (Monstruo monstruo : monstruos) {
                        if (monstruo != monstruoJefe) {
                            if (monstruo.getVida() <= 0) {
                                vista.imprimirMensaje(
                                        "Monstruo: " + monstruo.getNombre() + " ha sido derrotado.");
                            } else {
                                vista.imprimirMensaje(
                                        "Monstruo: " + monstruo.getNombre() +
                                                " - Vida: " + monstruo.getVida());
                            }
                        }
                    }

                    // Estado del dragón
                    if (bosque.getDragon() != null) {
                        if (bosque.getDragon().getResistencia() <= 0) {
                            vista.imprimirMensaje(
                                    "El dragón " + bosque.getDragon().getNombre() + " ha sido derrotado.");
                        } else {
                            vista.imprimirMensaje(
                                    "Dragón: " + bosque.getDragon().getNombre() +
                                            " - Resistencia: " + bosque.getDragon().getResistencia());
                        }
                    }

                    vista.imprimirMensaje("----------------------------------");
                    turno++;

                    // Condiciones de fin
                    if (monstruos.stream().allMatch(m -> m.getVida() <= 0)) {
                        vista.imprimirMensaje(
                                "¡Los magos han derrotado al monstruo jefe " +
                                        monstruoJefe.getNombre() + " y a sus lacayos!");
                        combateTerminado = true;
                    } else if (magos.stream().allMatch(m -> m.getVida() <= 0)) {
                        vista.imprimirMensaje(
                                "¡El monstruo jefe " + monstruoJefe.getNombre() +
                                        " ha derrotado a todos los magos!");
                        combateTerminado = true;
                    }
                }
            } else {
                vista.imprimirMensaje("Error: el bosque no tiene monstruo jefe.");
            }
        }
    }
}
