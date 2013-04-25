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

import org.jbr.commons.container.http.HttpServerConfig;
import org.jbr.commons.container.java.JavaSpringContainerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.WebApplicationInitializer;

/**
 * Configures the container (a.k.a. root) context for the Task Manager application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Configuration
@Import({
		JavaSpringContainerConfig.class,
		HttpServerConfig.class
})
@PropertySource({
		"classpath:config/taskmgr.properties",
		"classpath:config/${app.env:LOCAL}/taskmgr.properties"
})
public class TaskManagerContainerConfig {

	@Bean
	public WebApplicationInitializer webApplicationInitializer() {
		return new WebInitializer();
	}

}
