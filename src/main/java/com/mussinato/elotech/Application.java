package com.mussinato.elotech;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
@Configuration
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public HikariDataSource dataSource() {
		Properties dbProperties = new Properties();
		dbProperties.setProperty("driverClassName", "org.postgresql.Driver");
		dbProperties.setProperty("connectionTestQuery", "SELECT 1");
		dbProperties.setProperty("maximumPoolSize", "2");
		dbProperties.setProperty("username", "webadmin");
		dbProperties.setProperty("password", "jOZAZkiKwz");
		dbProperties.setProperty("transactionIsolation", "0");
		dbProperties.setProperty("jdbcUrl", "jdbc:postgresql://10.70.17.55:5432/elotech");
		
		HikariConfig configHikari = new HikariConfig(dbProperties);
		configHikari.setTransactionIsolation("2");
		configHikari.setAutoCommit(true);
		HikariDataSource ds = new HikariDataSource(configHikari);
		return ds;
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                	.allowedOrigins("*")
                	.allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
