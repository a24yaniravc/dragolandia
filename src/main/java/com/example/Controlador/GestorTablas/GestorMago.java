package com.example.controlador.gestorTablas;

import java.util.List;

import com.example.controlador.ControladorSesion;
import com.example.modelo.clasesJuego.Mago;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Mago.
 */
public class GestorMago {
    // Singleton
    private static GestorMago instancia;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private GestorMago() {}

    /**
     * Obtiene la instancia única del controlador de Mago.
     * @return
     */
    public static GestorMago getInstancia() {
        if (instancia == null) {
            instancia = new GestorMago();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo mago en la base de datos.
     * @param mago
     */
    public void insertarMago(Mago mago) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(mago);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica un mago existente en la base de datos.
     * @param mago
     */
    public void modificarMago(Mago mago) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(mago);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un mago de la base de datos.
     * @param mago
     */
    public void eliminarMago(Mago mago) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.remove(em.merge(mago));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Borra todos los datos de la tabla Mago.
     */
    public void borrarDatos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.createQuery("DELETE FROM Mago").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los magos de la base de datos.
     * @return
     */
    public List<Mago> obtenerTodos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        try {
            return em.createQuery("FROM Mago", Mago.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
