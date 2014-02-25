/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.Record;
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
public class RecordDao extends BaseDao<Record>{

  private static final String SAVE_SQL = 
          "INSERT INTO t_record(price, stock, promotion, link_id, datetime) "
          + "VALUES(:price, :stock, :promotion, :link_id, :datetime)";
  
  private static final String FIND_BY_ID_SQL = 
          "SELECT id, price, stock, promotion, link_id, datetime FROM t_record where id=:id";
  
  private static final String FIND_LATEST_BY_LINK_ID_SQL = 
          "SELECT id, price, stock, promotion, link_id, datetime FROM t_record "
          + "where link_id=:link_id ORDER BY datetime DESC limit 1";
  
  private static final String UPDATE_DATETIME_SQL = 
          "UPDATE t_record SET datetime=:datetime WHERE id=:id";
  
  private static final String SIZE_SQL = "SELECT count(*) AS size FROM t_record";
 
  private final RowMapper<Record> baseRowMapper;
  
  public RecordDao() {
    super(Record.class);
    this.baseRowMapper = new BaseRowMapper();
  }

  @Override
  public Record save(Record obj) {
    KeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("price", obj.getPrice());
    sps.addValue("stock", obj.getStock());
    sps.addValue("promotion", obj.getPromotion());
    sps.addValue("link_id", obj.getLinkId());
    sps.addValue("datetime", obj.getDatetime());
    this.jdbcTemplate.update(SAVE_SQL, sps, holder);
    obj.setId(holder.getKey().intValue());
    return obj;
  }

  public void updateDatetime(Integer id, Long datetime) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    sps.addValue("datetime", datetime);
    this.jdbcTemplate.update(UPDATE_DATETIME_SQL, sps);
  }
  
  @Override
  public Record findById(Serializable id) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("id", id);
    Record record = this.jdbcTemplate.queryForObject(FIND_BY_ID_SQL, sps, this.baseRowMapper);
    return record;
  }

  public Record findLatestByLinkId(Integer linkId) {
    MapSqlParameterSource sps = new MapSqlParameterSource();
    sps.addValue("link_id", linkId);
    Record record = this.jdbcTemplate.queryForObject(FIND_LATEST_BY_LINK_ID_SQL, sps, this.baseRowMapper);
    return record;
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
  
  private class BaseRowMapper implements RowMapper<Record> {
    @Override
    public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
      Record record = new Record();
      record.setId(rs.getInt("id"));
      record.setPrice((Double)rs.getObject("price"));
      record.setStock((Integer)rs.getObject("stock"));
      record.setPromotion(rs.getString("promotion"));
      record.setLinkId((Integer)rs.getObject("link_id"));
      record.setDatetime((Long)rs.getObject("datetime"));
      return record;
    }
  }
}
