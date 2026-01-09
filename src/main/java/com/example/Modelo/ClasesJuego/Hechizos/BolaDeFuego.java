package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase que representa el hechizo Bola de Fuego.
 */
public class BolaDeFuego extends Hechizo {
    /**
     * Constructor de la clase BolaDeFuego.
     */
    public BolaDeFuego() {
        super("Bola de Fuego");
    }

    /**
     * Efecto del hechizo Bola de Fuego.
     */
    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Daño fijo de la Bola de Fuego
        int danio = 50; 
        
        // Número aleatorio de afectados
        int afectados = (int) (Math.random() * monstruos.size());
        
        // Victimas aleatorias
        for (int i = 0; i < afectados; i++) {
            Monstruo mons = monstruos.get((int) (Math.random() * monstruos.size()));
            mons.setVida(Math.max(0, mons.getVida() - danio));
        }
    }

    /**
     * Devuelve el daño del hechizo por monstruo.
     */
    @Override
    public int getDanho() {
        return 50;
    }
}
