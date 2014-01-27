/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.LatestRecord;
import im.dadoo.price.core.domain.Link;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author codekitten
 */
@Repository
public class LatestRecordDao extends BaseDao<LatestRecord>{
  
  public LatestRecordDao() {
    super(LatestRecord.class);
  }
  
  public LatestRecord findByLink(Link link) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.eq("link", link));
    return (LatestRecord)criteria.uniqueResult();
  }
  
  public List<LatestRecord> listInLinks(List<Link> links) {
    Session session = this.sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(this.c);
    criteria.add(Restrictions.in("link", links));
    return (List<LatestRecord>)criteria.list();
  }
}
