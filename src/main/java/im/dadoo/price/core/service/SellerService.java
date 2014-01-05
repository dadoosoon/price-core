/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.SellerDao;
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
public class SellerService {
  
  @Autowired
  private SellerDao sellerDao;
  
  public Seller save(String name, String site) {
    Seller seller = Seller.create(name, site);
    this.sellerDao.save(seller);
    return seller;
  }
  
  public Seller update(Integer id, String name, String site) {
    Seller seller = this.sellerDao.findById(id);
    seller.setName(name);
    seller.setSite(site);
    this.sellerDao.update(seller);
    return seller;
  }
  
  public void deleteById(Integer id) {
    this.sellerDao.deleteById(id);
  }
  
  public Seller findById(Integer id) {
    return this.sellerDao.findById(id);
  }
  
  public Seller findByName(String name) {
    return this.sellerDao.findByName(name);
  }
  
  public Seller findBySite(String site) {
    return this.sellerDao.findBySite(site);
  }
  
  public List<Seller> list() {
    return this.sellerDao.list();
  }
  
  
}
