/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.price.core.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author codekitten
 */
@Configuration
@ComponentScan("im.dadoo.price.core")
public class Application {
  
  @Bean(initMethod = "init", destroyMethod = "close")
  public DataSource dataSource() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUrl("jdbc:mysql://db.dadoo.im:33066/dadooprice?characterEncoding=utf8&autoReconnect=true");
    dataSource.setUsername("root");
    dataSource.setPassword("dadoo2012dadoo");
    return dataSource;
  }
  
  @Bean
  public NamedParameterJdbcTemplate jdbcTemplate() {
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource());
    return jdbcTemplate;
  }
}
