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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Provides a standardized web application initializer, which is a Servlet 3.0
 * replacement for a conventional <code>web.xml</code> file.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public abstract class StandardWebAppInitializer implements WebApplicationInitializer, ApplicationContextAware {

	protected ApplicationContext containerContext;

	protected void createAdditionalComponents(
			final ServletContext servletContext,
			final WebApplicationContext rootContext) {
		// does nothing
	}

	protected void createContextLoaderListener(
			final ServletContext servletContext, 
			final WebApplicationContext rootContext) {
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

	protected void createDispatcherServlet(
			final ServletContext servletContext, 
			final WebApplicationContext rootContext) {
		final ServletRegistration.Dynamic appServlet =
				servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
		appServlet.setLoadOnStartup(1);
		appServlet.addMapping("/");
	}

	protected abstract WebApplicationContext createWebApplicationContext();

	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {

		final WebApplicationContext rootContext = createWebApplicationContext();
		createContextLoaderListener(servletContext, rootContext);
		createDispatcherServlet(servletContext, rootContext);
		createAdditionalComponents(servletContext, rootContext);

	}

	@Override
	public void setApplicationContext(final ApplicationContext containerContext)
			throws BeansException {
		this.containerContext = containerContext;
	}

}
