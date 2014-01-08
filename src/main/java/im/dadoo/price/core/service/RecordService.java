/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.LinkDao;
import im.dadoo.price.core.dao.RecordDao;
import im.dadoo.price.core.domain.Link;
import im.dadoo.price.core.domain.Product;
import im.dadoo.price.core.domain.Record;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zyq
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RecordService {
  
  @Autowired
  private RecordDao recordDao;
  
  @Autowired
  private LinkDao linkDao;
  
  public Record save(Link link, Double price, Integer stock) {
    Record record = null;
    if (link != null) {
      Record prev = this.recordDao.findLatestByLink(link);
      if (prev != null && ObjectUtils.equals(prev.getPrice(), price) 
              && ObjectUtils.equals(prev.getStock(), stock)) {
        record = prev;
        record.setDatetime(System.currentTimeMillis());
        this.recordDao.update(record);
      } else {
        record = Record.create(price, stock, link, System.currentTimeMillis());
        this.recordDao.save(record);
      }
    }
    return record;
  }
  
  public Record update(Integer id, Integer linkId, Double price, Integer stock) {
    Record record = this.recordDao.findById(id);
    if (record != null) {
      Link link = this.linkDao.findById(linkId);
      record.setLink(link);
      record.setPrice(price);
      record.setStock(stock);
    }
    return record;
  }
  
  public void deleteById(Integer id) {
    this.recordDao.deleteById(id);
  }
  
  public Record findById(Integer id) {
    return this.recordDao.findById(id);
  }
  
  public List<Record> list() {
    return this.recordDao.list();
  }
  
  public List<Record> listByLink(Link link) {
    return this.recordDao.listByLink(link);
  }
  
  public Record findLatestByLink(Link link) {
    return this.recordDao.findLatestByLink(link);
  }
  
  public List<Record> listLatestByProductPerLink(Product product) {
    List<Record> records = null;
    if (product != null) {
      List<Link> links = this.linkDao.listByProduct(product);
      if (links != null && !links.isEmpty()) {
        records = new ArrayList<>(links.size());
        for (Link link : links) {
          Record record = this.recordDao.findLatestByLink(link);
          records.add(record);
        }
      }
    }
    return records;
  }
}
