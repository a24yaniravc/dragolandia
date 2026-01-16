package com.example.modeloo.clasesJuegoo.hechizos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.modeloo.clasesJuegoo.Hechizo;
import com.example.modeloo.clasesJuegoo.Monstruo;

/**
 * Clase que representa el hechizo Bola de Fuego.
 */
public class BolaDeFuego extends Hechizo {
    public BolaDeFuego() {
        super("Bola de Fuego");
    }

    /**
     * Efecto del hechizo Bola de Fuego: inflige daño a un número aleatorio de monstruos.
     * Devuelve un mapa con los monstruos afectados y el daño recibido por cada uno.
     * @return Mapa de monstruos afectados y su daño recibido.
     */
    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> danhos = new HashMap<>();
        int danio = 50;
        
        // Si no hay monstruos, no hacer nada
        if (monstruos.isEmpty()) {
            return danhos;
        }
        
        // Número aleatorio de monstruos a afectar (al menos 1)
        int afectados = (int) (Math.random() * monstruos.size()) + 1;
        Set<Monstruo> yaAfectados = new HashSet<>();
        
        // Afectar monstruos aleatoriamente
        for (int i = 0; i < afectados; i++) {
            Monstruo mons = monstruos.get((int) (Math.random() * monstruos.size()));
            
            if (!yaAfectados.contains(mons)) {
                int vidaAntes = mons.getVida();
                mons.setVida(Math.max(0, vidaAntes - danio));
                int danoReal = vidaAntes - mons.getVida();
                danhos.put(mons, danoReal);
                yaAfectados.add(mons);
            }
        }

        return danhos;
    }

    /**
     * Devuelve el daño base del hechizo Bola de Fuego.
     */
    @Override
    public int getDanho() {
        return 50;
    }
}
