package com.example.Controlador.GestorTablas;

import java.util.List;

import com.example.Controlador.ControladorSesion;
import com.example.Modelo.ClasesJuego.Monstruo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad Monstruo.
 */
public class GestorMonstruo {
    // Singleton
    private static GestorMonstruo instancia;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private GestorMonstruo() {}

    /**
     * Obtiene la instancia única del controlador de Monstruo.
     * @return
     */
    public static GestorMonstruo getInstancia() {
        if (instancia == null) {
            instancia = new GestorMonstruo();
        }
        return instancia;
    }

    /**
     * Inserta un nuevo monstruo en la base de datos.
     * @param monstruo
     */
    public void insertarMonstruo(Monstruo monstruo) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(monstruo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Modifica un monstruo existente en la base de datos.
     * @param monstruo
     */
    public void modificarMonstruo(Monstruo monstruo) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(monstruo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un monstruo de la base de datos.
     * @param monstruo
     */
    public void eliminarMonstruo(Monstruo monstruo) {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.remove(em.merge(monstruo));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina todos los datos de la tabla Monstruo.
     */
    public void borrarDatos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.createQuery("DELETE FROM Monstruo").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los monstruos de la base de datos.
     * @return
     */
    public List<Monstruo> obtenerTodos() {
        EntityManager em = ControladorSesion.getInstancia()
                .getHybernateUtil().getSesion();
        try {
            return em.createQuery("FROM Monstruo", Monstruo.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
