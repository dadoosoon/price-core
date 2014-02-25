/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author codekitten
 * @param <T>
 */
public class BaseDao<T> {
  
  
  @Resource
  protected NamedParameterJdbcTemplate jdbcTemplate;
  
  protected Class<T> c;
  
  public BaseDao(Class<T> c) {
    this.c = c;
  }

  public T save(T obj) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public T update(T obj) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void delete(Serializable id) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public T findById(Serializable id) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public List<T> list() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public List<T> list(Integer pagecount, Integer pagesize) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public Serializable size() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
