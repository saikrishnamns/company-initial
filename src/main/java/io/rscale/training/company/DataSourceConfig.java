package io.rscale.training.company;

import javax.annotation.PostConstruct;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
@Profile("local")
@Configuration
public class DataSourceConfig {

    private static final Logger logger = Logger.getLogger(DataSourceConfig.class);

    @Value("${mysql.dbName}")
    private String mysqlDbName;
    
    @Value("${mysql.user}")
    private String mysqlUser;
    
    @Value("${mysql.password}")
    private String mysqlPassword;
    
    @Value("${mysql.server}")
    private String mysqlServer;
    
    @Value("${mysql.port}")
    private int mysqlPort;
    
    private DataSource dataSource;
    
    public DataSourceConfig() {}
    
    @PostConstruct
    public void init() {
        logger.info(this.getClass() + " loaded");
        try {
	    	MysqlDataSource mysql = new MysqlDataSource();
	    	mysql.setDatabaseName(mysqlDbName);
	    	mysql.setUser(mysqlUser);
	    	mysql.setPassword(mysqlPassword);
	    	mysql.setServerName(mysqlServer);
	    	mysql.setPort(mysqlPort);
	    	logger.info("Created datasource:  " + mysql);
	        this.dataSource = mysql;
    	} catch (Exception e) {
    		logger.error("Could not create datasoruce", e);
    	}
        logger.info(this.getClass() + " initialized");
    }

    @Bean
    public DataSource dataSource() {
    	return dataSource;
    }

}
