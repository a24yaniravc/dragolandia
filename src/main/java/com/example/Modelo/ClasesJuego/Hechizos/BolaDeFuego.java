package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase que representa el hechizo Bola de Fuego.
 */
public class BolaDeFuego extends Hechizo {
    public BolaDeFuego() {
        super("Bola de Fuego");
    }

    @Override
    public Map<Monstruo, Integer> efecto(List<Monstruo> monstruos) {
        Map<Monstruo, Integer> danhos = new HashMap<>();
        int danio = 50;
        
        if (monstruos.isEmpty()) {
            return danhos;
        }
        
        int afectados = (int) (Math.random() * monstruos.size()) + 1;
        Set<Monstruo> yaAfectados = new HashSet<>();
        
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

    @Override
    public int getDanho() {
        return 50;
    }
}
