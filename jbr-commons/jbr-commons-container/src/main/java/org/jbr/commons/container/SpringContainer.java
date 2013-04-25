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
package org.jbr.commons.container;

import org.springframework.context.ApplicationContext;

/**
 * Defines the contract required of a Spring Container.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public interface SpringContainer {

	/**
	 * Obtains the root application context for this container.
	 * 
	 * @return this container's root application context
	 */
	public ApplicationContext getContainerContext();

	/**
	 * Determines if this container is running.
	 * 
	 * @return <code>true</code> if this container is running; otherwise,
	 *         <code>false</code> if it is not
	 */
	public boolean isRunning();

	/**
	 * Starts this container.
	 * 
	 * @throws SpringContainerException
	 *             if this container cannot be started due to an error
	 */
	public void startup() throws SpringContainerException;

	/**
	 * Stops this container.
	 * 
	 * @throws SpringContainerException
	 *             if this container cannot be stopped due to an error
	 */
	public void shutdown() throws SpringContainerException;

}