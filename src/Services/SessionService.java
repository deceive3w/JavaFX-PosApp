/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Deceive
 */
public class SessionService {
    public static Configuration cfg;
    public static SessionFactory sessionFactory;
    public static SessionService _instance = null;
    public static Session session;
    public static SessionService getInstance(){
        if(_instance == null){
            _instance = new SessionService();
            cfg = new Configuration().configure("hibernate.cfg.xml");
            sessionFactory = cfg.buildSessionFactory();
            session = sessionFactory.openSession();
            return _instance;
        }
        return _instance;
    }
    public Query executeQuery(String query){
        return sessionFactory.openSession().createQuery(query);
  
    }
    public void closeMe(){
        sessionFactory.close();
    }
    public Session getSession(){
        return session;
    }
}
