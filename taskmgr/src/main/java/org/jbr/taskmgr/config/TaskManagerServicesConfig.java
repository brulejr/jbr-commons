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
package org.jbr.taskmgr.config;

import org.jbr.commons.service.uuid.UUIDService;
import org.jbr.commons.service.uuid.impl.RandomUUIDServiceImpl;
import org.jbr.taskmgr.service.task.TaskService;
import org.jbr.taskmgr.service.task.impl.TaskServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

/**
 * Configures the services for the Task Manager application.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Configuration
public class TaskManagerServicesConfig {

	@Bean
	public EventBus eventBus() {
		return new EventBus();
	}

	@Bean
	public TaskService taskService(final EventBus eventBus) {
		final TaskService taskService = new TaskServiceImpl();
		taskService.setEventBus(eventBus);
		return taskService;
	}

	@Bean
	public UUIDService uuidService() {
		return new RandomUUIDServiceImpl();
	}

}
