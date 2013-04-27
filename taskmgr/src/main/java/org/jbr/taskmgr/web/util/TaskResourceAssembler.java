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
package org.jbr.taskmgr.web.util;

import java.util.HashSet;

import org.jbr.taskmgr.model.domain.Tag;
import org.jbr.taskmgr.model.domain.Task;
import org.jbr.taskmgr.model.resource.TaskResource;
import org.jbr.taskmgr.web.controller.TaskController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * Converts between {@link Task} domain objects and RESTful {@link TaskResource}
 * objects.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class TaskResourceAssembler extends ResourceAssemblerSupport<Task, TaskResource> {

	/**
	 * Constructs a new instance of this RESTful Task Resource assembler.
	 */
	public TaskResourceAssembler() {
		super(TaskController.class, TaskResource.class);
	}

	public Task toEntity(final TaskResource resource) {
		final Task task = new Task.Builder()
				.setUuid(resource.getUuid())
				.setName(resource.getName())
				.setVersion(resource.getVersion())
				.setStatus(resource.getStatus())
				.setDescription(resource.getDescription())
				.setTagnames(resource.getTags())
				.build();
		return task;
	}

	@Override
	public TaskResource toResource(final Task task) {
		final TaskResource resource = new TaskResource();
		resource.setUuid(task.getUuid());
		resource.setName(task.getName());
		resource.setVersion(task.getVersion());
		resource.setStatus(task.getStatus());
		resource.setDescription(task.getDescription());
		if (task.getTagnames().size() > 0) {
			resource.setTags(new HashSet<>(task.getTagnames()));
		} else {
			for (final Tag tag : task.getTags()) {
				resource.addTag(tag.getName());
			}
		}
		return resource;
	}

}
