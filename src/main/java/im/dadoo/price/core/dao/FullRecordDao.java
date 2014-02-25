/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.FullRecord;
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
public class FullRecordDao extends BaseDao<FullRecord>{

  private static final String SAVE_SQL = 
          "INSERT INTO t_full_record(id, seller_name, product_name, amount, url, price, stock, "
          + "max_price, min_price, remark, promotion, datetime) "
          + "VALUES(:id, :seller_name, :product_name, :amount, :url, :price, :stock, "
          + ":max_price, :min_price, :remark, :promotion, :datetime)";
  
  private static final String UPDATE_SQL = 
          "UPDATE t_full_record SET seller_name=:seller_name, product_name=:product_name,"
          + "amount=:amount, url=:url, price=:price, stock=:stock, max_price=:max_price, "
          + "min_price=:min_price, remark=:remark, promotion=:promotion, datetime=:datetime"
          + "WHERE id=:id";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, seller_name, product_name, amount, url, price, stock, "
          + "max_price, min_price, remark, promotion, datetime "
          + "FROM t_full_record where id=:id";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_full_record";
 
  private final RowMapper<FullRecord> baseRowMapper;
  
  public FullRecordDao() {
    super(FullRecord.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public FullRecord save(FullRecord obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", obj.getId());
    sps.addValue("seller_name", obj.getSellerName());
    sps.addValue("product_name", obj.getProductName());
    sps.addValue("amount", obj.getAmount());
    sps.addValue("url", obj.getUrl());
    sps.addValue("price", obj.getPrice());
    sps.addValue("stock", obj.getStock());
    sps.addValue("max_price", obj.getMaxPrice());
    sps.addValue("min_price", obj.getMinPrice());
    sps.addValue("remark", obj.getRemark());
    sps.addValue("promotion", obj.getPromotion());
    sps.addValue("datetime", obj.getDatetime());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  @Override
  public FullRecord update(FullRecord obj) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", obj.getId());
    sps.addValue("seller_name", obj.getSellerName());
    sps.addValue("product_name", obj.getProductName());
    sps.addValue("amount", obj.getAmount());
    sps.addValue("url", obj.getUrl());
    sps.addValue("price", obj.getPrice());
    sps.addValue("stock", obj.getStock());
    sps.addValue("max_price", obj.getMaxPrice());
    sps.addValue("min_price", obj.getMinPrice());
    sps.addValue("remark", obj.getRemark());
    sps.addValue("promotion", obj.getPromotion());
    sps.addValue("datetime", obj.getDatetime());
    this.jdbcTemplate.update(UPDATE_SQL, sps);
    return obj;
  }
  
  @Override
  public FullRecord findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    FullRecord fr = this.jdbcTemplate.queryForObject(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    return fr;
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
  
  private class BaseRowMapper implements RowMapper<FullRecord> {
    @Override
    public FullRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
      FullRecord fr = new FullRecord();
      fr.setId(rs.getInt("id"));
      fr.setSellerName(rs.getString("seller_name"));
      fr.setProductName(rs.getString("product_name"));
      fr.setAmount((Integer)rs.getObject("amount"));
      fr.setUrl(rs.getString("url"));
      fr.setPrice((Double)rs.getObject("price"));
      fr.setStock((Integer)rs.getObject("stock"));
      fr.setMaxPrice((Double)rs.getObject("max_price"));
      fr.setMinPrice((Double)rs.getObject("min_price"));
      fr.setRemark(rs.getString("remark"));
      fr.setPromotion(rs.getString("promotion"));
      fr.setDatetime((Long)rs.getObject("datetime"));
      return fr;
    }
  }
}
