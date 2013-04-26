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
package org.jbr.commons.service;

/**
 * Provides a base exception for any service implementation.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class EntityServiceException extends Exception {

	private static final long serialVersionUID = 4619611137550454203L;

	private final EntityServiceExceptionType type;

	private final Object data;

	/**
	 * Constructs a generic entity service exception.
	 * 
	 * @param type
	 *            the exception type
	 * @param message
	 *            the exception message
	 * @param data
	 *            the exception data
	 */
	public EntityServiceException(final EntityServiceExceptionType type, final String message, final Object data) {
		super(message);
		this.type = type;
		this.data = data;
	}

	/**
	 * Constructs a generic entity service exception.
	 * 
	 * @param type
	 *            the exception type
	 * @param message
	 *            the exception message
	 * @param data
	 *            the exception data
	 * @param cause
	 *            the causal exception
	 */
	public EntityServiceException(final EntityServiceExceptionType type, final String message, final Object data, final Throwable cause) {
		super(message, cause);
		this.type = type;
		this.data = data;
	}

	/**
	 * Constructs a generic entity service exception.
	 * 
	 * @param message
	 *            the exception message
	 * @param data
	 *            the exception data
	 */
	public EntityServiceException(final String message, final Object data) {
		super(message);
		type = EntityServiceExceptionType.GENERAL;
		this.data = data;
	}

	/**
	 * Constructs a generic service exception.
	 * 
	 * @param message
	 *            the exception message
	 * @param data
	 *            the exception data
	 * @param cause
	 *            the causal exception
	 */
	public EntityServiceException(final String message, final Object data, final Throwable cause) {
		super(message, cause);
		type = EntityServiceExceptionType.GENERAL;
		this.data = data;
	}

	/**
	 * Obtains the data for this exception.
	 * 
	 * @return this exception's data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Obtains the type for this exception.
	 * 
	 * @return this exception's type
	 */
	public EntityServiceExceptionType getType() {
		return type;
	}

}
