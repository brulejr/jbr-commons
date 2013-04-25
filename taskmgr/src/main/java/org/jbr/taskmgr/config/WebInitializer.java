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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initializes the Servlet 3 web application container for the Task Manager
 * application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class WebInitializer implements WebApplicationInitializer, ApplicationContextAware {

	private ApplicationContext containerContext;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherContext =
				new AnnotationConfigWebApplicationContext();
		dispatcherContext.setParent(containerContext);
		dispatcherContext.register(WebMvcConfig.class);
		dispatcherContext.registerShutdownHook();

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher =
				servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	@Override
	public void setApplicationContext(ApplicationContext containerContext) throws BeansException {
		this.containerContext = containerContext;
	}

}
