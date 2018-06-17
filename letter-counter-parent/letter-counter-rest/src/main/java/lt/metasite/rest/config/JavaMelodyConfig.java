package lt.metasite.rest.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import net.bull.javamelody.*;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import net.bull.javamelody.MonitoringFilter;
import net.bull.javamelody.Parameter;
import net.bull.javamelody.SessionListener;

@Configuration
@ImportResource("classpath:net/bull/javamelody/monitoring-spring-aspectj.xml")
public class JavaMelodyConfig {

	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(new SessionListener());
	}

	@Bean
	public FilterRegistrationBean<MonitoringFilter> javaMelody() {
		FilterRegistrationBean<MonitoringFilter> javaMelody = new FilterRegistrationBean<MonitoringFilter>();

		javaMelody.setFilter(new MonitoringFilter());
		javaMelody.addInitParameter(Parameter.LOG.getCode(), Boolean.toString(true));
		javaMelody.addUrlPatterns("/*");
		javaMelody.setName("javamelody");

		return javaMelody;
	}

	@Bean
	public SpringDataSourceBeanPostProcessor monitoringDataSourceBeanPostProcessor() {
		SpringDataSourceBeanPostProcessor processor = new SpringDataSourceBeanPostProcessor();
		processor.setExcludedDatasources(null);
		return processor;
	}

	/**
	 * Monitoring those classes whose added @MonitoringWithSpring
	 */
	@Bean
	public MonitoringSpringAdvisor monitoringAdvisor() {
		final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
		interceptor.setPointcut(new MonitoredWithAnnotationPointcut());
		return interceptor;
	}

	/**
	 * Monitoring all function and classes those without @MonitoringWithSpring
	 * annotation
	 */
	@Bean
	public MonitoringSpringAdvisor springServiceMonitoringAdvisor() {
		final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
		interceptor.setPointcut(new AnnotationMatchingPointcut(Service.class));
		return interceptor;
	}

	/**
	 * Monitoring all services and controller of application
	 */
	@Bean
	public MonitoringSpringAdvisor springRestControllerMonitoringAdvisor() {
		final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
		interceptor.setPointcut(new AnnotationMatchingPointcut(RestController.class));
		return interceptor;
	}
}