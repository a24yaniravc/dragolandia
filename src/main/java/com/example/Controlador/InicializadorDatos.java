package com.example.Controlador;

import java.util.ArrayList;
import java.util.List;

import com.example.Controlador.GestorTablas.*;
import com.example.Modelo.ClasesJuego.*;

/**
 * Clase para inicializar los datos de la aplicación.
 */
public class InicializadorDatos {

    /**
     * Constructor privado para evitar instanciación.
     */
    private InicializadorDatos() {}

    /**
     * Borra todos los datos existentes en la base de datos.
     */
    public static void borrarDatos() {
        GestorBosque.getInstancia().borrarDatos();
        GestorMago.getInstancia().borrarDatos();
        GestorHechizo.getInstancia().borrarDatos();
        GestorMonstruo.getInstancia().borrarDatos();
        GestorDragon.getInstancia().borrarDatos();
    }

    /**
     * Carga los datos iniciales en la base de datos.
     */
    public static void cargarDatosIniciales() {

        // DRAGONES
        Dragon dragon = new Dragon("Draco", 200, 50);
        Dragon dragon2 = new Dragon("Smaug", 300, 80);
        Dragon dragon3 = new Dragon("Toothless", 150, 40);
        
        GestorDragon.getInstancia().insertarDragon(dragon);
        GestorDragon.getInstancia().insertarDragon(dragon2);
        GestorDragon.getInstancia().insertarDragon(dragon3);

        // MONSTRUOS
        Monstruo m1 = new Monstruo("Casper", 150, "espectro", 25);
        Monstruo m2 = new Monstruo("Shrek", 1000, "ogro", 500);
        Monstruo m3 = new Monstruo("Fionna", 300, "troll", 45);
        Monstruo m4 = new Monstruo("Princesa llama", 100, "espectro", 30);
        Monstruo m5 = new Monstruo("Gorgo el Terrible", 150, "ogro", 40);
        Monstruo m6 = new Monstruo("Pepe el Troll", 400, "troll", 50);

        GestorMonstruo gm = GestorMonstruo.getInstancia();
        gm.insertarMonstruo(m1);
        gm.insertarMonstruo(m2);
        gm.insertarMonstruo(m3);
        gm.insertarMonstruo(m4);
        gm.insertarMonstruo(m5);
        gm.insertarMonstruo(m6);

        // BOSQUES
        Bosque b1 = new Bosque("Bosque maldito", 1,
                new ArrayList<>(List.of(m5, m2, m3)), m1, dragon2);

        Bosque b2 = new Bosque("Selva oscura", 2,
                new ArrayList<>(List.of(m4, m6)), m2, dragon3);

        Bosque b3 = new Bosque("Pantano tenebroso", 5,
                new ArrayList<>(List.of(m1, m5, m6, m2)), m3, dragon);

        GestorBosque gb = GestorBosque.getInstancia();
        gb.insertarBosque(b1);
        gb.insertarBosque(b2);
        gb.insertarBosque(b3);

        // HECHIZOS
        Hechizo bolaFuego = new Hechizo("Bola de Fuego");
        Hechizo bolaNieve = new Hechizo("Bola de Nieve");
        Hechizo rayo = new Hechizo("Rayo");
        Hechizo risa = new Hechizo("Risa de Tasha");
        Hechizo agujero = new Hechizo("Agujero Negro");

        GestorHechizo gh = GestorHechizo.getInstancia();
        gh.insertarHechizo(bolaFuego);
        gh.insertarHechizo(bolaNieve);
        gh.insertarHechizo(rayo);
        gh.insertarHechizo(risa);
        gh.insertarHechizo(agujero);

        // MAGOS
        Mago mago1 = new Mago("Patosaurio", 4000, 100);
        Mago mago2 = new Mago("Fenixdor", 120, 40);
        Mago mago3 = new Mago("Lunargenta", 90, 25);
        Mago mago4 = new Mago("Gandalf", 110, 35);
        Mago mago5 = new Mago("Ling Wei", 95, 28);
        Mago mago6 = new Mago("Frieren", 250, 60);

        mago1.aprenderHechizo(bolaFuego);
        mago1.aprenderHechizo(agujero);
        mago1.aprenderHechizo(bolaNieve);
        mago1.aprenderHechizo(risa);
        mago1.aprenderHechizo(rayo);

        mago2.aprenderHechizo(rayo);
        mago3.aprenderHechizo(risa);
        mago4.aprenderHechizo(bolaFuego);
        mago5.aprenderHechizo(bolaNieve);
        mago6.aprenderHechizo(rayo);

        GestorMago gma = GestorMago.getInstancia();
        gma.insertarMago(mago1);
        gma.insertarMago(mago2);
        gma.insertarMago(mago3);
        gma.insertarMago(mago4);
        gma.insertarMago(mago5);
        gma.insertarMago(mago6);
    }
}
