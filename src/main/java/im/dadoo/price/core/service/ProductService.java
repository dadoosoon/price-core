/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.ProductDao;
import im.dadoo.price.core.domain.Product;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author codekitten
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProductService {
  
  @Resource
  private ProductDao productDao;
  
  public Product findById(Integer id) {
    return this.productDao.findById(id);
  }
  
  public List<Product> list() {
    return this.productDao.list();
  }
}
