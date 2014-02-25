/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service3;

import im.dadoo.price.core.dao3.FullRecordDao;
import im.dadoo.price.core.dao3.LinkDao;
import im.dadoo.price.core.dao3.RecordDao;
import im.dadoo.price.core.domain3.FullRecord;
import im.dadoo.price.core.domain3.Link;
import im.dadoo.price.core.domain3.Product;
import im.dadoo.price.core.domain3.Record;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zyq
 */
@Service("recordService3")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RecordService {
  
  @Autowired
  private RecordDao recordDao;
  
  @Autowired
  private FullRecordDao fullRecordDao;
  
  @Autowired
  private LinkDao linkDao;
  
  public Record save(Link link, Double price, Integer stock, String promotion) {
    Record record = null;
    if (link != null) {
      Record prev = this.recordDao.findLatestByLink(link);
      if (prev != null && ObjectUtils.equals(prev.getPrice(), price) 
              && ObjectUtils.equals(prev.getStock(), stock)
              && ObjectUtils.equals(prev.getPromotion(), promotion)) {
        record = prev;
        record.setDatetime(System.currentTimeMillis());
        this.recordDao.update(record);
      } else {
        record = Record.create(price, stock, promotion, link, System.currentTimeMillis());
        this.recordDao.save(record);
      }
      
      //记录到t_full_record中
      FullRecord fr = this.fullRecordDao.findById(link.getId());
      if (fr != null) {
        fr.setDatetime(System.currentTimeMillis());
        fr.setPrice(price);
        fr.setStock(stock);
        fr.setAmount(link.getAmount());
        fr.setPromotion(promotion);
        fr.setSellerName(link.getSeller().getName());
        fr.setProductName(link.getProduct().getName());
        fr.setUrl(link.getUrl());
        fr.setRemark(link.getRemark());
        if (ObjectUtils.compare(price, fr.getMaxPrice(), false) > 0) {
          fr.setMaxPrice(price);
        }
        if (ObjectUtils.compare(price, fr.getMinPrice(), true) < 0) {
          fr.setMinPrice(price);
        }
        this.fullRecordDao.update(fr);
      } else {
        fr = FullRecord.create(link.getId(), link.getSeller().getName(), link.getProduct().getName(), 
                link.getAmount(), link.getUrl(), price, stock, price, price, link.getRemark(), promotion, 
                System.currentTimeMillis());
        this.fullRecordDao.save(fr);
      }
    }
    return record;
  }
  
  public Record update(Integer id, Integer linkId, Double price, Integer stock, String promotion) {
    Record record = this.recordDao.findById(id);
    if (record != null) {
      Link link = this.linkDao.findById(linkId);
      record.setLink(link);
      record.setPrice(price);
      record.setStock(stock);
      record.setPromotion(promotion);
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
