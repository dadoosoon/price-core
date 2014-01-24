/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.LinkDao;
import im.dadoo.price.core.dao.ProductDao;
import im.dadoo.price.core.dao.SellerDao;
import im.dadoo.price.core.domain.Link;
import im.dadoo.price.core.domain.Product;
import im.dadoo.price.core.domain.Seller;
import java.util.List;
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
public class LinkService {
  
  @Autowired
  private LinkDao linkDao;
  
  @Autowired
  private SellerDao sellerDao;
  
  @Autowired
  private ProductDao productDao;
  
  public Link save(String url, Integer amount, Integer sellerId, Integer productId) {
    Seller seller = this.sellerDao.findById(sellerId);
    Product product = this.productDao.findById(productId);
    
    Link link = Link.create(url, amount, seller, product);
    this.linkDao.save(link);
    return link;
  }
  
  public Link update(Integer id, String url, Integer amount, 
          Integer sellerId, Integer productId) {
    Link link = this.linkDao.findById(id);
    
    Seller seller = this.sellerDao.findById(sellerId);
    Product product = this.productDao.findById(productId);
    
    link.setUrl(url);
    link.setAmount(amount);
    link.setSeller(seller);
    link.setProduct(product);
    return link;
  }
  
  public void deleteById(Integer id) {
    this.linkDao.deleteById(id);
  }
  
  public Link findById(Integer id) {
    return this.linkDao.findById(id);
  }
  
  public List<Link> list() {
    return this.linkDao.list();
  }
  
  public List<Link> listBySeller(Integer sellerId) {
    Seller seller = this.sellerDao.findById(sellerId);
    return this.linkDao.listBySeller(seller);
  }
  
  public List<Link> listByProduct(Integer productId) {
    Product product = this.productDao.findById(productId);
    return this.linkDao.listByProduct(product);
  }
  
  public Link findByUrl(String url) {
    return this.linkDao.findByUrl(url);
  }
  
}
