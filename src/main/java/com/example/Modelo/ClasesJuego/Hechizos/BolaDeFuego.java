package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Monstruo;

public class BolaDeFuego extends Hechizo {
    public BolaDeFuego() {
        super("Bola de Fuego");
    }

    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Daño fijo de la Bola de Fuego
        int danio = 50; 
        
        // Número aleatorio de afectados
        int afectados = (int) (Math.random() * monstruos.size());
        
        // Victimas aleatorias
        for (int i = 0; i < afectados; i++) {
            Monstruo mons = monstruos.get((int) (Math.random() * monstruos.size()));
            mons.setVida(mons.getVida() - danio);
        }
    }
    
}
