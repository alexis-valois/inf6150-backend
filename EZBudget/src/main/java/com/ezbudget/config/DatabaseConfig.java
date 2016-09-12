package com.ezbudget.config;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ezbudget.entity.IEntity;
import com.ezbudget.repository.IRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	private static Logger logger = LoggerFactory
			.getLogger(DatabaseConfig.class);

	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	@Value("${jdbc.pool.maxsize}")
	private int poolSize;

	@Bean
	public HashMap<String, IRepository<IEntity>> getRepositories() {
		return new HashMap<String, IRepository<IEntity>>();
	}
	
	@Bean
	public DataSource getSqlDataSource() {
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(poolSize);
		config.setIdleTimeout(1000 * 60 * 60);
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(jdbcUsername);
		config.setPassword(jdbcPassword);
		config.setConnectionTimeout(10 * 1000);
		logger.info("[bootstrap] database={}", jdbcUrl);
		return new HikariDataSource(config);
	}

	@Bean
	public JdbcTemplate getMySqlJdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	@Bean
	public DefaultFlywayConfig flyway(DataSource dataSource) {
		return new DefaultFlywayConfig();
	}

	class DefaultFlywayConfig {

		@Autowired
		private DataSource dataSource;

		@Autowired
		private JdbcTemplate jdbcTemplate;

		@Value("${flyway.enabled:false}")
		private boolean enabled;

		@Value("${flyway.version:@null}")
		private String version;

		@PostConstruct
		private void init() {
			if (enabled) {
				Flyway fly = new Flyway();
				fly.setDataSource(dataSource);
				fly.setLocations("sql/db");
				if (version != null && !"@null".equals(version)) {
					fly.setBaselineOnMigrate(true);
					fly.setBaselineVersionAsString(version);
				} else {
					fly.setBaselineVersionAsString("0");
				}
				fly.migrate();
			}
		}
	}
}
