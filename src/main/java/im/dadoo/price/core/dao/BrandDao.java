/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Brand;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zyq
 */
@Repository
public class BrandDao extends BaseDao<Brand> {
  
  public BrandDao() {
    super(Brand.class);
  }
  
  public Brand findByName(String name) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("name", name));
    return (Brand)criteria.uniqueResult();
  }
  
  
}
