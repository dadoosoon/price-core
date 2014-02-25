/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao3;

import im.dadoo.price.core.domain3.Link;
import im.dadoo.price.core.domain3.Product;
import im.dadoo.price.core.domain3.Seller;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zyq
 */
@Repository("linkDao3")
public class LinkDao extends BaseDao<Link> {
  
  public LinkDao() {
    super(Link.class);
  }
  
  public List<Link> listBySeller(Seller seller) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("seller", seller));
    return (List<Link>)criteria.list();
  }
  
  public List<Link> listByProduct(Product product) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("product", product));
    return (List<Link>)criteria.list();
  }
  
  public Link findByUrl(String url) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("url", url));
    return (Link)criteria.uniqueResult();
  }
  
}
