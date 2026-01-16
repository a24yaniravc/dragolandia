package com.example.modeloo.clasesJuegoo.hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.modeloo.clasesJuegoo.Hechizo;
import com.example.modeloo.clasesJuegoo.Monstruo;

/**
 * Clase AgujeroNegro que representa el hechizo Agujero Negro.
 */
public class AgujeroNegro extends Hechizo {
    public AgujeroNegro() {
        super("Agujero Negro");
    }

    /**
     * Efecto del Agujero Negro (establece la vida de todos los monstruos a 0).
     * @return Mapa de monstruos afectados y su daño recibido.
     */
    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        // Mapa a devolver
        Map<Monstruo, Integer> danhos = new HashMap<>();
        
        for (Monstruo mons : monstruos) {
            int vidaAntes = mons.getVida();
            mons.setVida(0);
            danhos.put(mons, vidaAntes);
        }
        
        return danhos;
    }

    /**
     * Devuelve el daño base del hechizo Agujero Negro (-1 pq elimina toda la vida de varios monstruos).
     */
    @Override
    public int getDanho() {
        return -1;
    }
}
