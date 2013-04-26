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
package org.jbr.commons.service.event;

import java.util.Date;
import java.util.UUID;


/**
 * Defines a service event.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public interface ServiceEvent {
	
	public UUID getId();
	
	public String getText();

	public Date getTimestamp();
	
	public void setId(UUID id);
	
	public void setText(String text);

	public void setTimestamp(Date timestamp);
	
}
