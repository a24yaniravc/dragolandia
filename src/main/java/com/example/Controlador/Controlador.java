package com.example.controlador;

import java.util.ArrayList;
import java.util.List;

import com.example.controlador.gestorTablas.GestorBosque;
import com.example.controlador.gestorTablas.GestorDragon;
import com.example.controlador.gestorTablas.GestorHechizo;
import com.example.controlador.gestorTablas.GestorMago;
import com.example.controlador.gestorTablas.GestorMonstruo;
import com.example.modelo.Modelo;
import com.example.modelo.clasesJuego.Bosque;
import com.example.modelo.clasesJuego.Monstruo;
import com.example.vista.MotorCombate;
import com.example.vista.Vista;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Clase Controlador para manejar la lógica del juego.
 */
public class Controlador {
    // SINGLETON
    private static Controlador instancia;

    // ATRIBUTOS
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
    /**
     * Devuelve el modelo.
     * @return
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * Devuelve la vista.
     * @return
     */
    public Vista getVista() {
        return vista;
    }

    // MÉTODOS
    /**
     * Método para cargar los personajes desde la base de datos.
     */
    public void loadFromDatabase() {
        try {
            modelo.limpiarListas(); // Limpiar listas antes de cargar nuevos datos

            modelo.getListaMagos().addAll(GestorMago.getInstancia().obtenerTodos());
            modelo.getListaDragones().addAll(GestorDragon.getInstancia().obtenerTodos());
            modelo.getListaBosques().addAll(GestorBosque.getInstancia().obtenerTodos());
            modelo.getListaMonstruos().addAll(GestorMonstruo.getInstancia().obtenerTodos());
            modelo.getListaHechizos().addAll(GestorHechizo.getInstancia().obtenerTodos());

        } catch (Exception e) {
            vista.imprimirMensaje("Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Carga un bosque aleatorio para el juego desde la base de datos.
     * 
     * @return
     */
    public Bosque cargarBosqueAleatorioParaJuego() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();

        EntityTransaction tx = em.getTransaction();

        // Seleccionar un bosque aleatorio
        try {
            tx.begin();

            List<Bosque> bosques = em
                    .createQuery("FROM Bosque", Bosque.class)
                    .getResultList();

            if (bosques.isEmpty()) {
                throw new IllegalStateException("No hay bosques en la BD"); // Chequeo de seguridad
            }

            Bosque bosque = bosques.get(
                    (int) (Math.random() * bosques.size()));

            List<Monstruo> monstruosJuego = new ArrayList<>(bosque.getMonstruos());

            bosque.setMonstruos(monstruosJuego);

            tx.commit();
            return bosque;

        } finally {
            em.close();
        }
    }

    /**
     * Comienza el combate.
     */
    public void comenzarCombate() {
        MotorCombate motor = new MotorCombate(modelo, vista);

        Bosque bosque = cargarBosqueAleatorioParaJuego();
        modelo.setBosque(bosque);

        modelo.inicializarJuego();

        motor.comenzarCombate();
    }
}
