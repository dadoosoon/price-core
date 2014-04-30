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
import javax.annotation.Resource;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author codekitten
 */
@Service
@Transactional
public class CategoryBrandService {
  
  @Resource
  private CategoryDao categoryDao;
  
  @Resource
  private BrandDao brandDao;
  
  @Resource
  private CategoryBrandDao cbDao;
  
  public CategoryBrand save(Integer categoryId, Integer brandId) {
    CategoryBrand cb = CategoryBrand.create(categoryId, brandId);
    return this.cbDao.save(cb);
  }
  
  public CategoryBrand findById(Integer id) {
    return this.cbDao.findById(id);
  }
  
  public ImmutablePair<Category, Brand> findPairById(Integer id) {
    CategoryBrand cb = this.cbDao.findById(id);
    if (cb != null) {
      Category c = this.categoryDao.findById(cb.getCategoryId());
      Brand b = this.brandDao.findById(cb.getBrandId());
      return ImmutablePair.of(c, b);
    } else {
      return null;
    }
  }
}
