package com.example.controladorr.gestorTablass;

import java.util.List;

import com.example.controladorr.ControladorSesion;
import com.example.modeloo.clasesJuegoo.Dragon;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Dragon.
 */
public class GestorDragon {
    // Singleton
    private static GestorDragon instancia;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private GestorDragon() {}

    /**
     * Obtiene la instancia única del controlador de Dragon.
     * @return
     */
    public static GestorDragon getInstancia() {
        if (instancia == null) {
            instancia = new GestorDragon();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo dragón en la base de datos.
     * @param dragon
     */
    public void insertarDragon(Dragon dragon) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(dragon);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica un dragón existente en la base de datos.
     * @param dragon
     */
    public void modificarDragon(Dragon dragon) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(dragon);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * * Elimina un dragón de la base de datos.
     * @param dragon
     */
    public void eliminarDragon(Dragon dragon) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.remove(em.merge(dragon));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina todos los dragones de la base de datos.
     */
    public void borrarDatos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.createQuery("DELETE FROM Dragon").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los dragones de la base de datos.
     * @return
     */
    public List<Dragon> obtenerTodos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        try {
            return em.createQuery("FROM Dragon", Dragon.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
