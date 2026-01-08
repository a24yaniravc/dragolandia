package com.example.Controlador.GestorTablas;

import java.util.List;

import com.example.Controlador.ControladorSesion;
import com.example.Modelo.ClasesJuego.Hechizo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Hechizo.
 */
public class GestorHechizo {
    // Singleton
    private static GestorHechizo instancia;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private GestorHechizo() {}

    /**
     * Obtiene la instancia única del controlador de Hechizo.
     * @return
     */
    public static GestorHechizo getInstancia() {
        if (instancia == null) {
            instancia = new GestorHechizo();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo hechizo en la base de datos.
     * @param hechizo
     */
    public void insertarHechizo(Hechizo hechizo) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(hechizo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica un hechizo existente en la base de datos.
     * @param hechizo
     */
    public void modificarHechizo(Hechizo hechizo) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(hechizo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un hechizo de la base de datos.
     * @param hechizo
     */
    public void eliminarHechizo(Hechizo hechizo) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.remove(em.merge(hechizo));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los hechizos de la base de datos.
     * @return
     */
    public List<Hechizo> obtenerTodos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        try {
            return em.createQuery("FROM Hechizo", Hechizo.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
