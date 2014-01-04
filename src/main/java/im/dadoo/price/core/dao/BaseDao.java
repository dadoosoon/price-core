/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author zyq
 */
public class BaseDao<T> {
  
  protected Class<T> c;
        
  @Autowired
  protected SessionFactory sessionFactory;
  
  public BaseDao(Class<T> c) {
    this.c = c;
  }
  
  public Serializable save(T obj) {
    Session session = this.sessionFactory.getCurrentSession();
    return session.save(obj);
  }
  
  public void update(T obj) {
    Session session = this.sessionFactory.getCurrentSession();
    session.update(obj);
  }
  
  public T findById(Serializable id) {
    Session session = this.sessionFactory.getCurrentSession();
    return (T)session.get(c, id);
  }
  
  public void delete(T obj) {
    Session session = this.sessionFactory.getCurrentSession();
    session.delete(obj);
  }
  
  public void deleteById(Serializable id) {
    this.delete(this.findById(id));
  }
  
  public List<T> list() {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    return (List<T>)criteria.list();
  }
  
  public List<T> list(Integer pagecount, Integer pagesize) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.setFirstResult((pagecount - 1) * pagesize);
    criteria.setMaxResults(pagesize);
    return (List<T>)criteria.list();
  }
  
  public Serializable size() {
    String hql = "select count(*) from " + this.c.getName();
    return (Serializable)this.sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
  }
}
