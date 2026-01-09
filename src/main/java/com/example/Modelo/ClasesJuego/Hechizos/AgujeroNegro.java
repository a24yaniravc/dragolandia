package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase AgujeroNegro que representa el hechizo Agujero Negro.
 */
public class AgujeroNegro extends Hechizo {
    /**
     * Constructor de la clase AgujeroNegro.
     */
    public AgujeroNegro() {
        super("Agujero Negro");
    }

    /**
     * Efecto del hechizo Agujero Negro: elimina a todos los monstruos del combate.
     */
    @Override
    public void efecto(List<Monstruo> monstruos) {
        for (Monstruo mons : monstruos) {
            mons.setVida(0);
        }
    }
}
