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
package org.jbr.taskmgr.service.task.impl;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jbr.commons.service.EntityServiceException;
import org.jbr.commons.service.crud.CrudServiceUtils;
import org.jbr.commons.service.crud.CrudServiceUtils.CountCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.CreateCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.DeleteAllCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.DeleteByIdCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.DeleteByUuidCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.FindByIdCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.FindByNameCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.FindByUUIDCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.RetrieveCallback;
import org.jbr.commons.service.crud.CrudServiceUtils.UpdateCallback;
import org.jbr.commons.service.uuid.UUIDService;
import org.jbr.taskmgr.model.domain.Task;
import org.jbr.taskmgr.repository.TaskRepository;
import org.jbr.taskmgr.service.DuplicateEntityException;
import org.jbr.taskmgr.service.NullEntityException;
import org.jbr.taskmgr.service.TaskManagerException;
import org.jbr.taskmgr.service.TaskManagerException.ExceptionType;
import org.jbr.taskmgr.service.TaskManagerException.OperationType;
import org.jbr.taskmgr.service.UnknownEntityException;
import org.jbr.taskmgr.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

/**
 * Provides a JPA implementation of a service that manages {@link Task}s for the
 * Task Manager application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UUIDService uuidService;

	private final CrudServiceUtils<Task, Long, String> crudServiceUtils =
			new CrudServiceUtils<Task, Long, String>();

	private EventBus eventBus;

	@Override
	public long countTasks() throws TaskManagerException {
		return countTasks(null);
	}

	@Override
	public long countTasks(final Task criteria) throws TaskManagerException {
		try {
			return crudServiceUtils.countEntities(Task.class, criteria, new CountCallback<Task>() {
				@Override
				public long count(Task criteria) {
					if (criteria == null)
						return taskRepository.count();
					else
						throw new UnsupportedOperationException();
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.COUNT, criteria, e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = TaskManagerException.class)
	public Task createTask(final Task task) throws TaskManagerException {
		try {
			return crudServiceUtils.createEntity(Task.class, task, new CreateCallback<Task>() {
				@Override
				public void create(final Task task) throws EntityServiceException {
					task.setUuid(uuidService.nextUUID());
					taskRepository.save(task);
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.DuplicateEntityException e) {
			throw new DuplicateEntityException(ExceptionType.TASK, OperationType.CREATE, task, e.getMessage(), e);
		} catch (final org.jbr.commons.service.NullEntityException e) {
			throw new NullEntityException(ExceptionType.TASK, OperationType.CREATE, task, e.getMessage(), e);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.CREATE, task, e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = TaskManagerException.class)
	public void deleteAllTasks() throws TaskManagerException {
		try {
			crudServiceUtils.deleteAllEntities(Task.class, new DeleteAllCallback<Task>() {
				@Override
				public void deleteAll() {
					taskRepository.deleteAll();
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.DELETE_ALL, e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = TaskManagerException.class)
	public void deleteTask(final Long taskOid) throws TaskManagerException {
		try {
			crudServiceUtils.deleteEntity(Task.class, taskOid, new DeleteByIdCallback<Long>() {
				@Override
				public void delete(final Long entityOid) {
					taskRepository.delete(entityOid);
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.DELETE, taskOid, e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = TaskManagerException.class)
	public void deleteTask(final UUID uuid) throws TaskManagerException {
		try {
			crudServiceUtils.deleteEntity(Task.class, uuid, new DeleteByUuidCallback() {
				@Override
				public void delete(final UUID uuid) throws EntityServiceException {
					final Task task = taskRepository.findByUuid(uuid);
					if (task != null) {
						taskRepository.delete(task);
					} else {
						throw new org.jbr.commons.service.UnknownEntityException(
								"No task found under uuid '" + uuid + "'!", uuid);
					}
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.UnknownEntityException e) {
			throw new UnknownEntityException(ExceptionType.TASK, OperationType.DELETE, uuid, e.getMessage(), e);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.DELETE, uuid, e.getMessage(), e);
		}
	}

	@Override
	public Task findTaskById(final Long taskOid) throws TaskManagerException {
		try {
			return crudServiceUtils.findById(Task.class, taskOid, new FindByIdCallback<Task, Long>() {
				@Override
				public Task findById(final Long id) {
					return taskRepository.findOne(id);
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.FIND, taskOid, e.getMessage(), e);
		}
	}

	@Override
	public Task findTaskByName(final String taskName) throws TaskManagerException {
		try {
			return crudServiceUtils.findByName(Task.class, taskName, new FindByNameCallback<Task, String>() {
				@Override
				public Task findByName(final String name) {
					return taskRepository.findByName(name);
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.FIND, taskName, e.getMessage(), e);
		}
	}

	@Override
	public Task findTaskByUuid(@NotNull final UUID uuid) throws TaskManagerException {
		try {
			return crudServiceUtils.findByUUID(Task.class, uuid, new FindByUUIDCallback<Task>() {
				@Override
				public Task findByUUID(final UUID uuid) throws EntityServiceException {
					return taskRepository.findByUuid(uuid);
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.FIND, uuid, e.getMessage(), e);
		}
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public List<Task> retrieveTasks(final Task criteria) throws TaskManagerException {
		try {
			final List<Task> tasks = crudServiceUtils.retrieveEntities(Task.class, criteria,
					new RetrieveCallback<Task>() {
						@Override
						public List<Task> retrieve(final Task criteria) {
							if (criteria == null)
								return Lists.newArrayList(taskRepository.findAll());
							else
								// TODO - implement retrieveTasks for criteria
								return null;
						}
					}, eventBus);
			return tasks;
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.RETRIEVE, criteria, e.getMessage(), e);
		}
	}

	@Override
	public void setEventBus(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	@Transactional(rollbackFor = TaskManagerException.class)
	public Task updateTask(final Task task) throws TaskManagerException {
		try {
			return crudServiceUtils.updateEntity(Task.class, task, new UpdateCallback<Task>() {
				@Override
				public Task update(final Task task) throws EntityServiceException {
					final Task existingTask = taskRepository.findByUuid(task.getUuid());
					if (existingTask != null) {
						if (task.getName() != null) {
							existingTask.setName(task.getName());
						}
						if (task.getDescription() != null) {
							existingTask.setDescription(task.getDescription());
						}
						if (task.getStatus() != null) {
							existingTask.setStatus(task.getStatus());
						}
						return taskRepository.save(existingTask);
					} else {
						throw new org.jbr.commons.service.UnknownEntityException(
								"Unable to find task under uuid '" + task.getUuid() + "'!", 
								task.getUuid());
					}
				}
			}, eventBus);
		} catch (final org.jbr.commons.service.NullEntityException e) {
			throw new NullEntityException(ExceptionType.TASK, OperationType.UPDATE, task, e.getMessage(), e);
		} catch (final org.jbr.commons.service.UnknownEntityException e) {
			throw new UnknownEntityException(ExceptionType.TASK, OperationType.UPDATE, task, e.getMessage(), e);
		} catch (final org.jbr.commons.service.EntityServiceException e) {
			throw new TaskManagerException(ExceptionType.TASK, OperationType.UPDATE, task, e.getMessage(), e);
		}
	}

}
