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

import org.jbr.commons.container.SpringContainerException;
import org.jbr.commons.container.SpringContainerSupport;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.util.Assert;

/**
 * TODO - type header
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class JavaSpringContainer extends SpringContainerSupport {

	public final static String JVM_PARM_CONTEXT_CONFIG_CLASS = "JavaSpringContainer.contextConfigClass";
	public final static String JVM_PARM_PROFILE = "app.env";

	private final static String DEFAULT_PROFILE = "LOCAL";

	/**
	 * Provides a container-based application with its main entry point.
	 * 
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(final String[] args) {
		try {
			final JavaSpringContainer container = new JavaSpringContainer(args);
			container.startup();
		} catch (final SpringContainerException e) {
			e.printStackTrace();
		}
	}

	private Class<?> contextConfigClass;

	/**
	 * Constructs a new JavaConfig-based Spring container.
	 * 
	 * @param contextConfigClass
	 *            the context configuration class
	 */
	public JavaSpringContainer(final Class<?> contextConfigClass) {
		setContextConfigClass(contextConfigClass);
	}

	/**
	 * Constructs a new JavaConfig-based Spring container.
	 * 
	 * @param args
	 *            the command-line arguments
	 */
	public JavaSpringContainer(final String[] args) {
	}

	/**
	 * Constructs the application context used by this container.
	 * 
	 * @return this container's new application context
	 * @throws SpringContainerException
	 */
	@Override
	protected AbstractApplicationContext createApplicationContext() throws SpringContainerException {
		final Class<?> contextConfigClass = getContextConfigClass();
		final String profile = System.getProperty(JVM_PARM_PROFILE, getDefaultProfile());
		log.info(
				"Configuring Java SpringContainer using registered class [" + contextConfigClass
						+ "] under profile [" + profile + "]");
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles(profile);
		context.register(contextConfigClass);
		context.registerShutdownHook();
		return context;
	}

	/**
	 * Obtains the single JavaConfig class used to register when building this
	 * container's application context. By default, this looks toward a JVM
	 * parameter by the name <code>JavaSpringContainer.contextConfigClass</code>
	 * for the configuration classname. This behavior, however, is overriden if
	 * the classname is passed as a constructor parameter.
	 * 
	 * @return the context configuration class
	 * @throws SpringContainerException
	 *             if unable to find the configuration classname specified by
	 *             the JVM parameter
	 */
	public Class<?> getContextConfigClass() throws SpringContainerException {
		if (contextConfigClass != null)
			return contextConfigClass;
		else {
			try {
				final String configClassname = System.getProperty(JVM_PARM_CONTEXT_CONFIG_CLASS);
				Assert.notNull(
						configClassname,
						"Missing '" + JVM_PARM_CONTEXT_CONFIG_CLASS + "' JVM parameter! "
								+ "Required to bootstrap " + getClass().getSimpleName() + "!");
				return Class.forName(configClassname);
			} catch (final ClassNotFoundException e) {
				throw new SpringContainerException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Obtains the default Spring bean profile to activate.
	 * 
	 * @return the default Spring bean profile
	 */
	public String getDefaultProfile() {
		return DEFAULT_PROFILE;
	}

	protected void setContextConfigClass(final Class<?> contextConfigClass) {
		this.contextConfigClass = contextConfigClass;
	}

}
