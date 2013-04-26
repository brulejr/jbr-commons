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
package org.jbr.commons.rest;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jbr.commons.service.uuid.UUIDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * TODO - type header
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Component
@Aspect
public class RestEndpointAspect {

	public static final String HEADER_ELAPSED_TIME = "x-rest-elapsed-time";
	public static final String HEADER_RESPONSE_ID = "x-rest-response-id";
	public static final String HEADER_START_TIME = "x-rest-start-time";

	private static final Logger LOG = LoggerFactory.getLogger(RestEndpointAspect.class);

	@Autowired(required = false)
	private UUIDService uuidService;

	/**
	 * Aspect that surrounds a bound RESTful controller method to provide the
	 * following enhanced functionality:
	 * 
	 * @param joinPoint
	 *            Provides reflective access to both the state available at a
	 *            join point and static information about it.
	 * @param responseType
	 *            the {@link ResponseType} annotation which is used to specify
	 *            the response class for the bound controller method
	 * @throws Throwable
	 *             any encountered error is passed through
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Around(value = "@annotation(responseTimer)", argNames = "joinPoint, responseTimer")
	public Object around(final ProceedingJoinPoint joinPoint, final RestEndpoint responseTimer) throws Throwable {

		// start execution timer
		final Date startTime = startTimer();

		// execute wrapped method
		final Object returnValue = joinPoint.proceed();

		// halt and record execution timer
		final long elapsedTime = System.currentTimeMillis() - startTime.getTime();
		LOG.debug("elapsed time for {} is {}ms", joinPoint, elapsedTime);

		// handle response entity
		if (returnValue instanceof ResponseEntity<?>) {
			final ResponseEntity<?> entity = (ResponseEntity<?>) returnValue;
			final HttpHeaders headers = new HttpHeaders();

			// transfer any existing headers, if enabled
			if (responseTimer.transferHeaders()) {
				headers.putAll(entity.getHeaders());
			}
			
			// transfer self link as Location header, if CREATED reponse
			if (entity.getStatusCode().equals(HttpStatus.CREATED)) {
				if (entity.getBody() instanceof ResourceSupport) {
					final ResourceSupport resource = (ResourceSupport) entity.getBody();
					if (resource.getId() != null) {
						headers.setLocation(new URI(resource.getId().getHref()));
					}
				}
			}

			// save elapsed time header
			headers.add(HEADER_RESPONSE_ID, generatedResponseIdentifier());
			headers.add(HEADER_START_TIME, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(startTime));
			headers.add(HEADER_ELAPSED_TIME, String.valueOf(elapsedTime));

			// return new response entity
			return new ResponseEntity(entity.getBody(), headers, entity.getStatusCode());
		}

		// handle non-response entity
		else {
			return returnValue;
		}
	}

	private String generatedResponseIdentifier() {
		return (uuidService != null)
				? uuidService.nextUUID().toString()
				: UUID.randomUUID().toString();
	}

	private Date startTimer() {
		final RequestAttributes attrs = RequestContextHolder.currentRequestAttributes();
		final int scope = RequestAttributes.SCOPE_REQUEST;
		Date startTime = (Date) attrs.getAttribute(HEADER_START_TIME, scope);
		if (startTime == null) {
			startTime = new Date();
			attrs.setAttribute(HEADER_START_TIME, startTime, scope);
		}
		return startTime;

	}

}
