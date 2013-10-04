package context;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.trouble.lrv.ScanMarker;
import com.trouble.lrv.core.hmargresolver.PrincipalArgumentResolver;
import com.trouble.lrv.core.view.FileDownloadView;
import com.trouble.lrv.domain.CastorScanMarker;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Configuration
@ComponentScan(basePackageClasses=ScanMarker.class, excludeFilters=@Filter(type=FilterType.ASSIGNABLE_TYPE, value={Service.class, Repository.class}))
@EnableWebMvc
@EnableAspectJAutoProxy
public class WebContext extends WebMvcConfigurerAdapter {
	private final Logger logger = LoggerFactory.getLogger(WebContext.class);
	@Autowired
	PrincipalArgumentResolver argumentResolver;
	
	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setContentType("text/html; charset=UTF-8");
		viewResolver.setOrder(2);
		return viewResolver;
	}
	
	
	@Bean
	public FileDownloadView fileDownloadView(){
		return new FileDownloadView();
	}
	
	
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "/public-resources/");
	}
	
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jackson2HttpMessageConverter.setPrettyPrint(true);
		logger.info("Creating Jackson converter: "+ jackson2HttpMessageConverter.getClass().getSimpleName());
		
		converters.add(jackson2HttpMessageConverter);
		converters.add(xmlConverter());
		
	}
	
	@Bean
	public CastorMarshaller marshaller(){
		CastorMarshaller castorMarshaller = new CastorMarshaller();
		castorMarshaller.setTargetClass(CastorScanMarker.class);
		return castorMarshaller;		
	}
	
	@Bean
	public MarshallingHttpMessageConverter xmlConverter(){
		MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter(marshaller());
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.APPLICATION_XML);
		
		marshallingHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		
		return marshallingHttpMessageConverter;
		
	}
	
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.
		useJaf(false).
		defaultContentType(MediaType.APPLICATION_JSON).
		mediaType("xml", MediaType.APPLICATION_XML).
		mediaType("json",  MediaType.APPLICATION_JSON);
		
	}
	/* 
	@Bean
	public FormattingConversionServiceFactoryBean conversionService(){
		return new FormattingConversionServiceFactoryBean();
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PoliticsColorConverter.PoliticsColorToString());
		registry.addConverter(new PoliticsColorConverter.StringToPoliticsColor());
	}
	*/
	
	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(argumentResolver);
	}
	
	
	
}
