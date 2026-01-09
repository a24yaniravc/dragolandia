package com.example.Controlador;

import com.example.Controlador.GestorTablas.GestorBosque;
import com.example.Controlador.GestorTablas.GestorDragon;
import com.example.Controlador.GestorTablas.GestorHechizo;
import com.example.Controlador.GestorTablas.GestorMago;
import com.example.Controlador.GestorTablas.GestorMonstruo;
import com.example.Modelo.Modelo;
import com.example.Modelo.MotorCombate;
import com.example.Vista.Vista;

/**
 * Clase Controlador para manejar la lógica del juego.
 */
public class Controlador {
    private static Controlador instancia;
    private final Modelo modelo;
    private final Vista vista;

    /**
     * Constructor privado (Singleton).
     */
    private Controlador() {
        this.vista = new Vista();
        this.modelo = Modelo.getInstancia();
    }

    /**
     * Devuelve la instancia única del controlador.
     */
    public static Controlador getInstancia() {
        if (instancia == null) {
            instancia = new Controlador();
        }
        return instancia;
    }

    // GETTERS
    public Modelo getModelo() {
        return modelo;
    }

    public Vista getVista() {
        return vista;
    }

    // MÉTODOS
    /**
     * Método para cargar los personajes desde la base de datos.
     */
    public void loadFromDatabase() {
        try {
            modelo.limpiarListas();

            modelo.getListaMagos().addAll(GestorMago.getInstancia().obtenerTodos());
            modelo.getListaDragones().addAll(GestorDragon.getInstancia().obtenerTodos());
            modelo.getListaBosques().addAll(GestorBosque.getInstancia().obtenerTodos());
            modelo.getListaMonstruos().addAll(GestorMonstruo.getInstancia().obtenerTodos());
            modelo.getListaHechizos().addAll(GestorHechizo.getInstancia().obtenerTodos());

        } catch (Exception e) {
            vista.imprimirMensaje("Error al cargar datos: " + e.getMessage());
        }
    }

    public void comenzarCombate() {
        MotorCombate motor = new MotorCombate(modelo, vista);
        motor.comenzarCombate();
    }
}
