package kr.airport.parking.reduction.config.adaptor;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis Configure Adaptor
 * 
 * @author ysKo
 * @since 0.0.5-SNAPSHOT
 */
@Configuration
public abstract class MybatisConfigureAdaptor {
	
	private static Logger log = LoggerFactory.getLogger(MybatisConfigureAdaptor.class);
	
	@Bean public abstract DataSource dataSource();
	@Bean public abstract SqlSessionFactory sqlSessionFactory();
	@Bean public abstract SqlSessionTemplate sqlSessionTemplate();
	
	protected void setupDataSource(BasicDataSource dataSource){ /**TODO */ };
	protected void setupSqlSessionFactoryBean(SqlSessionFactoryBean factoryBean){ /**TODO */ }
	protected void setupMybatisProperties(Properties props) { /**TODO */ }
	
	protected final void setDefaultMybatisProperties(Properties props) {
		props.put("cacheEnabled", true);
		props.put("lazyLoadingEnabled", false);
		props.put("multipleResultSetsEnabled", true);
		props.put("useColumnLabel", true);
		props.put("useGeneratedKeys", false);
		props.put("defaultExecutorType", "SIMPLE");
		props.put("defaultStatementTimeout", 25000);
	}
	
	
	protected final DataSource buildDataSource(String driverClassName, String url, String username, String password){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		setupDataSource(dataSource);
		
		return dataSource;
	}
	
	protected final SqlSessionFactory buildSessionFactory(boolean useDefalutMybatisProperties){
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		if(log.isDebugEnabled()) log.debug("SessionFactory Bean을 생성합니다.");
		factoryBean.setDataSource(dataSource());
		setupSqlSessionFactoryBean(factoryBean);
		
		Properties props = new Properties();
		if(useDefalutMybatisProperties)
			setDefaultMybatisProperties(props);
		setupMybatisProperties(props);
		
		factoryBean.setConfigurationProperties(props);
		
		try {
			factoryBean.afterPropertiesSet();
			if(log.isDebugEnabled()) log.debug("SessionFactory Bean을 생성하였습니다.");
			return factoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SessionFactory 빌드에 실패했습니다.", e);
		}
	}
}
