/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.FullRecordDao;
import im.dadoo.price.core.dao.LinkDao;
import im.dadoo.price.core.dao.ProductDao;
import im.dadoo.price.core.dao.RecordDao;
import im.dadoo.price.core.dao.SellerDao;
import im.dadoo.price.core.domain.FullRecord;
import im.dadoo.price.core.domain.Link;
import im.dadoo.price.core.domain.Record;
import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author codekitten
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RecordService {
  
  @Resource
  private RecordDao recordDao;
  
  @Resource
  private FullRecordDao fullRecordDao;
  
  @Resource
  private LinkDao linkDao;
  
  @Resource
  private SellerDao sellerDao;
  
  @Resource
  private ProductDao productDao;
  
  public Record save(Link link, Double price, Integer stock, String promotion) {
    Record record = null;
    if (link != null) {
      Record prev = this.recordDao.findLatestByLinkId(link.getId());
      //首先操作t_record表
      if (prev != null && ObjectUtils.equals(prev.getPrice(), price) 
              && ObjectUtils.equals(prev.getStock(), stock)
              && ObjectUtils.equals(prev.getPromotion(), promotion)) {
        record = prev;
        this.recordDao.updateDatetime(record.getId(), System.currentTimeMillis());
      } else {
        record = Record.create(price, stock, promotion, link.getId(), System.currentTimeMillis());
        this.recordDao.save(record);
        System.out.println("新增" + record.toString());
      }
      //然后操作t_full_record表
      FullRecord fr = this.fullRecordDao.findById(link.getId());
      String sellerName = this.sellerDao.findById(link.getSellerId()).getName();
      String productName = this.productDao.findById(link.getProductId()).getName();
    
      if (fr != null) {
        fr.setDatetime(System.currentTimeMillis());
        fr.setPrice(price);
        fr.setStock(stock);
        fr.setAmount(link.getAmount());
        fr.setPromotion(promotion);
        fr.setSellerName(sellerName);
        fr.setProductName(productName);
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
        fr = FullRecord.create(link.getId(), sellerName, productName, link.getAmount(), 
                link.getUrl(), price, stock, price, price, link.getRemark(), 
                promotion, System.currentTimeMillis());
        this.fullRecordDao.save(fr);
      }
    }
    return record;
  }
  
  public void updateDatetime(Integer id, Long datetime) {
    this.recordDao.updateDatetime(id, datetime);
  }
  
  public Record findLatestByLinkId(Integer linkId) {
    return this.recordDao.findLatestByLinkId(linkId);
  }
  
  
}
