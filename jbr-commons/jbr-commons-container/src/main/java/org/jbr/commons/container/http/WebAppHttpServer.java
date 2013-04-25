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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.WebApplicationInitializer;

/**
 * TODO - type header
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class WebAppHttpServer extends HttpServerSupport {

	private WebApplicationInitializer webApplicationInitializer;

	@Override
	protected void configureApplication(final Server server) throws Exception {
		final ServletContextHandler handler = new ServletContextHandler();
		handler.addEventListener(new ServletContextListener() {
			public void contextDestroyed(final ServletContextEvent event) {
				// NO-OP
			}
			public void contextInitialized(final ServletContextEvent event) {
				try {
					webApplicationInitializer.onStartup(event.getServletContext());
				} catch (final Exception e) {
					log.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		});
		server.setHandler(handler);
	}

	@Required
	public void setWebApplicationInitializer(
			final WebApplicationInitializer webApplicationInitializer) {
		this.webApplicationInitializer = webApplicationInitializer;
	}

}
