package com.example.controladorr.gestorTablass;

import java.util.List;

import com.example.controladorr.ControladorSesion;
import com.example.modeloo.clasesJuegoo.Hechizo;

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
     * Borra todos los datos de la tabla Hechizo.
     */
    public void borrarDatos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.createQuery("DELETE FROM Hechizo").executeUpdate();
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
