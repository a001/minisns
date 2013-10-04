package context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Driver;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.trouble.lrv.ScanMarker;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Configuration
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@EnableScheduling
@ComponentScan(basePackageClasses=ScanMarker.class, excludeFilters=@Filter(type=FilterType.ASSIGNABLE_TYPE, value=Controller.class))
@MapperScan(basePackages="com.trouble.lrv.dao")
@ImportResource("classpath:context/spring-security.xml")
@PropertySource("context/database-mysql.properties")
public class AppContext {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${db.driverClass}") Class<? extends Driver> driverClass;
	@Value("${db.url}") String url;
	@Value("${db.username}") String username;
	@Value("${db.password}") String password;
	
	@Autowired
	ResourcePatternResolver resourceResolver;
	
	@Bean
	public DataSource dataSource(){
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		
		return transactionManager;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		final String resourceLocationPattern = "classpath:com/trouble/lrv/dao/**/*Mapper.xml";
		Resource[] resource = null;
		
		try{
			resource = resourceResolver.getResources(resourceLocationPattern);
		}catch(IOException ioe){
			logger.error("resourceLocationPattern does not exists file");
			throw new FileNotFoundException();
		}
		sqlSessionFactoryBean.setMapperLocations(resource);
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setTypeAliasesPackage("com.trouble.lrv.domain");
		return sqlSessionFactoryBean.getObject();
	}
	/*
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory().getObject());
	}
	
	@Bean
	public static MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("me.anna.dao");
		mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
		return mapperScannerConfigurer;
	}
	*/
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1024000000);
		try {
			multipartResolver.setUploadTempDir(uploadDirResource());
		} catch (IOException e) {
			logger.info("uploadTempDir not Exist. {}", uploadDirResource().getPath());
		}
		return multipartResolver;
	}
	
	@Bean
	public FileSystemResource uploadDirResource(){
		return new FileSystemResource("C:/upload/");
	}
	
	@Bean
	public CacheManager cacheManager(){
		return new ConcurrentMapCacheManager();
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource bundleMessageSource =  new ReloadableResourceBundleMessageSource();
		bundleMessageSource.setBasename("classpath:prop/messages");
		return bundleMessageSource;
	}
	

}
