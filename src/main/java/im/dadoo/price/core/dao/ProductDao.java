/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Product;
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
public class ProductDao extends BaseDao<Product>{

  private static final String SAVE_SQL = 
          "INSERT INTO t_product(category_brand_id, name, thumbnail_path) "
          + "VALUES(:category_brand_id, :name, :thumbnail_path)";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, category_brand_id, name, thumbnail_path FROM t_product WHERE id=:id LIMIT 1";
  
  private static final String FIND_BY_NAME_SQL = 
          "SELECT id, category_brand_id, name, thumbnail_path FROM t_product WHERE name=:name LIMIT 1";
  
  private static final String LIST_SQL = "SELECT id, category_brand_id, name, thumbnail_path "
          + "FROM t_product";
  
  private static final String LIST_LIMIT_SQL = 
          "SELECT id, category_brand_id, name, thumbnail_path FROM t_product "
          + "LIMIT :pagecount, :pagesize";
  
  private static final String LIST_BY_CATEGORY_AND_BRAND_ID_SQL = 
          "SELECT t_product.id AS id, t_product.category_brand_id AS category_brand_id, "
          + "t_product.name AS name, t_product.thumbnail_path AS thumbnail_path "
          + "FROM t_product LEFT OUTER JOIN t_category_brand "
          + "ON t_product.category_brand_id = t_category_brand.id "
          + "WHERE t_category_brand.category_id = :category_id "
          + "AND t_category_brand.brand_id = :brand_id";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_product";
 
  private final RowMapper<Product> baseRowMapper;
  
  public ProductDao() {
    super(Product.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public Product save(Product obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("category_brand_id", obj.getCategoryBrandId());
    sps.addValue("name", obj.getName());
    sps.addValue("thumbnail_path", obj.getThumbnailPath());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  @Override
  public Product findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    List<Product> products = this.jdbcTemplate.query(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    if (products != null && !products.isEmpty()) {
      return products.get(0);
    } else {
      return null;
    }
  }
  
  public Product findByName(String name) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("name", name);
    List<Product> products = this.jdbcTemplate.query(FIND_BY_NAME_SQL, sps, this.baseRowMapper);
    if (products != null && !products.isEmpty()) {
      return products.get(0);
    } else {
      return null;
    }
  }
  
  @Override
  public List<Product> list() {
    List<Product> products = this.jdbcTemplate.query(LIST_SQL, this.baseRowMapper);
    return products;
  }

  @Override
  public List<Product> list(Integer pagecount, Integer pagesize) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("pagecount", pagecount);
    sps.addValue("pagesize", pagesize);
    List<Product> products = this.jdbcTemplate.query(LIST_LIMIT_SQL, sps, this.baseRowMapper);
    return products;
  }

  public List<Product> listByCategoryAndBrandId(Integer categoryId, Integer brandId) {
    if (categoryId != null && brandId != null) {
      MapSqlParameterSource sps = new MapSqlParameterSource();
      sps.addValue("category_id", categoryId);
      sps.addValue("brand_id", brandId);
      return this.jdbcTemplate.query(LIST_BY_CATEGORY_AND_BRAND_ID_SQL, sps, this.baseRowMapper);
    }
    return null;
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
  
  private class BaseRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
      Product product = new Product();
      product.setId(rs.getInt("id"));
      product.setCategoryBrandId((Integer)rs.getObject("category_brand_id"));
      product.setName(rs.getString("name"));
      product.setThumbnailPath(rs.getString("thumbnail_path"));
      return product;
    }
  }
}
