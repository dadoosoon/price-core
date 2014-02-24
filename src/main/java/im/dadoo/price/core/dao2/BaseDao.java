/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao2;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author codekitten
 * @param <T>
 */
public abstract class BaseDao<T> {
  
  
  @Resource
  protected NamedParameterJdbcTemplate jdbcTemplate;
  
  protected Class<T> c;
  
  public BaseDao(Class<T> c) {
    this.c = c;
  }

  public abstract T save(T obj);
  
  public abstract T update(T obj);
  
  public abstract void delete(Serializable id);
  
  public abstract T findById(Serializable id);
  
  public abstract List<T> list();
  
  public abstract List<T> list(Integer pagecount, Integer pagesize);
  
  public abstract Serializable size();
}
