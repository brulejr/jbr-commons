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
package org.jbr.taskmgr.model.resource;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbr.commons.lang.xml.XmlUtils;
import org.jbr.taskmgr.model.TaskStatus;
import org.jbr.taskmgr.model.domain.Task;
import org.springframework.hateoas.ResourceSupport;

/**
 * RESTful resource model for a domain {@link Task}.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@XmlRootElement(name = "TaskResource")
public class TaskResource extends ResourceSupport {

	private UUID uuid;
	private Long version;
	private String name;
	private String description;
	private TaskStatus status;

	private Set<String> tags = new HashSet<>(0);

	public void addTag(final String tag) {
		tags.add(tag);
	}
	
	public String getDescription() {
		return description;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	@XmlAttribute
	public TaskStatus getStatus() {
		return status;
	}

	public Set<String> getTags() {
		return tags;
	}

	@XmlAttribute
	public UUID getUuid() {
		return uuid;
	}

	@XmlAttribute
	public Long getVersion() {
		return version;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStatus(final TaskStatus status) {
		this.status = status;
	}

	public void setTags(final Set<String> tags) {
		this.tags = tags;
	}

	public void setUuid(final UUID uuid) {
		this.uuid = uuid;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return XmlUtils.toXml(this);
	}

}
