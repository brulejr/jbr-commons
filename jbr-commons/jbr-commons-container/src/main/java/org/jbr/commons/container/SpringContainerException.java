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

/**
 * General exception for a Spring Container error.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class SpringContainerException extends Exception {

	private static final long serialVersionUID = 5390797173686117678L;

	/**
	 * @param message
	 *            the exception message
	 */
	public SpringContainerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 *            the causal exception
	 */
	public SpringContainerException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the causal exception
	 */
	public SpringContainerException(String message, Throwable cause) {
		super(message, cause);
	}

}
