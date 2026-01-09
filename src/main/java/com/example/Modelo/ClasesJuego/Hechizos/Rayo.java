package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase Rayo que representa un hechizo de tipo Rayo.
 */
public class Rayo extends Hechizo {
    public Rayo() {
        super("Rayo");
    }

    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> daños = new HashMap<>();
        int danio = 25;
        
        if (monstruos.isEmpty()) {
            return daños;
        }
        
        Monstruo mons = monstruos.get((int) (Math.random() * monstruos.size()));
        int vidaAntes = mons.getVida();
        mons.setVida(Math.max(0, vidaAntes - danio));
        int danoReal = vidaAntes - mons.getVida();
        daños.put(mons, danoReal);
        
        return daños;
    }

    @Override
    public int getDanho() {
        return 25;
    }
}
