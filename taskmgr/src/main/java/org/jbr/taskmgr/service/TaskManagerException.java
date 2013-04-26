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
 * Base exception for the Task Manager application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class TaskManagerException extends Exception {

	public enum ExceptionType {
		TAG, TASK;
	}

	public enum OperationType {
		AUTHENTICATE, COUNT, CREATE, DELETE, DELETE_ALL, FIND, RETRIEVE, UPDATE;
	}

	private static final long serialVersionUID = -7047222061765267498L;

	private final ExceptionType exceptionType;

	private final OperationType operationType;

	private final Object entity;

	/**
	 * Constructs a general Task Manager application exception.
	 * 
	 * @param exceptionType
	 *            the exception type
	 * @param operationType
	 *            the operation type
	 * @param entity
	 *            the offending entity
	 * @param message
	 *            the error message
	 */
	public TaskManagerException(
			final ExceptionType exceptionType,
			final OperationType operationType,
			final Object entity,
			final String message) {
		super(message);
		this.exceptionType = exceptionType;
		this.operationType = operationType;
		this.entity = entity;
	}

	/**
	 * Constructs a general Task Manager application exception.
	 * 
	 * @param exceptionType
	 *            the exception type
	 * @param operationType
	 *            the operation type
	 * @param entity
	 *            the offending entity
	 * @param message
	 *            the error message
	 * @param cause
	 *            the causal exception
	 */
	public TaskManagerException(
			final ExceptionType exceptionType,
			final OperationType operationType,
			final Object entity,
			final String message,
			final Throwable cause) {
		super(message, cause);
		this.exceptionType = exceptionType;
		this.operationType = operationType;
		this.entity = entity;
	}

	/**
	 * Constructs a general Task Manager application exception.
	 * 
	 * @param exceptionType
	 *            the exception type
	 * @param operationType
	 *            the operation type
	 * @param message
	 *            the error message
	 */
	public TaskManagerException(
			final ExceptionType exceptionType,
			final OperationType operationType,
			final String message) {
		super(message);
		this.exceptionType = exceptionType;
		this.operationType = operationType;
		entity = null;
	}

	/**
	 * Constructs a general Task Manager application exception.
	 * 
	 * @param exceptionType
	 *            the exception type
	 * @param operationType
	 *            the operation type
	 * @param message
	 *            the error message
	 * @param cause
	 *            the causal exception
	 */
	public TaskManagerException(
			final ExceptionType exceptionType,
			final OperationType operationType,
			final String message,
			final Throwable cause) {
		super(message, cause);
		this.exceptionType = exceptionType;
		this.operationType = operationType;
		entity = null;
	}

	/**
	 * Obtains the offending entity associated with this exception.
	 * 
	 * @return this exception's offending entity
	 */
	public Object getEntity() {
		return entity;
	}

	/**
	 * Obtains the exception type for this exception.
	 * 
	 * @return this exception's exception type
	 */
	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	/**
	 * Obtains the operation type for this exception.
	 * 
	 * @return this exception's operation type
	 */
	public OperationType getOperationType() {
		return operationType;
	}
	
}
