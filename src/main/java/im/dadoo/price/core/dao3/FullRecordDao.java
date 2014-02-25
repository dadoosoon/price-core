/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.dao3;

import im.dadoo.price.core.domain3.FullRecord;
import org.springframework.stereotype.Repository;

/**
 *
 * @author codekitten
 */
@Repository("fullRecordDao3")
public class FullRecordDao extends BaseDao<FullRecord>{
  
  public FullRecordDao() {
    super(FullRecord.class);
  }
  
}
