package org.jqiaofu.spring.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

@Order(0)
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	public static final Logger log = LoggerFactory.getLogger(AppInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
		super.onStartup(getServletContext(servletContext));
	}
	
	private ServletContext getServletContext(ServletContext servletContext) {
		//Log4jConfigListener  
        servletContext.setInitParameter("log4jConfigLocation", "classpath:config/log4j.properties");
        servletContext.setInitParameter("rootLevel", "DEBUG");
        servletContext.setInitParameter("loggingLevel", "DEBUG");
        servletContext.setInitParameter("swagger.api.basepath", "http://localhost:8080/SpringDataJPA/");
        servletContext.addListener(Log4jConfigListener.class);
        return servletContext;
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{
				WebConfiguration.class,
				DataSourceConfig.class,
				AppConfig.class
                };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		Class<?> swaggerConfigClass = null;
		try {
            swaggerConfigClass = ClassUtils.forName("org.jqiaofu.spring.config.SwaggerConfig", null);
        } catch (Exception ex) {
            swaggerConfigClass = null;
            log.error("exception is thrown when loading SwaggerConfig class [org.jqiaofu.spring.config.SwaggerConfig]...@" + ex);
        }
		List<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(AppConfig.class);
        if (swaggerConfigClass != null) {
            classes.add(swaggerConfigClass);
        }
        return classes.toArray(new Class[classes.size()]);
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{ "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    return new Filter[] { characterEncodingFilter };
	}

}
