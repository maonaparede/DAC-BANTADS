
package com.tads.dac.conta.repositoryR;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
    basePackages = "com.tads.dac.conta.repositoryR", 
    entityManagerFactoryRef = "readEntityManager", 
    transactionManagerRef = "readTransactionManager"
)
public class BdConfigR {
    
    @Autowired
    private Environment env;
    
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean readEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(readDataSource());
        em.setPackagesToScan("com.tads.dac.conta.modelR");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL81Dialect");
        
        properties.put("hibernate.hbm2ddl.auto", "update");
        
        properties.put("spring.jpa.hibernate.ddl-auto", "update");  
        
        properties.put("spring.jpa.show-sql", "true");
        
        em.setJpaPropertyMap(properties);
        
        return em;
    }

    @Bean
    @Primary
    public DataSource readDataSource() {
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://bantads-db:5432/postgres");
        dataSource.setSchema("contar");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager readTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(readEntityManager().getObject());
        return transactionManager;
    }    
}
