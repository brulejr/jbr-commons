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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test case for {@link Tag}.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class TagTest {

	private static final Logger LOG = LoggerFactory.getLogger(TagTest.class);

	@Test
	public void test() {
		LOG.info("BEGIN: test()");
		try {

			Tag tag = new Tag.Builder()
					.setOid(123L)
					.setName("ABC")
					.build();
			LOG.info("tag = {}", tag);
			assertEquals(
					"<Tag oid=\"123\" name=\"ABC\"/>",
					tag.toString());

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		LOG.info("END: test()");
	}

}
