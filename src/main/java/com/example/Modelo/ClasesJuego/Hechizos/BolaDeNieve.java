package com.example.modelo.clasesJuego.Hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.modelo.clasesJuego.Hechizo;
import com.example.modelo.clasesJuego.Monstruo;

/**
 * Clase BolaDeNieve que representa el hechizo Bola de Nieve.
 */
public class BolaDeNieve extends Hechizo {
    public BolaDeNieve() {
        super("Bola de Nieve");
    }

    /**
     * Efecto del hechizo Bola de Nieve: congela a un monstruo aleatorio (vida a 0).
     * @return Mapa de monstruos afectados y su daño recibido.
     */
    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> danhos = new HashMap<>();
        
        // Si no hay monstruos, no hacer nada
        if (monstruos.isEmpty()) {
            return danhos;
        }
        
        // Matar (congelar) un monstruo aleatorio
        Monstruo mons = monstruos.get((int) (Math.random() * monstruos.size()));
        int vidaAntes = mons.getVida();
        mons.setVida(0);
        danhos.put(mons, vidaAntes);
        return danhos;
    }

    /**
     * Devuelve el daño base del hechizo Bola de Nieve (-1 pq elimina toda la vida de un monstruo).
     */
    @Override
    public int getDanho() {
        return -1;
    }
}
