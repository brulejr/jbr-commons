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
package org.jbr.taskmgr.model.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.jbr.commons.lang.xml.XmlUtils;
import org.jbr.taskmgr.model.TaskStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Bean that defines a standard task.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Entity
@Table(name = "T_TASK")
@XStreamAlias("Task")
@XmlRootElement(name = "Task")
@XmlType(propOrder = { "uuid", "name", "description", "status", "version" })
@JsonInclude(Include.NON_NULL)
public class Task {

	public static class Builder {
		
		private Task task = new Task();
		private boolean initialized = false;

		public Task build() {
			return (initialized) ? task : null;
		}

		public Builder setDescription(final String description) {
			task.description = description;
			initialized = true;
			return this;
		}

		public Builder setOid(final Long oid) {
			task.oid = oid;
			initialized = true;
			return this;
		}

		public Builder setName(final String name) {
			task.name = name;
			initialized = true;
			return this;
		}

		public Builder setStatus(final TaskStatus status) {
			task.status = status;
			initialized = true;
			return this;
		}

		public Builder setUuid(final UUID uuid) {
			task.uuid = uuid;
			initialized = true;
			return this;
		}

	}

	@Id
	@Column(name = "TASK_OID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XStreamAsAttribute
	private Long oid;

	@Column(name = "TASK_UUID", unique = true, nullable = false)
	@Type(type = "uuid-char")
	@XStreamAsAttribute
	private UUID uuid;
	
	@Version
	@Column(name = "TASK_VERSION", nullable = false)
	@XStreamAsAttribute
	private Long version;

	@Column(name = "TASK_NAME", nullable = false)
	@XStreamAsAttribute
	private String name;

	@Column(name = "TASK_DESC", nullable = true)
	private String description;

	@Column(name = "TASK_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	@XStreamAsAttribute
	private TaskStatus status;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "T_TASK_TAG",
			joinColumns = { @JoinColumn(name = "TASK_OID", referencedColumnName = "TASK_OID") },
			inverseJoinColumns = { @JoinColumn(name = "TAG_OID", referencedColumnName = "TAG_OID", unique = true) })
	private Set<Tag> tags = new HashSet<>(0);

	@Transient
	private Set<String> tagnames = new HashSet<>(0);

	protected Task() {
	}

	public void addTag(final Tag tag) {
		tags.add(tag);
	}

	public void addTagname(final String tagname) {
		tagnames.add(tagname);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Task rhs = (Task) obj;
		return new EqualsBuilder()
				.append(oid, rhs.oid)
				.append(uuid, rhs.uuid)
				.append(version, rhs.version)
				.append(name, rhs.name)
				.append(description, rhs.description)
				.append(status, rhs.status)
				.isEquals();
	}

	/**
	 * @return the task's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the task's object identifier
	 */
	@XmlTransient
	public Long getOid() {
		return oid;
	}

	/**
	 * @return the task's name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * @return the task's status
	 */
	@XmlAttribute
	public TaskStatus getStatus() {
		return status;
	}

	/**
	 * @return the task's tag names
	 */
	public Set<String> getTagnames() {
		return tagnames;
	}

	/**
	 * @return the task's tags
	 */
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * @return the task's UUID
	 */
	@XmlAttribute
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @return the task's version
	 */
	public Long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 79)
				.append(oid)
				.append(uuid)
				.append(version)
				.append(name)
				.build();
	}

	/**
	 * @param description
	 *            the task description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @param oid
	 *            the task object identifier to set
	 */
	protected void setOid(final Long oid) {
		this.oid = oid;
	}

	/**
	 * @param name
	 *            the task name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param status
	 *            the task status to set
	 */
	public void setStatus(final TaskStatus status) {
		this.status = status;
	}

	public void setTagnames(final Set<String> tagnames) {
		this.tagnames = tagnames;
	}

	/**
	 * @param tags
	 *            the thing tags to set
	 */
	public void setTags(final Set<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(final UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(final Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return XmlUtils.toXml(this);
	}

}
