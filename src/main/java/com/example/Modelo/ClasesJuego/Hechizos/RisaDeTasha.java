package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase RisaDeTasha que representa el hechizo Risa de Tasha.
 */
public class RisaDeTasha extends Hechizo {
    public RisaDeTasha() {
        super("Risa de Tasha");
    }

    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> daños = new HashMap<>();
        int danio = 15;
        
        for (Monstruo mons : monstruos) {
            int vidaAntes = mons.getVida();
            mons.setVida(Math.max(0, vidaAntes - danio));
            int danoReal = vidaAntes - mons.getVida();
            daños.put(mons, danoReal);
        }
        
        return daños;
    }

    @Override
    public int getDanho() {
        return 15;
    }
}
