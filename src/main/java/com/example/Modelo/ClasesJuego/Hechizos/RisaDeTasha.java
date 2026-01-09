package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase RisaDeTasha que representa el hechizo Risa de Tasha.
 */
public class RisaDeTasha extends Hechizo {
    public RisaDeTasha() {
        super("Risa de Tasha");
    }
    
    /**
     * Efecto del hechizo Risa de Tasha: inflige daño a todos los monstruos.
     */
    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Daño fijo de la Bola de Fuego
        int danio = 15; 
        
        for (Monstruo mons : monstruos) {
            mons.setVida(mons.getVida()-danio);
        }
    }
}
