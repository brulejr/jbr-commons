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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jbr.taskmgr.model.TaskStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test case for {@link Task}.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class TaskTest {

	private static final Logger LOG = LoggerFactory.getLogger(TaskTest.class);

	@Test
	public void test() {
		LOG.info("BEGIN: test()");
		try {

			Task task = new Task.Builder()
					.setOid(123L)
					.setName("ABC")
					.setDescription("This is a test")
					.setStatus(TaskStatus.CREATED)
					.build();
			LOG.info("task = {}", task);
			assertEquals(
					"<Task oid=\"123\" name=\"ABC\" status=\"CREATED\"><description>This is a test</description></Task>",
					task.toString());

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		LOG.info("END: test()");
	}

}
