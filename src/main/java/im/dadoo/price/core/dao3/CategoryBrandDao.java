/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao3;

import im.dadoo.price.core.domain3.Brand;
import im.dadoo.price.core.domain3.Category;
import im.dadoo.price.core.domain3.CategoryBrand;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zyq
 */
@Repository("categoryBrandDao3")
public class CategoryBrandDao extends BaseDao<CategoryBrand> {
  
  public CategoryBrandDao() {
    super(CategoryBrand.class);
  }
  
  public List<CategoryBrand> listByCategory(Category category) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("category", category));
    return (List<CategoryBrand>)criteria.list();
  }
  
  public List<CategoryBrand> listByBrand(Brand brand) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("brand", brand));
    return (List<CategoryBrand>)criteria.list();
  }
  
  public CategoryBrand findByCategoryAndBrand(Category category, Brand brand) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("category", category));
    criteria.add(Restrictions.eq("brand", brand));
    return (CategoryBrand)criteria.uniqueResult();
  }
}
