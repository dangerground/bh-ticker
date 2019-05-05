package de.bhclub.ticker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
  //      DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@SpringBootApplication
public class TickerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TickerApplication.class, args);
    }
//
//    @Bean
//    @ConfigurationProperties("ticker.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
}
