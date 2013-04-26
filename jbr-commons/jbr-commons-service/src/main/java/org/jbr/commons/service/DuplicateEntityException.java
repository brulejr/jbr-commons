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
 * Indicates a duplicate entity has been encountered.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class DuplicateEntityException extends EntityServiceException {

	private static final long serialVersionUID = 2096895709294752905L;

	/**
	 * Constructs a duplicate entity service exception.
	 * 
	 * @param message
	 *            the exception message
	 * @param entity
	 *            the offending entity
	 */
	public DuplicateEntityException(final String message, final Object entity) {
		super(EntityServiceExceptionType.DUPLICATE_ENTITY, message, entity);
	}

	/**
	 * Constructs a duplicate entity service exception.
	 * 
	 * @param message
	 *            the exception message
	 * @param entity
	 *            the offending entity
	 * @param cause
	 *            the causal exception
	 */
	public DuplicateEntityException(final String message, final Object entity, final Throwable cause) {
		super(EntityServiceExceptionType.DUPLICATE_ENTITY, message, entity, cause);
	}

}
