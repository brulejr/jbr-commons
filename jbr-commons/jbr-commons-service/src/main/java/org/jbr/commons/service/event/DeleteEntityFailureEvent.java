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

import java.util.UUID;

/**
 * Indicates that the associated entity could not be deleted.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class DeleteEntityFailureEvent<E, I, N> extends EntityServiceFailureEventSupport<E, I, N> {
	
	public DeleteEntityFailureEvent() {
	}

	public DeleteEntityFailureEvent(final I entityID) {
		setEntityID(entityID);
	}

	public DeleteEntityFailureEvent(final I entityID, final ErrorType errorType) {
		setEntityID(entityID);
		setErrorType(errorType);
	}

	public DeleteEntityFailureEvent(final UUID entityUUID) {
		setEntityUUID(entityUUID);
	}

	public DeleteEntityFailureEvent(final UUID entityUUID, final ErrorType errorType) {
		setEntityUUID(entityUUID);
		setErrorType(errorType);
	}

}
