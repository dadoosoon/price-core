/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.comp;

import im.dadoo.price.core.domain3.Record;
import java.util.Comparator;
import org.apache.commons.lang3.ObjectUtils;

/**
 *
 * @author codekitten
 */
public class RecordComparator implements Comparator<Record> {

  @Override
  public int compare(Record o1, Record o2) {
    if (o1 != null) {
      if (o2 != null) {
        return ObjectUtils.compare(o1.getPrice(), o2.getPrice(), true);
      } else {
        return 1;
      }
    } else {
      if (o2 != null) {
        return -1;
      } else {
        return 0;
      }
    }
    
  }

}
