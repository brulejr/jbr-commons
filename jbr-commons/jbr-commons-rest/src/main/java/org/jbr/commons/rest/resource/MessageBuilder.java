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

/**
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class MessageBuilder {

	public static MessageBuilder fromClass(final Class<Message> messageClass) {
		return new MessageBuilder(messageClass);
	}

	public static MessageBuilder fromMessage(final String message) {
		return new MessageBuilder(message);
	}

	protected final Message message;

	protected MessageBuilder(final Class<Message> messageClass) {
		try {
			message = messageClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	protected MessageBuilder(final String message) {
		this.message = new GenericMessage();
		this.message.setMessage(message);
	}

	public MessageBuilder addHeader(final String key, final Object value) {
		message.addHeader(key, value);
		return this;
	}

	public Message build() {
		return message;
	}

	public MessageBuilder setHeaders(final Map<String, Object> headers) {
		message.setHeaders(headers);
		return this;
	}

	public MessageBuilder setMessage(final String message) {
		this.message.setMessage(message);
		return this;
	}

}
