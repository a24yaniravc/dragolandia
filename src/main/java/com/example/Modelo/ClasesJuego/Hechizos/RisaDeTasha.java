package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

public class RisaDeTasha extends Hechizo {
    public RisaDeTasha() {
        super("Risa de Tasha");
    }
    
    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Daño fijo de la Bola de Fuego
        int danio = 15; 
        
        for (Monstruo mons : monstruos) {
            mons.setVida(mons.getVida()-danio);
        }
    }
}
