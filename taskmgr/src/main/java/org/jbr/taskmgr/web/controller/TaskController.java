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
package org.jbr.taskmgr.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.jbr.commons.rest.RestEndpoint;
import org.jbr.commons.rest.resource.Message;
import org.jbr.commons.rest.resource.MessageBuilder;
import org.jbr.taskmgr.model.TaskStatus;
import org.jbr.taskmgr.model.domain.Task;
import org.jbr.taskmgr.model.resource.TaskResource;
import org.jbr.taskmgr.service.TaskManagerException;
import org.jbr.taskmgr.service.TaskManagerException.ExceptionType;
import org.jbr.taskmgr.service.TaskManagerException.OperationType;
import org.jbr.taskmgr.service.UnknownEntityException;
import org.jbr.taskmgr.service.task.TaskService;
import org.jbr.taskmgr.web.util.TaskResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * RESTful controller that provides CRUD access to {@link Task} instances.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Controller
@RequestMapping("/api/v1/task")
public class TaskController {

	private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	private final TaskResourceAssembler taskAssembler = new TaskResourceAssembler();

	@RequestMapping(method = RequestMethod.POST)
	@RestEndpoint
	public ResponseEntity<TaskResource> createTask(@Valid @RequestBody final TaskResource task)
			throws TaskManagerException {

		final Task createdTask = taskService.createTask(taskAssembler.toEntity(task));
		if (LOG.isDebugEnabled()) {
			LOG.debug("createdTask = {}", createdTask);
		}

		final TaskResource resource = taskAssembler.toResource(createdTask);
		resource.add(linkTo(TaskController.class).slash(task.getUuid()).withSelfRel());
		return new ResponseEntity<>(resource, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
	@RestEndpoint
	public ResponseEntity<Message> deleteDocument(@PathVariable final UUID uuid)
			throws TaskManagerException {

		taskService.deleteTask(uuid);
		final Message resource = MessageBuilder
				.fromMessage("Deleted document task with uuid '" + uuid + "'")
				.build();
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
	@RestEndpoint
	public ResponseEntity<TaskResource> findDocumentByUuid(@PathVariable final UUID uuid)
			throws TaskManagerException {

		final Task foundTask = taskService.findTaskByUuid(uuid);
		if (LOG.isDebugEnabled()) {
			LOG.debug("foundTask = {}", foundTask);
		}
		if (foundTask != null) {
			final TaskResource resource = taskAssembler.toResource(foundTask);
			resource.add(linkTo(TaskController.class).slash(foundTask.getUuid()).withSelfRel());
			return new ResponseEntity<>(resource, HttpStatus.OK);
		} else
			throw new UnknownEntityException(
					ExceptionType.TASK, OperationType.FIND, uuid, 
					"No task found under uuid '" + uuid + "'!");
	}

	@RequestMapping(method = RequestMethod.GET)
	@RestEndpoint
	public ResponseEntity<Iterable<TaskResource>> retrieveTasks() throws TaskManagerException {

		final List<Task> taskList = taskService.retrieveTasks(null);
		if (LOG.isDebugEnabled()) {
			LOG.debug("taskList = {}", taskList);
		}
		final Iterable<TaskResource> resource = taskAssembler.toResources(taskList);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@RestEndpoint
	public ResponseEntity<List<Map<String, String>>> retrieveTaskStatusList() throws TaskManagerException {
		final List<Map<String, String>> taskStatusList = Lists.transform(
				Arrays.asList(TaskStatus.values()),
				new Function<TaskStatus, Map<String, String>>() {
					public Map<String, String> apply(TaskStatus status) {
						return Collections.singletonMap("name", status.name());
					}
				});
		return new ResponseEntity<>(taskStatusList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
	@RestEndpoint
	public ResponseEntity<TaskResource> updateDocument(
			@PathVariable final UUID uuid,
			@Valid @RequestBody final TaskResource task
			) throws TaskManagerException {

		task.setUuid(uuid);
		final Task updatedTask =
				taskService.updateTask(taskAssembler.toEntity(task));
		if (LOG.isDebugEnabled()) {
			LOG.debug("updatedTask = {}", updatedTask);
		}

		final TaskResource resource = taskAssembler.toResource(updatedTask);
		resource.add(linkTo(TaskController.class).slash(updatedTask.getUuid()).withSelfRel());
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

}
