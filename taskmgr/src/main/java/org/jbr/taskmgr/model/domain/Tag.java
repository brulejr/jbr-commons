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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jbr.commons.lang.xml.XmlUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Bean that defines a standard tag.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Entity
@Table(name = "T_TAG")
@XStreamAlias("Tag")
public class Tag {

	public static class Builder {
		
		private Tag tag = new Tag();
		private boolean initialized = false;

		public Tag build() {
			return (initialized) ? tag : null;
		}

		public Builder setOid(final Long oid) {
			tag.oid = oid;
			initialized = true;
			return this;
		}

		public Builder setName(final String name) {
			tag.name = name;
			initialized = true;
			return this;
		}

	}

	@Id
	@Column(name = "TAG_OID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XStreamAsAttribute
	private Long oid;
	
	@Version
	@Column(name = "TAG_VERSION", nullable = false)
	@XStreamAsAttribute
	private Long version;

	@Column(name = "TAG_NAME", unique = true, nullable = false)
	@XStreamAsAttribute
	private String name;

	public Tag() {
	}
	
	public Tag(final String name) {
		this.name = name;
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
		Tag rhs = (Tag) obj;
		return new EqualsBuilder()
				.append(oid, rhs.oid)
				.append(version, rhs.version)
				.append(name, rhs.name)
				.isEquals();
	}

	/**
	 * @return the tag's object identifier
	 */
	public Long getOid() {
		return oid;
	}

	/**
	 * @return the tag's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the tag's version
	 */
	public Long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 79)
				.append(oid)
				.append(version)
				.append(name)
				.build();
	}

	/**
	 * @param oid
	 *            the tag object identifier to set
	 */
	protected void setOid(final Long oid) {
		this.oid = oid;
	}

	/**
	 * @param name
	 *            the tag name to set
	 */
	public void setName(final String name) {
		this.name = name;
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
