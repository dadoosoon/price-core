/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.BrandDao;
import im.dadoo.price.core.dao.CategoryBrandDao;
import im.dadoo.price.core.dao.CategoryDao;
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
public class CategoryService {
  
  @Autowired
  private CategoryDao categoryDao;
  
  @Autowired
  private CategoryBrandDao cbDao;
  
  @Autowired
  private BrandDao brandDao;
  
  public Category save(String name, Integer supId) {
    Category sup = this.categoryDao.findById(supId);
    Category category = Category.create(name, sup);
    this.categoryDao.save(category);
    return category;
  }
  
  public Category update(Integer id, String name, Integer supId) {
    Category category = this.categoryDao.findById(id);
    category.setName(name);
    category.setSup(this.categoryDao.findById(supId));
    this.categoryDao.update(category);
    return category;
  }
  
  public void deleteById(Integer id) {
    this.categoryDao.deleteById(id);
  }
  
  public Category findById(Integer id) {
    return this.categoryDao.findById(id);
  }
  
  public Category findByName(String name) {
    return this.categoryDao.findByName(name);
  }
  
  public List<Category> list() {
    return this.categoryDao.list();
  }
  
  public List<Category> listByBrand(Brand brand) { 
    List<CategoryBrand> cbs = this.cbDao.listByBrand(brand);
    List<Category> categories = null;
    if (cbs != null) {
      categories = new ArrayList<>(cbs.size());
      for (CategoryBrand cb : cbs) {
        categories.add(cb.getCategory());
      }
    }
    return categories;
  }
  
  public List<Category> listBySup(Category sup) {
    return this.categoryDao.listBySup(sup);
  }
}
