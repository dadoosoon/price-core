/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Link;
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
public class LinkDao extends BaseDao<Link>{

  private static final String SAVE_SQL = 
          "INSERT INTO t_link(amount, url, remark, seller_id, product_id) "
          + "VALUES(:amount, :url, :remark, :seller_id, :product_id)";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, amount, url, remark, seller_id, product_id FROM t_link where id=:id";
  
  private static final String LIST_BY_SELLER_ID_SQL = 
          "SELECT id, amount, url, remark, seller_id, product_id "
          + "FROM t_link where seller_id=:seller_id";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_link";
 
  private final RowMapper<Link> baseRowMapper;
  
  public LinkDao() {
    super(Link.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public Link save(Link obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("amount", obj.getAmount());
    sps.addValue("url", obj.getUrl());
    sps.addValue("remark", obj.getRemark());
    sps.addValue("seller_id", obj.getSellerId());
    sps.addValue("product_id", obj.getProductId());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  @Override
  public Link findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    Link link = this.jdbcTemplate.queryForObject(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    return link;
  }
  
  public List<Link> listBySellerId(Integer sellerId) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("seller_id", sellerId);
    return this.jdbcTemplate.query(LIST_BY_SELLER_ID_SQL, sps, this.baseRowMapper);
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
  
  private class BaseRowMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
      Link link = new Link();
      link.setId(rs.getInt("id"));
      link.setAmount((Integer)rs.getObject("amount"));
      link.setUrl(rs.getString("url"));
      link.setRemark(rs.getString("remark"));
      link.setSellerId((Integer)rs.getObject("seller_id"));
      link.setProductId((Integer)rs.getObject("product_id"));
      return link;
    }
  }
}
