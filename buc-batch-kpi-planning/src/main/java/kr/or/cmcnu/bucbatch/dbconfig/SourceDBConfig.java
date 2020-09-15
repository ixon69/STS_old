package kr.or.cmcnu.bucbatch.dbconfig;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(sqlSessionFactoryRef =  "sSqlSessionFactory")
@EnableTransactionManagement
public class SourceDBConfig {

	@Bean(name="sDB")
	@ConfigurationProperties(prefix = "spring.sourcedb.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name="sSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("sDB") DataSource dataSource, ApplicationContext applicationContext) throws Exception  {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/oracle/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

    @Bean(name="sSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory  sqlSessionFactory) throws Exception {
           return new SqlSessionTemplate(sqlSessionFactory);
    }

}
