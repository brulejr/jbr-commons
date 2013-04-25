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
package org.jbr.taskmgr;

import org.jbr.commons.container.SpringContainerException;
import org.jbr.taskmgr.config.TaskManagerContainerConfig;

/**
 * Java-based Spring application container for the Task Manager application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class JavaSpringContainer extends org.jbr.commons.container.java.JavaSpringContainer {

	/**
	 * Provides a container-based application with its main entry point.
	 * 
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(final String[] args) {
		try {
			final JavaSpringContainer container = new JavaSpringContainer(args);
			container.setContextConfigClass(TaskManagerContainerConfig.class);
			container.startup();
		} catch (final SpringContainerException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Constructs a new JavaConfig-based Spring container.
	 * 
	 * @param args
	 *            the command-line arguments
	 */
	public JavaSpringContainer(String[] args) {
		super(args);
	}

}
