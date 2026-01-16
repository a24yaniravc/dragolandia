package com.example.modeloo.clasesJuegoo.hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.modeloo.clasesJuegoo.Hechizo;
import com.example.modeloo.clasesJuegoo.Monstruo;

/**
 * Clase RisaDeTasha que representa el hechizo Risa de Tasha.
 */
public class RisaDeTasha extends Hechizo {
    public RisaDeTasha() {
        super("Risa de Tasha");
    }

    /**
     * Efecto del hechizo Risa de Tasha: inflige daño a todos los monstruos.
     * @return Mapa de monstruos afectados y su daño recibido.
     */
    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> danhos = new HashMap<>();
        int danio = 15;
        
        for (Monstruo mons : monstruos) {
            int vidaAntes = mons.getVida();
            mons.setVida(Math.max(0, vidaAntes - danio));
            int danoReal = vidaAntes - mons.getVida();
            danhos.put(mons, danoReal);
        }
        
        return danhos;
    }

    /**
     * Devuelve el daño base del hechizo Risa de Tasha.
     */
    @Override
    public int getDanho() {
        return 15;
    }
}
