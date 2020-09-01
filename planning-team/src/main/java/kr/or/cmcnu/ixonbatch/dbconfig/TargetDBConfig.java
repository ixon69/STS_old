package kr.or.cmcnu.ixonbatch.dbconfig;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TargetDBConfig {
	
	@Bean(name="tDB")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name="tSqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("tDB") DataSource dataSource, ApplicationContext applicationContext) throws Exception  {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/mysql/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

    @Bean(name="tSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory  sqlSessionFactory) throws Exception {
           return new SqlSessionTemplate(sqlSessionFactory);
    }

}
