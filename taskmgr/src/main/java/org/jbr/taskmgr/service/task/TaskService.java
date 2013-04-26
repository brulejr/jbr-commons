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
package org.jbr.taskmgr.service.task;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jbr.taskmgr.model.domain.Task;
import org.jbr.taskmgr.service.TaskManagerException;

import com.google.common.eventbus.EventBus;

/**
 * Defines the contract for a service that manages tasks for this application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public interface TaskService {

	public long countTasks() throws TaskManagerException;

	public long countTasks(Task criteria) throws TaskManagerException;

	public Task createTask(@NotNull @Valid Task task) throws TaskManagerException;

	public void deleteAllTasks() throws TaskManagerException;

	public void deleteTask(@NotNull Long taskOid) throws TaskManagerException;

	public void deleteTask(@NotNull UUID uuid) throws TaskManagerException;

	public Task findTaskById(@NotNull Long taskOid) throws TaskManagerException;

	public Task findTaskByName(@NotNull String taskName) throws TaskManagerException;
	
	public Task findTaskByUuid(@NotNull final UUID uuid) throws TaskManagerException;

	public EventBus getEventBus();
	
	public List<Task> retrieveTasks(Task criteria) throws TaskManagerException;
	
	public void setEventBus(EventBus eventBus);

	public Task updateTask(@NotNull @Valid Task task) throws TaskManagerException;

}
