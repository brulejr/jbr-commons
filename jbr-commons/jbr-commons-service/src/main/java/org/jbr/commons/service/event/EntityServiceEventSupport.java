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
 * TODO - type header
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public abstract class EntityServiceEventSupport<E, I, N>
		extends ServiceEventSupport
		implements EntityServiceEvent<E, I, N> {

	private E entity;
	private I entityID;
	private N entityName;
	private UUID entityUUID;

	@Override
	public E getEntity() {
		return entity;
	}

	@Override
	public N getEntityName() {
		return entityName;
	}

	@Override
	public I getEntityID() {
		return entityID;
	}

	@Override
	public UUID getEntityUUID() {
		return entityUUID;
	}

	@Override
	public void setEntity(final E entity) {
		this.entity = entity;
	}

	@Override
	public void setEntityName(final N entityName) {
		this.entityName = entityName;
	}

	@Override
	public void setEntityID(final I entityID) {
		this.entityID = entityID;
	}

	@Override
	public void setEntityUUID(final UUID entityUUID) {
		this.entityUUID = entityUUID;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " ["
				+ "id=" + getId()
				+ ", timestamp=" + getTimestamp()
				+ ", text=" + getText()
				+ ", entity=" + getEntity()
				+ ((entityName != null) ? ", entityName=" + getEntityName() : "")
				+ ((entityUUID != null) ? ", entityUUID=" + getEntityUUID() : "")
				+ "]";
	}

}
