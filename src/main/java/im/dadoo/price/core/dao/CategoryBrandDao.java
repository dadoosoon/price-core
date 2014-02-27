/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.CategoryBrand;
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
public class CategoryBrandDao extends BaseDao<CategoryBrand>{

  private static final String SAVE_SQL = 
          "INSERT INTO t_category_brand(category_id, brand_id) VALUES(:category_id, :brand_id)";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, category_id, brand_id FROM t_category_brand WHERE id=:id LIMIT 1";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_category_brand";
 
  private final RowMapper<CategoryBrand> baseRowMapper;
  
  public CategoryBrandDao() {
    super(CategoryBrand.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public CategoryBrand save(CategoryBrand obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("category_id", obj.getCategoryId());
    sps.addValue("brand_id", obj.getBrandId());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  @Override
  public CategoryBrand findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    List<CategoryBrand> cbs = this.jdbcTemplate.query(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    if (cbs != null && !cbs.isEmpty()) {
      return cbs.get(0);
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
  
  private class BaseRowMapper implements RowMapper<CategoryBrand> {
    @Override
    public CategoryBrand mapRow(ResultSet rs, int rowNum) throws SQLException {
      CategoryBrand cb = new CategoryBrand();
      cb.setId(rs.getInt("id"));
      cb.setCategoryId((Integer)rs.getObject("category_id"));
      cb.setBrandId((Integer)rs.getObject("brand_id"));
      return cb; 
    }
  }
}
