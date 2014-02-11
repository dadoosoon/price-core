/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.FullRecordDao;
import im.dadoo.price.core.dao.LinkDao;
import im.dadoo.price.core.domain.FullRecord;
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
public class FullRecordService {
  
  @Autowired
  private FullRecordDao fullRecordDao;
  
  @Autowired
  private LinkDao linkDao;
  
  public FullRecord findById(Integer id) {
    return this.fullRecordDao.findById(id);
  }
  
  public List<FullRecord> list() {
    return this.fullRecordDao.list();
  }
  
  public Map<Integer, List<FullRecord>> mapByProducts(List<Product> products) {
    Map<Integer, List<FullRecord>> result = new HashMap<>();
    if (products != null && !products.isEmpty()) {
      //初始化cache
      Map<String, List<FullRecord>> cache = new HashMap<>();
      for (Product product : products) {
        cache.put(product.getName(), new ArrayList<FullRecord>());
      }
      //将records放入到cache中
      List<FullRecord> records = this.fullRecordDao.list();
      for (FullRecord record : records) {
        if (cache.containsKey(record.getProductName())) {
          cache.get(record.getProductName()).add(record);
        }
      }
      //提取最终result
      for (Product product : products) {
        result.put(product.getId(), cache.get(product.getName()));
      }
    }
    return result;
  }
}
