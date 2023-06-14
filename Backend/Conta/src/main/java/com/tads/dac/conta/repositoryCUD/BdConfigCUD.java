
package com.tads.dac.conta.repositoryCUD;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
    basePackages = "com.tads.dac.conta.repositoryCUD", 
    entityManagerFactoryRef = "cudEntityManager", 
    transactionManagerRef = "cudTransactionManager"
)
public class BdConfigCUD {
 
    @Autowired
    private Environment env;
    
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean cudEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = 
                new LocalContainerEntityManagerFactoryBean();
        
        em.setDataSource(cudDataSource());
        em.setPackagesToScan("com.tads.dac.conta.modelCUD");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        
        properties.put("hibernate.dialect",
                env.getProperty("org.hibernate.dialect.PostgreSQL81Dialect"));
        
        properties.put("hibernate.hbm2ddl.auto", "update");
        
        properties.put("spring.jpa.hibernate.ddl-auto", "update");  
        
        properties.put("spring.jpa.show-sql", "true");
                
        em.setJpaPropertyMap(properties);
        
        return em;
    }

    @Bean
    @Primary
    public DataSource cudDataSource() {
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("postgresql://bantads-db:5432/bantads");
        dataSource.setSchema("conta");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager cudTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(cudEntityManager().getObject());
        return transactionManager;
    }  
    
}
