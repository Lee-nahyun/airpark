package kr.airport.parking.reduction.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

@Configuration
@EnableWebMvc
@ComponentScan(
	basePackages={"kr.airport"},
	excludeFilters={@Filter(Configuration.class)}
)
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Value("#{appConfig['upload.maxUploadSize']}")
	private Integer maxUploadSize;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/js/**").addResourceLocations("/resources/js/");
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		
		/**
		 * MessageConverter
		 * 
		 * Jackson MessageConverter 는 pom에 org.codehaus.jackson 를 추가하면 자동으로 등록됨
		 * IE9 이하에서 ajaxSumbit() 처리시 오류가 발생하므로 (XMLHttpRequest 헤더가 안넘어옴) MediaType을 text/html로 변경하여 처리함
		 */
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(new MediaType("text", "html", Charset.forName("UTF-8")));
		mediaTypes.add(new MediaType("text", "xml", Charset.forName("UTF-8")));
		jsonMessageConverter.setSupportedMediaTypes(mediaTypes);
		
		/** 
		 * ObjectMapper
		 */
		ObjectMapper mapper = new ObjectMapper();
		
		//Timezone
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		mapper.setDateFormat(dateFormat);
		mapper.setTimeZone(TimeZone.getDefault());
		
		//Null Value
		mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){

			@Override
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jgen.writeString("");
			}
			
		});
		
		/**
		 * StringJsonSerializer 설정
		 */
		SimpleModule module = new SimpleModule();
		module.addSerializer(String.class, new StringSerializer());
		mapper.registerModule(module);
	}
	
	
	/**
	 * bean name resolver
	 */
	@Bean
	public ViewResolver beanNameViewResolver(){
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		resolver.setOrder(0);
		return resolver;
	}
	
	
	/**
	 * resolver config
	 */
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setOrder(2);
		return resolver;
	}
	

}
