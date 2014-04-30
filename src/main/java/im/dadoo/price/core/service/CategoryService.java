/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.CategoryDao;
import im.dadoo.price.core.domain.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CategoryService {
  
  @Resource
  private CategoryDao categoryDao;
  
  public Category save(String name, Integer supId) {
    Category category = this.categoryDao.findByName(name);
    if (category == null) {
      category = Category.create(name, supId);
      return this.categoryDao.save(category);
    } else {
      return null;
    }
  }
  
  public List<Category> list() {
    return this.categoryDao.list();
  }
  
  public List<Category> listByBrandId(Integer brandId) {
    return this.categoryDao.listByBrandId(brandId);
  }
  
  public Map<Integer, Category> map() {
    List<Category> list = this.categoryDao.list();
    if (list != null) {
      Map<Integer, Category> map = new HashMap<>();
      for (Category category : list) {
        map.put(category.getId(), category);
      }
      return map;
    } else {
      return null;
    }
  }
}
