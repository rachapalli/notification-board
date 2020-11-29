package com.board.notification.config;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import com.board.notification.CommonsConfigurationFactoryBean;
import com.board.notification.exception.NotificationException;

/**
 * Injects the database table as properties into Spring environment
 *
 */
@Configuration
public class PropertiesConfig {

	@Autowired
	private Environment environment;

	@Autowired
	private DataSource dataSource;

	@Value("${application.database.properties.key}")
	private String appDatabasePropertiesTable;

	@PostConstruct
	public void initializeDatabasePropertySourceUsage() {
		MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();

		try {
			DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(dataSource,
					appDatabasePropertiesTable, "PROP_NAME", "PROP_VALUE");
			CommonsConfigurationFactoryBean commonsConfigurationFactoryBean = new CommonsConfigurationFactoryBean(
					databaseConfiguration);

			Properties dbProps = (Properties) commonsConfigurationFactoryBean.getObject();
			PropertiesPropertySource dbPropertySource = new PropertiesPropertySource("dbPropertySource", dbProps);

			// By being First, Database Properties take precedence over all
			// other properties that have the same key name
			// You could put this last, or just in front of the
			// application.properties if you wanted to...
			propertySources.addFirst(dbPropertySource);
		} catch (Exception e) {
			throw new NotificationException(e);
		}
	}
}
