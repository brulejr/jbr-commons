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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jbr.commons.service.uuid.UUIDService;
import org.jbr.commons.service.uuid.impl.RandomUUIDServiceImpl;
import org.jbr.taskmgr.config.TaskManagerDatabaseConfig;
import org.jbr.taskmgr.model.TaskStatus;
import org.jbr.taskmgr.model.domain.Task;
import org.jbr.taskmgr.service.task.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;

/**
 * Unit test case for a {@link TaskService}.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ActiveProfiles({ "LOCAL" })
public class TaskServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(TaskServiceImplTest.class);

	@Configuration
	@Import({ TaskManagerDatabaseConfig.class })
	static class TestSpringConfig {

		@Bean
		public TaskService TaskService() {
			final TaskService TaskService = new TaskServiceImpl();
			TaskService.setEventBus(new EventBus());
			return TaskService;
		}

		@Bean
		public UUIDService uuidService() {
			return new RandomUUIDServiceImpl();
		}

	}

	@Autowired
	private TaskService TaskService;

	private Task createTask(final String name) {
		return new Task.Builder()
				.setName(name)
				.setDescription("This is " + name)
				.setStatus(TaskStatus.CREATED)
				.build();
	}

	private Task createTask(final String name, final String... tagnames) {
		final Task Task = createTask(name);
		if (tagnames.length > 0) {
			Task.setTagnames(Sets.newHashSet(tagnames));
		}
		return Task;
	}

	@Test
	public void testCreateSimpleTask() {
		LOG.info("BEGIN: testCreateSimpleTask()");
		try {

			final Task Task = createTask("Task1");
			LOG.info("Task = {}", Task);
			assertNotNull(Task);

			final Task createdTask = TaskService.createTask(Task);
			LOG.info("createdTask = {}", createdTask);
			assertNotNull(createdTask);
			assertNotNull(createdTask.getOid());
			assertNotNull(createdTask.getUuid());
			assertEquals(Task.getName(), createdTask.getName());
			assertEquals(Task.getDescription(), createdTask.getDescription());
			assertEquals(Task.getStatus(), createdTask.getStatus());

			final Task foundTask = TaskService.findTaskByName("Task1");
			assertNotNull(foundTask);
			assertNotNull(foundTask.getOid());
			assertNotNull(foundTask.getUuid());
			assertEquals(Task.getName(), foundTask.getName());
			assertEquals(Task.getDescription(), foundTask.getDescription());
			assertEquals(Task.getStatus(), foundTask.getStatus());

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		LOG.info("END: testCreateSimpleTask()");
	}

	@Test
	public void testCreateTaggedTask() {
		LOG.info("BEGIN: testCreateTaggedTask()");
		try {

			final Task Task = createTask("Task2", "ABC", "DEF");
			LOG.info("Task = {}", Task);
			assertNotNull(Task);

			final Task createdTask = TaskService.createTask(Task);
			LOG.info("createdTask = {}", createdTask);
			assertNotNull(createdTask);
			assertNotNull(createdTask.getOid());
			assertNotNull(createdTask.getUuid());
			assertEquals(Task.getName(), createdTask.getName());
			assertEquals(Task.getDescription(), createdTask.getDescription());
			assertEquals(Task.getStatus(), createdTask.getStatus());

			final Task foundTask = TaskService.findTaskByName("Task2");
			assertNotNull(foundTask);
			assertNotNull(foundTask.getOid());
			assertNotNull(foundTask.getUuid());
			assertEquals(Task.getName(), foundTask.getName());
			assertEquals(Task.getDescription(), foundTask.getDescription());
			assertEquals(Task.getStatus(), foundTask.getStatus());

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		LOG.info("END: testCreateTaggedTask()");
	}

}
