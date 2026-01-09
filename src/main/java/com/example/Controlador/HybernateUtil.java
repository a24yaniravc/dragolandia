package com.example.Controlador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase HybernateUtil para gestionar la sesión con la base de datos.
 */
public class HybernateUtil {
    // ATRIBUTOS
    private static final EntityManagerFactory gestorEntidades = Persistence.createEntityManagerFactory("dragolandiaServizo");

    /**
     * Devuelve una nueva sesión de EntityManager.
     * @return
     */
    public EntityManager getSesion() {
        return gestorEntidades.createEntityManager();
    }

    /**
     * Cierra la sesión del EntityManagerFactory.
     */
    public static void cerrarSesion() {
        if (gestorEntidades.isOpen()) {
            gestorEntidades.close();
        }
    }
}
