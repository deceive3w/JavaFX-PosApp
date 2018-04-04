/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapp;

import javax.imageio.spi.ServiceRegistry;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Deceive
 */
public class HibernateUtil {
 	private static final EntityManagerFactory emFactory;
	static {
		emFactory = Persistence.createEntityManagerFactory("com.concretepage");
	}
	public static CriteriaBuilder getCriteriaBuilder(){
		CriteriaBuilder builder = emFactory.getCriteriaBuilder();
		return  builder;
	}
	public static EntityManager getEntityManager(){
		return emFactory.createEntityManager();
	}
        private static SessionFactory sessionFactory;
        
        public void Init(){
        }
} 