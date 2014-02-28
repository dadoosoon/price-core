/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Category;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author codekitten
 */
@Repository
public class CategoryDao extends BaseDao<Category>{

  private static final String SAVE_SQL = 
          "INSERT INTO t_category(name, sup_id) VALUES(:name, :sup_id)";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, name, sup_id FROM t_category WHERE id=:id LIMIT 1";
  
  private static final String FIND_BY_NAME_SQL = 
          "SELECT id, name, sup_id FROM t_category WHERE name=:name LIMIT 1";
  
  private static final String LIST_SQL = "SELECT id, name, sup_id FROM t_category ORDER BY name ASC";
  
  private static final String LIST_LIMIT_SQL = 
          "SELECT id, name, sup_id FROM t_category ORDER BY name ASC LIMIT :pagecount, :pagesize";
  
  private static final String LIST_BY_SUP_ID_SQL = 
          "SELECT id, name, sup_id FROM t_category WHERE sup_id = :sup_id ORDER BY name ASC";
  
  private static final String LIST_BY_SUP_ID_IS_NULL_SQL = 
          "SELECT id, name, sup_id FROM t_category WHERE sup_id IS NULL ORDER BY name ASC";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_category";
 
  private final RowMapper<Category> baseRowMapper;
  
  public CategoryDao() {
    super(Category.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public Category save(Category obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("name", obj.getName());
    sps.addValue("sup_id", obj.getSupId());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  @Override
  public Category update(Category obj) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void delete(Serializable id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Category findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    List<Category> categories = this.jdbcTemplate.query(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    if (categories != null && !categories.isEmpty()) {
      return categories.get(0);
    } else {
      return null;
    }
  }
  
  public Category findByName(String name) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("name", name);
    List<Category> categories = this.jdbcTemplate.query(FIND_BY_NAME_SQL, sps, this.baseRowMapper);
    if (!categories.isEmpty()) {
      return categories.get(0);
    } else {
      return null;
    }
  }
  
  @Override
  public List<Category> list() {
    List<Category> categories = this.jdbcTemplate.query(LIST_SQL, this.baseRowMapper);
    return categories;
  }

  @Override
  public List<Category> list(Integer pagecount, Integer pagesize) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("pagecount", pagecount);
    sps.addValue("pagesize", pagesize);
    List<Category> categories = this.jdbcTemplate.query(LIST_LIMIT_SQL, sps, this.baseRowMapper);
    return categories;
  }

  public List<Category> listBySupId(Integer supId) {
    if (supId != null) {
      MapSqlParameterSource sps = new MapSqlParameterSource();
      sps.addValue("sup_id", supId);
      return this.jdbcTemplate.query(LIST_BY_SUP_ID_SQL, sps, this.baseRowMapper);
    } else {
      return this.jdbcTemplate.query(LIST_BY_SUP_ID_IS_NULL_SQL, this.baseRowMapper);
    }
  }
  
  @Override
  public Serializable size() {
    return (Serializable)this.jdbcTemplate.queryForObject(SIZE_SQL, 
            (SqlParameterSource)null, new RowMapper<Integer>() {
      @Override
      public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("size");
      }
    });
  }
  
  private class BaseRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
      Category category = new Category();
      category.setId(rs.getInt("id"));
      category.setName(rs.getString("name"));
      category.setSupId((Integer)rs.getObject("sup_id"));
      return category;
    }
  }
}
