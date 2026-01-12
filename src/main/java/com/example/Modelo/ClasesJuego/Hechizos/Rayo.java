package com.example.modelo.clasesJuego.Hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.modelo.clasesJuego.Hechizo;
import com.example.modelo.clasesJuego.Monstruo;

/**
 * Clase Rayo que representa un hechizo de tipo Rayo.
 */
public class Rayo extends Hechizo {
    public Rayo() {
        super("Rayo");
    }

    /**
     * Efecto del hechizo Rayo: inflige daño a un monstruo aleatorio.
     * @return Mapa de monstruos afectados y su daño recibido.
     */
    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> danhos = new HashMap<>();
        int danio = 25;
        
        // Si no hay monstruos, no hacer nada
        if (monstruos.isEmpty()) {
            return danhos;
        }
        
        // Infligir daño a un monstruo aleatorio
        Monstruo mons = monstruos.get((int) (Math.random() * monstruos.size()));
        int vidaAntes = mons.getVida();
        mons.setVida(Math.max(0, vidaAntes - danio));
        int danoReal = vidaAntes - mons.getVida();
        danhos.put(mons, danoReal);
        
        return danhos;
    }

    /**
     * Devuelve el daño base del hechizo Rayo.
     */
    @Override
    public int getDanho() {
        return 25;
    }
}
