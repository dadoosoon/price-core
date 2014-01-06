/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.BrandDao;
import im.dadoo.price.core.dao.CategoryBrandDao;
import im.dadoo.price.core.domain.Brand;
import im.dadoo.price.core.domain.Category;
import im.dadoo.price.core.domain.CategoryBrand;
import java.util.ArrayList;
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
public class BrandService {
  
  @Autowired
  private BrandDao brandDao;
  
  @Autowired
  private CategoryBrandDao cbDao;
  
  public Brand save(String name, String info) {
    Brand brand = Brand.create(name, info);
    this.brandDao.save(brand);
    return brand;
  }
  
  public Brand update(Integer id, String name, String info) {
    Brand brand = this.brandDao.findById(id);
    brand.setName(name);
    brand.setInfo(info);
    this.brandDao.update(brand);
    return brand;
  }
  
  public void deleteById(Integer id) {
    this.brandDao.deleteById(id);
  }
  
  public Brand findById(Integer id) {
    return this.brandDao.findById(id);
  }
  
  public Brand findByName(String name) {
    return this.brandDao.findByName(name);
  }
  
  public List<Brand> list() {
    return this.brandDao.list();
  }
  
  public List<Brand> listByCategory(Category category) {
    List<Brand> brands = null;
    List<CategoryBrand> cbs = this.cbDao.listByCategory(category);
    if (cbs != null && !cbs.isEmpty()) {
      brands = new ArrayList<>(cbs.size());
      for (CategoryBrand cb : cbs) {
        brands.add(cb.getBrand());
      }
    }
    return brands;
  }
}
