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
package org.jbr.commons.container;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.jbr.commons.lang.xml.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Provides the basis for a Spring execution container.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public abstract class SpringContainerSupport implements SpringContainer {

	protected final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	private final CountDownLatch CONTAINER_SHUTDOWN = new CountDownLatch(1);
	private AbstractApplicationContext containerContext;
	private boolean started;

	protected abstract AbstractApplicationContext createApplicationContext() throws SpringContainerException;

	@Override
	public ApplicationContext getContainerContext() {
		return containerContext;
	}

	@Override
	public boolean isRunning() {
		return started;
	}

	protected void logApplicationContext() {
		final List<String> beanDefinitionNames = Arrays.asList(containerContext.getBeanDefinitionNames());
		Collections.sort(beanDefinitionNames);
		log.info("Application Context: " + containerContext.getDisplayName());
		log.info("Bean Definition Count = " + containerContext.getBeanDefinitionCount());
		final MessageFormat logMsgFormat = new MessageFormat("Bean : {0} : {1}");
		for (final String beanName : beanDefinitionNames) {
			if (log.isDebugEnabled()) {
				log.debug(MessageFormat.format("Bean : {0} :\n{1}", new Object[] {
						beanName,
						XmlUtils.toXml(containerContext.getBean(beanName))
				}));
			} else {
				log.info(logMsgFormat.format(new Object[] {
						beanName, containerContext.getType(beanName)
				}));
			}
		}
	}

	@Override
	public void shutdown() throws SpringContainerException {
		if (containerContext != null) {
			containerContext.close();
		}
		started = false;
		CONTAINER_SHUTDOWN.countDown();
	}

	@Override
	public void startup() throws SpringContainerException {
		try {
			containerContext = createApplicationContext();
			containerContext.refresh();

			logApplicationContext();

			started = true;
			CONTAINER_SHUTDOWN.await();
		} catch (final InterruptedException e) {
			throw new SpringContainerException("Spring container execution interrupted", e);
		} catch (final Exception e) {
			throw new SpringContainerException("Unexpected exception encountered starting container", e);
		}
	}

}