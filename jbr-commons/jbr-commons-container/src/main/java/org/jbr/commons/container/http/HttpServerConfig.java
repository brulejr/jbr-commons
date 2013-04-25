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
package org.jbr.commons.container.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

/**
 * Parameterized Spring Java configuration for a {@link WebAbbHttpServer}.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Configuration
public class HttpServerConfig {

	@Bean
	public HttpServerSupport httpServer(
			final WebApplicationInitializer webApplicationInitializer,
			@Value("${http.server.name:HttpServer}") final String name,
			@Value("${http.server.port:9080}") final int port,
			@Value("${http.server.minThreads:1}") final int minThreads,
			@Value("${http.server.maxThreads:10}") final int maxThreads
			) {
		final WebAppHttpServer server = new WebAppHttpServer();
		server.setWebApplicationInitializer(webApplicationInitializer);
		server.setName(name);
		server.setPort(port);
		server.setMinThreads(minThreads);
		server.setMaxThreads(maxThreads);
		return server;
	}

}
