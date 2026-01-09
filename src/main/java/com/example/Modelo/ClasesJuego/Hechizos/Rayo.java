package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Hechizo;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase Rayo que representa un hechizo de tipo Rayo.
 */
public class Rayo extends Hechizo {
    /**
     * Constructor de la clase Rayo.
     */
    public Rayo() {
        super("Rayo");
    }

    /**
     * Efecto del hechizo Rayo: inflige daño a un monstruo aleatorio.
     */
    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Daño fijo del Rayo
        int danio = 25; 

        // Victima aleatoria
        int random = (int) (Math.random() * monstruos.size());
    
        Monstruo mons = monstruos.get(random);
        mons.setVida(Math.max(0, mons.getVida() - danio));
    }

    /**
     * Devuelve el daño del hechizo por monstruo.
     */
    @Override
    public int getDanho() {
        return 25;
    }
}
