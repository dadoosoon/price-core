/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Link;
import im.dadoo.price.core.domain.Record;
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
public class RecordDao extends BaseDao<Record> {
  
  public RecordDao() {
    super(Record.class);
  }
  
  public List<Record> listByLink(Link link) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("link", link));
    return (List<Record>)criteria.list();
  }
  
  public Record findLatestByLink(Link link) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("link", link));
    criteria.addOrder(Order.desc("id"));
    criteria.setMaxResults(1);
    return (Record)criteria.uniqueResult();
  }
  
}
