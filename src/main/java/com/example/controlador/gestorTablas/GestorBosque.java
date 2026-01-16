package com.example.controlador.gestorTablas;

import java.util.List;

import com.example.controlador.ControladorSesion;
import com.example.modelo.clasesJuego.Bosque;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Bosque.
 */
public class GestorBosque {
    // Singleton
    private static GestorBosque instancia;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private GestorBosque() {}

    /**
     * Obtiene la instancia única del controlador de Bosque.
     * @return
     */
    public static GestorBosque getInstancia() {
        if (instancia == null) {
            instancia = new GestorBosque();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo bosque en la base de datos.
     * @param bosque
     */
    public void insertarBosque(Bosque bosque) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(bosque);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica un bosque existente en la base de datos.
     * @param bosque
     */
    public void modificarBosque(Bosque bosque) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(bosque);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un bosque de la base de datos.
     * @param bosque
     */
    public void eliminarBosque(Bosque bosque) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.remove(em.merge(bosque));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los bosques de la base de datos.
     * @return
     */
    public List<Bosque> obtenerTodos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        try {
            return em.createQuery("FROM Bosque", Bosque.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Borra todos los bosques de la base de datos.
     */
    public void borrarDatos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.createQuery("DELETE FROM Bosque").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
