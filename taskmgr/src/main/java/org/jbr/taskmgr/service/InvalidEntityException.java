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
package org.jbr.taskmgr.service;

/**
 * Indicates an invalid entity has been encountered within the Task Manager
 * application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class InvalidEntityException extends TaskManagerException {

	private static final long serialVersionUID = -1918428206600206921L;

	/**
	 * Constructs an invalid entity exception.
	 * 
	 * @param exceptionType
	 *            the exception type
	 * @param operationType
	 *            the operation type
	 * @param entity
	 *            the offending entity
	 * @param message
	 *            the exception message
	 */
	public InvalidEntityException(
			final ExceptionType exceptionType, 
			final OperationType operationType, 
			final Object entity, 
			final String message) {
		super(exceptionType, operationType, entity, message);
	}

	/**
	 * Constructs an invalid entity exception.
	 * 
	 * @param exceptionType
	 *            the exception type
	 * @param operationType
	 *            the operation type
	 * @param entity
	 *            the offending entity
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the causal exception
	 */
	public InvalidEntityException(
			final ExceptionType exceptionType, 
			final OperationType operationType, 
			final Object entity, 
			final String message,
			final Throwable cause) {
		super(exceptionType, operationType, entity, message, cause);
	}

}
