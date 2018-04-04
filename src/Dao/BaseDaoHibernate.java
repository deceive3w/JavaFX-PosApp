/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Deceive
 */
public class BaseDaoHibernate<T> {
    @SuppressWarnings("unchecked")
    protected Class domainClass;
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    public BaseDaoHibernate() {
        this.domainClass = (Class) ((ParameterizedType)
        getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
    }
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
    return sessionFactory.getCurrentSession().createQuery("from " +
    domainClass.getName())
    .list();
    }

}
