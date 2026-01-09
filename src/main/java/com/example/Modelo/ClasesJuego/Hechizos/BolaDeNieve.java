package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase BolaDeNieve que representa el hechizo Bola de Nieve.
 */
public class BolaDeNieve extends Hechizo {
    public BolaDeNieve() {
        super("Bola de Nieve");
    }

    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> daños = new HashMap<>();
        
        if (monstruos.isEmpty()) {
            return daños;
        }
        
        Monstruo mons = monstruos.get((int) (Math.random() * monstruos.size()));
        int vidaAntes = mons.getVida();
        mons.setVida(0);
        daños.put(mons, vidaAntes);
        return daños;
    }

    @Override
    public int getDanho() {
        return -1;
    }
}
