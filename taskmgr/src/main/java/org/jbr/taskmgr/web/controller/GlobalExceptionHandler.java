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
package org.jbr.taskmgr.web.controller;

import org.jbr.commons.rest.RestEndpoint;
import org.jbr.commons.rest.resource.GenericMessage;
import org.jbr.commons.rest.resource.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception handler for the Task Manager application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Converts one of several client-based exception into an HTTP 400 response
	 * with an error body. The mapped exceptions are as follows:
	 * <ul>
	 * <li>{@link HttpMessageNotReadableException}</li>
	 * <li>{@link ServletRequestBindingException}</li>
	 * <li>{@link TypeMismatchException}</li>
	 * </ul>
	 * 
	 * @param e
	 *            the client exception
	 * @return the error body
	 */
	@ExceptionHandler({
			HttpMessageNotReadableException.class,
			ServletRequestBindingException.class,
			TypeMismatchException.class
	})
	@RestEndpoint
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<GenericMessage> handleClientBadRequest(
			final Exception e) {
		return createResponseEntity(e, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Converts a general, unmapped exception into an HTTP 500 response with an
	 * error body.
	 * 
	 * @param e
	 *            the general exception
	 * @return the error body
	 */
	@ExceptionHandler(Exception.class)
	@RestEndpoint
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<GenericMessage> handleGeneralException(final Exception e) {
		return createResponseEntity("Unexpected server-side error occurred!", e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<GenericMessage> createResponseEntity(final Exception e, HttpStatus httpStatus) {
		return createResponseEntity(null, e, httpStatus);
	}

	private ResponseEntity<GenericMessage> createResponseEntity(
			final String message, 
			final Exception e,
			final HttpStatus httpStatus) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message != null ? message : e.getMessage(), e);
		}
		final GenericMessage resource = new GenericMessage();
		resource.setMessage(message != null ? message : e.getMessage());
		resource.addHeader(Message.HEADER_MESSAGE_TYPE, "ERROR");
		return new ResponseEntity<>(resource, httpStatus);
	}

}