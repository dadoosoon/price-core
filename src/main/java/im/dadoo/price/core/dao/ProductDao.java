/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.CategoryBrand;
import im.dadoo.price.core.domain.Product;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zyq
 */
@Repository
public class ProductDao extends BaseDao<Product> {
  
  public ProductDao() {
    super(Product.class);
  }
  
  public Product findByName(String name) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("name", name));
    return (Product)criteria.uniqueResult();
  }
  
  public List<Product> listByCategoryBrand(CategoryBrand categoryBrand) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("categoryBrand", categoryBrand))
            .addOrder(Order.asc("name"));
    return (List<Product>)criteria.list();
  }
}
