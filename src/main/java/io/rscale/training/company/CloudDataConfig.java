
package io.rscale.training.company;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("cloud")
@Configuration
public class CloudDataConfig extends AbstractCloudConfig {

	private static final Logger logger = Logger.getLogger(CloudDataConfig.class);

	public CloudDataConfig() {
		logger.info(this.getClass() + " loaded");
	}

	@Bean
	public DataSource dataSource() throws Exception {
		DataSource dataSource = cloud().getSingletonServiceConnector(DataSource.class, null);
		Connection connection = dataSource.getConnection();
		if (!isMySQL(dataSource)) {
			logger.error("MySQL required when running cloud profile.");
			throw new UnsatisfiedDependencyException("javax.sql.DataSource", "javax.sql.DataSource",
					"javax.sql.DataSource", "MySQL required when running cloud profile.");
		}
		connection.close();
		return dataSource;
	}

	private boolean isMySQL(DataSource dataSource) {
		// implement me!
		try {
			logger.info("product name" + dataSource.getConnection().getMetaData().getDatabaseProductName());
			if (dataSource.getConnection().getMetaData().getDatabaseProductName().contains("MySQL")) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("failed");
			return false;
		}
		return false;
	}
}
