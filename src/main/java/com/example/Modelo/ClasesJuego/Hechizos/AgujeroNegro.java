package com.example.Modelo.ClasesJuego.Hechizos;

import java.util.List;

import com.example.Modelo.ClasesJuego.Monstruo;

public class AgujeroNegro extends Hechizo {
    public AgujeroNegro() {
        super("Agujero Negro");
    }

    @Override
    public void efecto(List<Monstruo> monstruos) {
        for (Monstruo mons : monstruos) {
            mons.setVida(0);
        }
    }
}
