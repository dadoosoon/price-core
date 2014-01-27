/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.LatestRecordDao;
import im.dadoo.price.core.dao.LinkDao;
import im.dadoo.price.core.domain.LatestRecord;
import im.dadoo.price.core.domain.Link;
import im.dadoo.price.core.domain.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author codekitten
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LatestRecordService {
  
  @Autowired
  private LatestRecordDao latestRecordDao;
  
  @Autowired
  private LinkDao linkDao;
  
  public LatestRecord findByLink(Link link) {
    return this.latestRecordDao.findByLink(link);
  }
  
  public List<LatestRecord> listByProduct(Product product) {
    List<Link> links = this.linkDao.listByProduct(product);
    List<LatestRecord> records = new ArrayList<>(links.size());
    for (Link link : links) {
      LatestRecord record = this.latestRecordDao.findByLink(link);
      if (record != null) {
        records.add(record);
      }
    }
    return records;
  }
  
  public Map<Integer, List<LatestRecord>> mapByProduct(List<Product> products) {
    Map<Integer, List<LatestRecord>> map = new HashMap<>();
    for (Product product : products) {
      List<Link> links = this.linkDao.listByProduct(product);
      List<LatestRecord> records = this.latestRecordDao.listInLinks(links);
      map.put(product.getId(), records);
    }
    return map;
  }
}
