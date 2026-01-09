package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase BolaDeNieve que representa el hechizo Bola de Nieve.
 */
public class BolaDeNieve extends Hechizo {
    /**
     * Constructor de la clase BolaDeNieve.
     */
    public BolaDeNieve() {
        super("Bola de Nieve");
    }
 
    /**
     * Efecto del hechizo Bola de Nieve: elimina instantáneamente a un monstruo aleatorio.
     */
    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Victima aleatoria
        int random = (int) (Math.random() * monstruos.size());
    
        com.example.Modelo.ClasesJuego.Monstruo mons = monstruos.get(random);
        mons.setVida(mons.getVida() - mons.getVida()); // INSTAKILL
    }
}
