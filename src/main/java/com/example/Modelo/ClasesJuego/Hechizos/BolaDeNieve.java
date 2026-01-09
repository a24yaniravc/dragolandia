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
    
        Monstruo mons = monstruos.get(random);
        mons.setVida(0); // INSTAKILL
    }

    /**
     * Devuelve el daño del hechizo. -1 indica efecto especial (instakill).
     */
    @Override
    public int getDanho() {
        return -1;
    }
}
