package com.example.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;
import com.example.Modelo.ClasesJuego.Hechizos.AgujeroNegro;
import com.example.Modelo.ClasesJuego.Hechizos.BolaDeFuego;
import com.example.Modelo.ClasesJuego.Hechizos.BolaDeNieve;
import com.example.Modelo.ClasesJuego.Hechizos.Rayo;
import com.example.Modelo.ClasesJuego.Hechizos.RisaDeTasha;
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
     * 
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
        } else {
            // Obtener listas iniciales
            List<Mago> magos = modelo.getMagos();
            List<Monstruo> monstruos = modelo.getMonstruos();
            Bosque bosque = modelo.getBosque();

            // Monstruo jefe inicial
            Monstruo monstruoJefe = modelo.getMonstruoJefe();

            // Imprimir estado inicial
            vista.imprimirInicioCombate(magos, bosque, monstruoJefe);

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
                vista.imprimirEstado(magos, monstruos, monstruoJefe, bosque);

                turno++;
            }
        }
    }

    /* ===================== MÉTODOS AUXILIARES ===================== */

    /**
     * Verifica si el estado inicial del juego es válido para comenzar el combate.
     * 
     * @return
     */
    private boolean estadoInicialValido() {
        return modelo.getBosque() != null && !modelo.getMagos().isEmpty();
    }

    /**
     * Obtiene la lista de monstruos vivos.
     * 
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
     * 
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
     * 
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
     * Convierte un Hechizo genérico (de la BBDD) en su subclase con lógica.
     */
    private Hechizo obtenerInstanciaHechizo(Hechizo hechizoGenerico) {
        switch (hechizoGenerico.getNombre()) {
            case "Agujero Negro":
                return new AgujeroNegro();
            case "Bola de Fuego":
                return new BolaDeFuego();
            case "Bola de Nieve":
                return new BolaDeNieve();
            case "Rayo":
                return new Rayo();
            case "Risa de Tasha":
                return new RisaDeTasha();
            default:
                return hechizoGenerico;
        }
    }

    /**
     * Turno de los magos.
     * 
     * @param magos
     * @param monstruos
     */
    private void turnoMagos(List<Mago> magos, List<Monstruo> todosLosMonstruos) {
        vista.imprimirMensaje("\n--- Turno de los magos ---");

        for (Mago mago : magos) {
            if (mago.getVida() <= 0)
                continue;

            List<Monstruo> objetivosValidos = obtenerMonstruosVivos(todosLosMonstruos);
            if (objetivosValidos.isEmpty())
                break;

            // Seleccion hechizo aleatorio
            Hechizo hechizoDato = modelo.getListaHechizos()
                    .get((int) (Math.random() * modelo.getListaHechizos().size()));

            boolean conoce = mago.getConjuros().stream()
                    .anyMatch(h -> h.getNombre().equals(hechizoDato.getNombre()));

            if (!conoce) {
                vista.imprimirMensaje("El mago " + mago.getNombre() + " intenta lanzar " + hechizoDato.getNombre()
                        + ", ¡pero no lo conoce! El hechizo falla.");
                continue;
            }

            // Clase lógica del hechizo
            Hechizo hechizoLogico = obtenerInstanciaHechizo(hechizoDato);

            vista.imprimirMensaje("El mago " + mago.getNombre() + " lanza " + hechizoLogico.getNombre() + ".");

            // Efecto del hechizo
            Map<Monstruo, Integer> danhos = hechizoLogico.efecto(objetivosValidos);

            // Si el mapa está vacío a pesar de haber objetivos, algo falla en la clase del
            // hechizo
            if (danhos.isEmpty()) {
                vista.imprimirMensaje("DEBUG: El hechizo no hizo efecto.");
            }

            for (Map.Entry<Monstruo, Integer> entry : danhos.entrySet()) {
                Monstruo m = entry.getKey();
                int dano = entry.getValue();

                vista.imprimirMensaje("El hechizo " + hechizoLogico.getNombre() +
                        " inflige " + dano + " puntos de daño a " + m.getNombre() + ".");

                if (m.getVida() <= 0) {
                    vista.imprimirMensaje("¡El monstruo " + m.getNombre() + " ha sido derrotado!");
                }
            }
        }
    }

    /**
     * Turno de los monstruos.
     * 
     * @param monstruos
     * @param magos
     * @param bosque
     */
    private void turnoMonstruos(List<Monstruo> monstruos, List<Mago> magos, Bosque bosque) {
        vista.imprimirMensaje("\n--- Turno de los monstruos ---");

        // Obtener magos vivos
        List<Mago> magosVivos = obtenerMagosVivos(magos);
        boolean dragonVivo = bosque.getDragon() != null && bosque.getDragon().getResistencia() > 0;

        // Turno de cada monstruo
        for (Monstruo monstruo : monstruos) {
            if (monstruo.getVida() <= 0 || (magosVivos.isEmpty() && !dragonVivo))
                continue;

            // Decidir objetivo: mago o dragón
            boolean atacarDragon = dragonVivo && Math.random() < 0.3; // 30% de probabilidad de atacar al dragón
            if (atacarDragon) { // Atacar al dragón
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
            } else { // Atacar a un mago
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
     * 
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
     * Victoria de los magos.
     * 
     * @param jefe
     */
    private void victoriaMagos(Monstruo jefe) {
        vista.imprimirMensaje(
                "¡Los magos han derrotado al monstruo jefe " + jefe.getNombre() + "!");
        vista.imprimirMensaje("¡Felicidades, has ganado el combate y brindado paz al bosque!");
        combateActivo = false;
    }

    /**
     * Victoria de los monstruos.
     * 
     * @param jefe
     */
    private void victoriaMonstruos(Monstruo jefe) {
        vista.imprimirMensaje(
                "¡El monstruo jefe " + jefe.getNombre() +
                        " ha derrotado a todos los magos!");
        vista.imprimirMensaje("¡Los monstruos han tomado el control del bosque y sembrado el caos!");
        combateActivo = false;
    }
}