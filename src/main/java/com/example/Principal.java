package com.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

import com.example.Controlador.Controlador;
import com.example.Modelo.ClasesJuego.Bosque;
import com.example.Modelo.ClasesJuego.Mago;
import com.example.Modelo.ClasesJuego.Monstruo;

/**
 * Clase principal para iniciar la aplicación.
 */
public final class Principal {
    private final Controlador controlador = new Controlador();
    
    public static void main(String[] args) {
        Principal principal = new Principal();
        Controlador controlador = principal.controlador;

        // Creación del SessionFactory
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();) {

            Session session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();

            // Guardado de las entidades en la base de datos
            Mago mago = controlador.getModelo().getMago();
            session.merge(mago);

            Bosque bosque = controlador.getModelo().getBosque();
            session.merge(bosque);

            Monstruo monstruo = controlador.getModelo().getBosque().getMonstruoJefe();
            session.merge(monstruo);

            tx.commit(); // Confirmar la transacción para guardar los cambios

            // AVISOS 
            System.out.println("Se ha insertado correctamente: ");
            System.out.println(controlador.getModelo().getBosque());
            System.out.println(controlador.getModelo().getMago());
            System.out.println(controlador.getModelo().getBosque().getMonstruoJefe());
            
            controlador.ComenzarCombate(); // Iniciar el combate

        } catch(HibernateException e) {
            System.out.println("Hibernate ha dado un error: " + e.getMessage());
        }
    }
}
