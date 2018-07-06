package com.bid.smc.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "bidsenseEntityManagerFactory",
    transactionManagerRef = "bidsenseTransactionManager", basePackages = {"com.bid.smc.repo.bidsense"})
public class BidSenseDbConfig {

  @Bean(name = "bidSenseDataSource")
  @ConfigurationProperties(prefix = "bidsense.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "bidsenseEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("bidSenseDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).packages("com.bid.smc.model.bidsense").persistenceUnit("bidsense")
        .build();
  }

  @Bean(name = "bidsenseTransactionManager")
  public PlatformTransactionManager barTransactionManager(
      @Qualifier("bidsenseEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
    return new JpaTransactionManager(barEntityManagerFactory);
  }

}
