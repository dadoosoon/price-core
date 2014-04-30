/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.service;

import im.dadoo.price.core.dao.BrandDao;
import im.dadoo.price.core.domain.Brand;
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
public class BrandService {
  
  @Resource
  private BrandDao brandDao;
  
  public Brand save(String name, String info) {
    Brand brand = Brand.create(name, info);
    return this.brandDao.save(brand);
  }
  
  public Brand findById(Integer id) {
    return this.brandDao.findById(id);
  }
  
  public List<Brand> list() {
    return this.brandDao.list();
  }
  
  public Map<Integer, Brand> map() {
    List<Brand> brands = this.brandDao.list();
    if (brands != null) {
      Map<Integer, Brand> map = new HashMap<>();
      for (Brand brand : brands) {
        map.put(brand.getId(), brand);
      }
      return map;
    } else {
      return null;
    }
  }
  
  public List<Brand> listByCategoryId(Integer categoryId) {
    return this.brandDao.listByCategoryId(categoryId);
  }
}
