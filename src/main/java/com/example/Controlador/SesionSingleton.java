package com.example.Controlador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SesionSingleton {
    private static final EntityManagerFactory gestorEntidades = Persistence.createEntityManagerFactory("dragolandiaServizo");

    public static EntityManager getSesion() {
        return gestorEntidades.createEntityManager();
    }

    public static void cerrarSesion() {
        if (gestorEntidades.isOpen()) {
            gestorEntidades.close();
        }
    }
}
