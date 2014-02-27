/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.CategoryDao;
import im.dadoo.price.core.domain.Category;
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
}
