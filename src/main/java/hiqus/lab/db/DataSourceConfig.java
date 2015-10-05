package hiqus.lab.db;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by jh3389 on 10/4/15.
 */
@Configuration
@ComponentScan(basePackages = {"hiqus.lab"})
@PropertySource({
        "classpath:/application.properties",
        "classpath:/datasource.primary.properties",
        "classpath:/datasource.secondary.properties"})
public class DataSourceConfig {

    @Autowired
    Environment env;

    @Bean
    @Primary
    public DataSource primaryDataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(env.getProperty("primary.url"));
        dataSource.setUser(env.getProperty("primary.username"));
        dataSource.setPassword(env.getProperty("primary.password"));
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        Properties properties = new Properties();
        /*
        MinLimit:1
        MaxLimit:12
        InitialLimit:1
        ConnectionWaitTimeout:120
        InactivityTimeout:180
        ValidateConnection:true
        */
        properties.setProperty("MinLimit", "1");
        properties.setProperty("MaxLimit", "8");
        properties.setProperty("InitialLimit", "1");
        properties.setProperty("ConnectionWaitTimeout", "128");
        properties.setProperty("InactivityTimeout", "180");
        properties.setProperty("ValidateConnection", "true");
        dataSource.setConnectionProperties(properties);

        return dataSource;
    }

    @Bean
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("secondary.driverClassName"))
                .url(env.getProperty("secondary.url"))
                .username(env.getProperty("secondary.username"))
                .password(env.getProperty("secondary.password"))
                .build();
    }

    @Bean
    public TransactionAwareDataSourceProxy primaryTransactionAwareDataSource() throws SQLException {
        return new TransactionAwareDataSourceProxy(primaryDataSource());
    }

    @Bean
    public DataSourceTransactionManager primaryTransactionManager() throws SQLException {
        // return new DataSourceTransactionManager( primaryDataSource() );
        return new DataSourceTransactionManager( primaryTransactionAwareDataSource() );
    }

    @Bean
    public JdbcTemplate primaryJdbcTemplate() throws SQLException {
        return  new JdbcTemplate( primaryTransactionAwareDataSource() );
    }

}
