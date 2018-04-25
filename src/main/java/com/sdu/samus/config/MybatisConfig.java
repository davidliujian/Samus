package com.sdu.samus.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.sdu.samus.mapper")
public class MybatisConfig {
	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() throws Exception{
		Properties props = new Properties();
		props.put("driverClassName",env.getProperty("samus.jdbc.driverClassName"));
		props.put("url",env.getProperty("samus.jdbc.url"));
		props.put("username",env.getProperty("samus.jdbc.username"));
		props.put("password",env.getProperty("samus.jdbc.password"));
		return DruidDataSourceFactory.createDataSource(props);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setDataSource(ds);
		fb.setTypeAliasesPackage("com.sdu.samus.model");//指定基包
		fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));//指定xml位置
		return fb.getObject();
	}
}
