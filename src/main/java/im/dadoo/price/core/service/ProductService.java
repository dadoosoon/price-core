/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.BrandDao;
import im.dadoo.price.core.dao.CategoryBrandDao;
import im.dadoo.price.core.dao.CategoryDao;
import im.dadoo.price.core.dao.ProductDao;
import im.dadoo.price.core.domain.Brand;
import im.dadoo.price.core.domain.Category;
import im.dadoo.price.core.domain.CategoryBrand;
import im.dadoo.price.core.domain.Product;
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
public class ProductService {
  
  @Autowired
  private ProductDao productDao;
  
  @Autowired
  private CategoryBrandDao cbDao;
  
  @Autowired
  private CategoryDao categoryDao;
  
  @Autowired
  private BrandDao brandDao;
  
  public Product save(String name, Integer categoryId, Integer brandId) {
    Product product = null;
    Category category = this.categoryDao.findById(categoryId);
    Brand brand = this.brandDao.findById(brandId);
    CategoryBrand cb = this.cbDao.findByCategoryAndBrand(category, brand);
    if (cb != null) {
      product = Product.create(name, cb);
      this.productDao.save(product);
    }
    return product;
  }
  
  public Product update(Integer id, String name, Integer categoryId, Integer brandId) {
    Product product = this.productDao.findById(id);
    Category category = this.categoryDao.findById(categoryId);
    Brand brand = this.brandDao.findById(brandId);
    CategoryBrand cb = this.cbDao.findByCategoryAndBrand(category, brand);
    if (cb != null) {
      product.setName(name);
      product.setCategoryBrand(cb);
    }
    return product;
  }
  
  public void deleteById(Integer id) {
    this.cbDao.deleteById(id);
  }
  
  public Product findById(Integer id) {
    return this.productDao.findById(id);
  }
  
  public List<Product> list() {
    return this.productDao.list();
  }
  
  public List<Product> listByCategoryAndBrand(Integer categoryId, Integer brandId) {
    Category category = this.categoryDao.findById(categoryId);
    Brand brand = this.brandDao.findById(brandId);
    CategoryBrand cb = this.cbDao.findByCategoryAndBrand(category, brand);
    return this.productDao.listByCategoryBrand(cb);
  }
}
