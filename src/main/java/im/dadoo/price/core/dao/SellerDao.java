/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Seller;
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
public class SellerDao extends BaseDao<Seller>{

  private static final String SAVE_SQL = 
          "INSERT INTO t_seller(name, site) VALUES(:name, :site)";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, name, site, delay FROM t_seller WHERE id=:id LIMIT 1";
  
  private static final String FIND_BY_NAME_SQL = 
          "SELECT id, name, site, delay FROM t_seller WHERE name=:name LIMIT 1";
  
  private static final String LIST_SQL = "SELECT id, name, site, delay FROM t_seller ORDER BY name";
  
  private static final String LIST_LIMIT_SQL = 
          "SELECT id, name, site, delay FROM t_seller "
          + "ORDER BY name LIMIT :pagecount, :pagesize";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_seller";
 
  private final RowMapper<Seller> baseRowMapper;
  
  public SellerDao() {
    super(Seller.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public Seller save(Seller obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("name", obj.getName());
    sps.addValue("site", obj.getSite());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  @Override
  public Seller findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    List<Seller> sellers = this.jdbcTemplate.query(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    if (sellers != null && !sellers.isEmpty()) {
      return sellers.get(0);
    } else {
      return null;
    }
  }
  
  public Seller findByName(String name) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("name", name);
    List<Seller> sellers = this.jdbcTemplate.query(FIND_BY_NAME_SQL, sps, this.baseRowMapper);
    if (sellers != null && !sellers.isEmpty()) {
      return sellers.get(0);
    } else {
      return null;
    }
  }
  
  @Override
  public List<Seller> list() {
    List<Seller> sellers = this.jdbcTemplate.query(LIST_SQL, this.baseRowMapper);
    return sellers;
  }

  @Override
  public List<Seller> list(Integer pagecount, Integer pagesize) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("pagecount", pagecount);
    sps.addValue("pagesize", pagesize);
    List<Seller> sellers = this.jdbcTemplate.query(LIST_LIMIT_SQL, sps, this.baseRowMapper);
    return sellers;
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
  
  private class BaseRowMapper implements RowMapper<Seller> {
    @Override
    public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
      Seller seller = new Seller();
      seller.setId(rs.getInt("id"));
      seller.setName(rs.getString("name"));
      seller.setSite(rs.getString("site"));
      seller.setDelay((Long)rs.getObject("delay"));
      return seller; 
    }
  }
}
