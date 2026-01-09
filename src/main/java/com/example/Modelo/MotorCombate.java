package com.example.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;
import com.example.Vista.Vista;

/**
 * Clase MotorCombate que gestiona el flujo del combate entre magos y monstruos.
 */
public class MotorCombate {
    // Atributos
    private final Modelo modelo;
    private final Vista vista;
    private boolean combateActivo = true;

    /**
     * Constructor de la clase MotorCombate.
     * @param modelo
     * @param vista
     */
    public MotorCombate(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    /**
     * Método principal que inicia y gestiona el combate.
     */
    public void comenzarCombate() {
        // Verificar estado inicial
        if (!estadoInicialValido()) {
            vista.imprimirMensaje("Error: el juego no ha sido inicializado correctamente.");
            return;
        }

        // Obtener listas iniciales
        List<Mago> magos = modelo.getMagos();
        List<Monstruo> monstruos = modelo.getMonstruos();
        Bosque bosque = modelo.getBosque();

        // Monstruo jefe inicial
        Monstruo monstruoJefe = modelo.getMonstruoJefe();

        // Imprimir estado inicial
        imprimirInicioCombate(magos, bosque, monstruoJefe);

        int turno = 1;

        // Bucle principal del combate
        while (combateActivo) {

            vista.imprimirMensaje("----------------------------------\nTurno: " + turno);

            // Obtener listas de combatientes vivos
            List<Monstruo> monstruosVivos = obtenerMonstruosVivos(monstruos);
            List<Mago> magosVivos = obtenerMagosVivos(magos);

            // Comprobar condiciones de victoria
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

            // TURNOS
            turnoMagos(magosVivos, monstruosVivos);
            turnoMonstruos(monstruosVivos, magosVivos, bosque);
            turnoDragon(bosque, monstruoJefe);

            // Imprimir estado al final del turno
            imprimirEstado(magos, monstruos, monstruoJefe, bosque);

            turno++;
        }
    }

    /* ===================== MÉTODOS AUXILIARES ===================== */

    /**
     * Verifica si el estado inicial del juego es válido para comenzar el combate.
     * @return
     */
    private boolean estadoInicialValido() {
        return modelo.getBosque() != null && !modelo.getMagos().isEmpty();
    }

    /**
     * Obtiene la lista de monstruos vivos.
     * @param monstruos
     * @return
     */
    private List<Monstruo> obtenerMonstruosVivos(List<Monstruo> monstruos) {
        return new ArrayList<>(monstruos.stream()
                .filter(m -> m.getVida() > 0)
                .toList());
    }

    /**
     * Obtiene la lista de magos vivos.
     * @param magos
     * @return
     */
    private List<Mago> obtenerMagosVivos(List<Mago> magos) {
        return new ArrayList<>(magos.stream()
                .filter(m -> m.getVida() > 0)
                .toList());
    }

    /**
     * Elige un nuevo monstruo jefe aleatoriamente de la lista de monstruos vivos.
     * @param monstruosVivos
     * @return
     */
    private Monstruo elegirNuevoJefe(List<Monstruo> monstruosVivos) {
        Monstruo nuevoJefe = monstruosVivos
                .get((int) (Math.random() * monstruosVivos.size()));

        vista.imprimirMensaje("¡El nuevo monstruo jefe es " + nuevoJefe.getNombre() + "!");
        return nuevoJefe;
    }

    /**
     * Turno de los magos.
     * @param magos
     * @param monstruos
     */
    private void turnoMagos(List<Mago> magos, List<Monstruo> monstruos) {
        vista.imprimirMensaje("\n--- Turno de los magos ---");

        for (Mago mago : magos) {
            if (mago.getVida() <= 0)
                continue;

            Hechizo hechizo = modelo.getListaHechizos()
                    .get((int) (Math.random() * modelo.getListaHechizos().size()));

            // Verificar si el mago conoce el hechizo (comparando por nombre)
            boolean conoce = mago.getConjuros().stream()
                    .anyMatch(h -> h.getNombre().equals(hechizo.getNombre()));

            if (!conoce) {
                vista.imprimirMensaje("El mago " + mago.getNombre() + " intenta lanzar " + hechizo.getNombre()
                        + ", ¡pero no lo conoce! El hechizo falla.");
                continue;
            }

            vista.imprimirMensaje("El mago " + mago.getNombre() + " lanza " + hechizo.getNombre() + ".");

            Map<Monstruo, Integer> danhos = hechizo.efecto(monstruos);

            for (Map.Entry<Monstruo, Integer> entry : danhos.entrySet()) {
                Monstruo m = entry.getKey();
                int dano = entry.getValue();
                vista.imprimirMensaje("El hechizo " + hechizo.getNombre() +
                        " inflige " + dano + " puntos de daño a " + m.getNombre() +
                        ". Vida restante: " + m.getVida());
                if (m.getVida() <= 0) {
                    vista.imprimirMensaje("¡El monstruo " + m.getNombre() + " ha sido derrotado!");
                }
            }
        }
    }

    /**
     * Turno de los monstruos.
     * @param monstruos
     * @param magos
     * @param bosque
     */
    private void turnoMonstruos(List<Monstruo> monstruos, List<Mago> magos, Bosque bosque) {
        vista.imprimirMensaje("\n--- Turno de los monstruos ---");

        List<Mago> magosVivos = obtenerMagosVivos(magos);
        boolean dragonVivo = bosque.getDragon() != null && bosque.getDragon().getResistencia() > 0;

        for (Monstruo monstruo : monstruos) {
            if (monstruo.getVida() <= 0 || (magosVivos.isEmpty() && !dragonVivo))
                continue;

            boolean atacarDragon = dragonVivo && Math.random() < 0.3; // 30% de probabilidad de atacar al dragón
            if (atacarDragon) {
                int dano = monstruo.getFuerza();
                bosque.getDragon().setResistencia(Math.max(0, bosque.getDragon().getResistencia() - dano));
                vista.imprimirMensaje(
                        "El monstruo " + monstruo.getNombre() +
                                " ataca al dragón " + bosque.getDragon().getNombre() +
                                " infligiendo " + dano + " puntos de daño.");
                if (bosque.getDragon().getResistencia() <= 0) {
                    vista.imprimirMensaje("¡El dragón " + bosque.getDragon().getNombre() + " ha sido derrotado!");
                    dragonVivo = false;
                }
            } else {
                if (magosVivos.isEmpty())
                    continue;

                Mago objetivo = magosVivos.get((int) (Math.random() * magosVivos.size()));
                int dano = monstruo.getFuerza();
                objetivo.setVida(Math.max(0, objetivo.getVida() - dano));

                vista.imprimirMensaje(
                        "El monstruo " + monstruo.getNombre() +
                                " ataca al mago " + objetivo.getNombre() +
                                " infligiendo " + dano + " puntos de daño.");

                if (objetivo.getVida() <= 0) {
                    vista.imprimirMensaje("El mago " + objetivo.getNombre() + " ha sido derrotado.");
                    magosVivos.remove(objetivo); // evitar que otros monstruos sigan atacando al mismo mago muerto
                }
            }
        }
    }

    /**
     * Turno del dragón.
     * @param bosque
     * @param jefe
     */
    private void turnoDragon(Bosque bosque, Monstruo jefe) {
        if (bosque.getDragon() != null && bosque.getDragon().getResistencia() > 0 && jefe.getVida() > 0) {

            vista.imprimirMensaje("\n--- Turno del dragón ---");

            jefe.setVida(Math.max(0, jefe.getVida() - bosque.getDragon().getIntensidadFuego()));

            vista.imprimirMensaje(
                    "El dragón " + bosque.getDragon().getNombre() +
                            " exhala fuego sobre el monstruo jefe " + jefe.getNombre() +
                            ", infligiendo " + bosque.getDragon().getIntensidadFuego() + " puntos de daño.");
        }
    }

    /**
     * Imprime el estado inicial del combate.
     * @param magos
     * @param bosque
     * @param jefe
     */
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
                "**********************************";

        vista.imprimirMensaje(mensaje);
    }

    /**
     * Imprime el estado al final del turno.
     * @param magos
     * @param monstruos
     * @param jefe
     * @param bosque
     */
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

        vista.imprimirMensaje("\n----------------------------------");
    }

    /**
     * Victoria de los magos.
     * @param jefe
     */
    private void victoriaMagos(Monstruo jefe) {
        vista.imprimirMensaje(
                "¡Los magos han derrotado al monstruo jefe " + jefe.getNombre() + "!");
        combateActivo = false;
    }

    /**
     * Victoria de los monstruos.
     * @param jefe
     */
    private void victoriaMonstruos(Monstruo jefe) {
        vista.imprimirMensaje(
                "¡El monstruo jefe " + jefe.getNombre() +
                        " ha derrotado a todos los magos!");
        combateActivo = false;
    }
}