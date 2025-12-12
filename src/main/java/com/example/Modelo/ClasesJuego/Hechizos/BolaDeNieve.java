package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Monstruo;

public class BolaDeNieve extends Hechizo {
    public BolaDeNieve() {
        super("Bola de Nieve");
    }
 
    @Override
    public void efecto(List<Monstruo> monstruos) {
        // Victima aleatoria
        int random = (int) (Math.random() * monstruos.size());
    
        com.example.Modelo.ClasesJuego.Monstruo mons = monstruos.get(random);
        mons.setVida(mons.getVida() - mons.getVida()); // INSTAKILL
    }
}
