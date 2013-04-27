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
package org.jbr.commons.rest.resource;

import java.util.Map;

import org.jbr.commons.lang.xml.XmlUtils;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * RESTful resource model used for generic message response.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class GenericMessage extends ResourceSupport implements Message {

	private String message;
	private Map<String, Object> headers;

	@Override
	public void addHeader(final String key, final Object value) {
		this.headers.put(key, value);
	}
	
	@Override
	public Map<String, Object> getHeaders() {
		return headers;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setHeaders(final Map<String, Object> headers) {
		this.headers = headers;
	}

	@Override
	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return XmlUtils.toXml(this);
	}

}
