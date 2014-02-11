/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao;

import im.dadoo.price.core.domain.FullRecord;
import org.springframework.stereotype.Repository;

/**
 *
 * @author codekitten
 */
@Repository
public class FullRecordDao extends BaseDao<FullRecord>{
  
  public FullRecordDao() {
    super(FullRecord.class);
  }
  
}
