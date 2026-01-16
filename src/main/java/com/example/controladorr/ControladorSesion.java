package com.example.controladorr;

/**
 * Clase ControladorSesion para manejar la sesión de Hibernate.
 */
public class ControladorSesion {
    private static ControladorSesion instancia;
    private static final HybernateUtil hybernateUtil = new HybernateUtil();

    /**
     * Constructor privado (Singleton).
     */
    private ControladorSesion() {
    }

    /**
     * Devuelve la instancia única del controlador.
     */
    public static ControladorSesion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorSesion();
        }
        return instancia;
    }

    public HybernateUtil getHybernateUtil() {
        return hybernateUtil;
    }
}
