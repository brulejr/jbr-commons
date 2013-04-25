/**
 * Copyright (C) - 2013 jbrule
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>
 */
package org.jbr.taskmgr.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * Configures the Spring MVC context for the Task Manager application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
		"org.jbr.taskmgr.web.controller"
})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/*")
				.addResourceLocations("classpath:/META-INF/webapp/WEB-INF/js/*");
		registry.addResourceHandler("/js/vendor/*")
				.addResourceLocations("classpath:/META-INF/webapp/WEB-INF/js/vendor/*");
		registry.addResourceHandler("/css/*")
				.addResourceLocations("classpath:/META-INF/webapp/WEB-INF/css/*");
		registry.addResourceHandler("/img/*")
				.addResourceLocations("classpath:/META-INF/webapp/WEB-INF/img/*");
		registry.addResourceHandler("/views/*")
				.addResourceLocations("classpath:/META-INF/webapp/WEB-INF/views/client/*");
		registry.addResourceHandler("/views/server/*")
				.addResourceLocations("classpath:/META-INF/webapp/WEB-INF/views/server/*");
		registry.addResourceHandler("/favicon.ico")
				.addResourceLocations("classpath:/META-INF/webapp/WEB-INF/view/images/favicon.ico");
	}

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(false)
				.favorParameter(false)
				.favorPathExtension(false)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("html", MediaType.TEXT_HTML)
				.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
		configurer.enable("dispatcher");
	}

	@Bean
	public InternalResourceViewResolver configureInternalResourceViewResolver() {
		final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/views/server/");
		resolver.setSuffix(".html");
		return resolver;
	}

	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		converters.add(createMappingJackson2HttpMessageConverter());
	}

	private MappingJackson2HttpMessageConverter createMappingJackson2HttpMessageConverter() {
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector());
		converter.setObjectMapper(objectMapper);
		return converter;
	}

//	@Bean
//	public RestEndpointAspect responseTimerAspect() {
//		return new RestEndpointAspect();
//	}

}
