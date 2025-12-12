package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Monstruo;

public class Rayo extends Hechizo {
    public Rayo() {
        super("Rayo");
    }

    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Daño fijo del Rayo
        int danio = 25; 

        // Victima aleatoria
        int random = (int) (Math.random() * monstruos.size());
    
        Monstruo mons = monstruos.get(random);
        mons.setVida(mons.getVida() - danio);
    }
    
}
