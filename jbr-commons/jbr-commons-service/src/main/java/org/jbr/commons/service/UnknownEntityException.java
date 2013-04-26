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
 * Indicates an unknown entity has been encountered.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class UnknownEntityException extends EntityServiceException {

	private static final long serialVersionUID = 2096895709294752905L;

	/**
	 * Constructs an unknown entity service exception.
	 * 
	 * @param message
	 *            the exception message
	 * @param entityId
	 *            the offending entity identifier
	 */
	public UnknownEntityException(final String message, final Object entityId) {
		super(EntityServiceExceptionType.UNKNOWN_ENTITY, message, entityId);
	}

	/**
	 * Constructs an unknown entity service exception.
	 * 
	 * @param message
	 *            the exception message
	 * @param entityId
	 *            the offending entity identifier
	 * @param cause
	 *            the causal exception
	 */
	public UnknownEntityException(final String message, final Object entityId, final Throwable cause) {
		super(EntityServiceExceptionType.UNKNOWN_ENTITY, message, entityId, cause);
	}

}
