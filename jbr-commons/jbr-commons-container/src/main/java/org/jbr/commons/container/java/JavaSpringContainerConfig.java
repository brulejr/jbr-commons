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
package org.jbr.commons.container.java;

import javax.management.MBeanServer;

import org.jbr.commons.container.SpringContainerManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.support.MBeanServerFactoryBean;

/**
 * TODO - type header
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Configuration
public class JavaSpringContainerConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		final PropertySourcesPlaceholderConfigurer pspc =
				new PropertySourcesPlaceholderConfigurer();
		pspc.setIgnoreUnresolvablePlaceholders(true);
		return pspc;
	}

	@Bean
	public AnnotationMBeanExporter annotationMBeanExporter(
			@Value("${jmxDomain:org.jrb.shared.container}") final String jmxDomain) {
		final AnnotationMBeanExporter exporter = new AnnotationMBeanExporter();
		if (jmxDomain != null) {
			exporter.setDefaultDomain(jmxDomain);
		}
		return exporter;
	}

	@Bean
	public MBeanServer mBeanServer() {
		final MBeanServerFactoryBean msfb = new MBeanServerFactoryBean();
		msfb.setLocateExistingServerIfPossible(true);
		return msfb.getObject();
	}

	@Bean
	public SpringContainerManager springContainerManager() {
		return new SpringContainerManager();
	}

}
