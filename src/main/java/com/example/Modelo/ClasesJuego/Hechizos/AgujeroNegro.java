package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase AgujeroNegro que representa el hechizo Agujero Negro.
 */
public class AgujeroNegro extends Hechizo {
    public AgujeroNegro() {
        super("Agujero Negro");
    }

    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> daños = new HashMap<>();
        for (Monstruo mons : monstruos) {
            int vidaAntes = mons.getVida();
            mons.setVida(0);
            daños.put(mons, vidaAntes);
        }
        return daños;
    }

    @Override
    public int getDanho() {
        return -1;
    }
}
