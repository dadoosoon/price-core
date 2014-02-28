/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Brand;
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
public class BrandDao extends BaseDao<Brand> {
  
  private static final String SAVE_SQL = 
          "INSERT INTO t_brand(name, info) VALUES(:name, :info)";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, name, info FROM t_brand WHERE id=:id LIMIT 1";
  
  private static final String FIND_BY_NAME_SQL = 
          "SELECT id, name, info FROM t_brand WHERE name=:name LIMIT 1";
  
  private static final String LIST_SQL = "SELECT id, name, info FROM t_brand ORDER BY name ASC";
  
  private static final String LIST_LIMIT_SQL = 
          "SELECT id, name, info FROM t_brand ORDER BY name ASC LIMIT :pagecount, :pagesize";
  
  private static final String LIST_BY_CATEGORY_ID_SQL = 
          "SELECT t_brand.id AS id, t_brand.name AS name, t_brand.info AS info FROM t_brand "
          + "LEFT OUTER JOIN t_category_brand ON t_brand.id = t_category_brand.brand_id "
          + "WHERE t_category_brand.category_id = :category_id "
          + "ORDER BY name ASC";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_brand";
  
  private final RowMapper<Brand> baseRowMapper;
  
  public BrandDao() {
    super(Brand.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public Brand save(Brand obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("name", obj.getName());
    sps.addValue("info", obj.getInfo());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  @Override
  public Brand update(Brand obj) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void delete(Serializable id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Brand findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    List<Brand> brands = this.jdbcTemplate.query(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    if (brands != null && !brands.isEmpty()) {
      return brands.get(0);
    } else {
      return null;
    }
  }
  
  public Brand findByName(String name) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("name", name);
    List<Brand> brands = this.jdbcTemplate.query(FIND_BY_NAME_SQL, sps, this.baseRowMapper);
    if (!brands.isEmpty()) {
      return brands.get(0);
    } else {
      return null;
    }
  }
  
  @Override
  public List<Brand> list() {
    List<Brand> brands = this.jdbcTemplate.query(LIST_SQL, this.baseRowMapper);
    return brands;
  }

  @Override
  public List<Brand> list(Integer pagecount, Integer pagesize) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("pagecount", pagecount);
    sps.addValue("pagesize", pagesize);
    List<Brand> brands = this.jdbcTemplate.query(LIST_LIMIT_SQL, sps, this.baseRowMapper);
    return brands;
  }

  public List<Brand> listByCategoryId(Integer categoryId) {
    if (categoryId != null) {
      MapSqlParameterSource sps = new MapSqlParameterSource();
      sps.addValue("category_id", categoryId);
      List<Brand> brands = this.jdbcTemplate.query(LIST_BY_CATEGORY_ID_SQL, sps, this.baseRowMapper);
      return brands;
    } else {
      return null;
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
  
  private class BaseRowMapper implements RowMapper<Brand> {
    @Override
    public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
      Brand brand = new Brand();
      brand.setId(rs.getInt("id"));
      brand.setName(rs.getString("name"));
      brand.setInfo(rs.getString("info"));
      return brand;
    }
  }
}
