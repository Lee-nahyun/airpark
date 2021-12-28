package kr.airport.parking.reduction.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * Property 파일 설정
 *
 * <pre>
 * [ 개정이력(Modification Information) ]
 * --------------  --------  ----------------------
 * 2015. 8. 7.    ysKo      신규생성
 * --------------  --------  ----------------------
 * </pre>
 *
 *
 */
@Configuration
public class PropertyConfig {
	
	/**
	 * Properties 설정 1 : (SpEL 사용)
	 * 
	 * @since
	 * @작성자 ysKo
	 * @작성일 2015. 8. 7.
	 * @수정자
	 * @수정일
	 * @수정내용
	 *
	 * @return
	 */
	@Bean(name="appConfig")
	public static PropertiesFactoryBean applicationProperties() {
	    PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
	    ClassPathResource classPathResource = new ClassPathResource("config/application.xml");
	    factoryBean.setLocation(classPathResource);
	  
	    return factoryBean;
	}
	
	/**
	 * Properties 설정 2 : @PropertySource Annotation 사용 (${aaa} 형태)
	 * 
	 * @since
	 * @작성자 ysKo
	 * @작성일 2015. 8. 7.
	 * @수정자
	 * @수정일
	 * @수정내용
	 *
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer placeholderConfigure = new PropertySourcesPlaceholderConfigurer();
		/*
		config.setIgnoreResourceNotFound(true);
		config.setIgnoreUnresolvablePlaceholders(true);
		*/
		return placeholderConfigure;
	}
}
