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

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Manages a Spring Container.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
@Component
@ManagedResource
public class SpringContainerManager implements Lifecycle {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	@Resource
	private ApplicationContext context;

	private Calendar startTimestamp;
	private Calendar stopTimestamp;

	@ManagedAttribute(description = "When the Spring Container was last started")
	public Date getStartTimestamp() {
		return (startTimestamp != null) ? startTimestamp.getTime() : null;
	}

	@ManagedAttribute(description = "When the Spring Container was last stopped")
	public Date getStopTimestamp() {
		return (stopTimestamp != null) ? stopTimestamp.getTime() : null;
	}

	@PostConstruct
	public void init() {

	}

	@Override
	@ManagedAttribute(description = "Returns true if the Spring ApplicationContext is running")
	public boolean isRunning() {
		return ((AbstractApplicationContext) context).isRunning();
	}

	@Override
	public void start() {
		log.info("Starting {}", getClass().getSimpleName());
		startTimestamp = Calendar.getInstance();
	}

	@ManagedOperation(description = "Start the SpringContainer if it is not already running.")
	public void startContainer() {
		if (log.isInfoEnabled()) {
			log.info("Starting SpringContainer...");
		}

		final AbstractApplicationContext ctx = (AbstractApplicationContext) context;
		if (!ctx.isRunning()) {
			ctx.start();
			startTimestamp = Calendar.getInstance();
			if (log.isInfoEnabled()) {
				log.info("SpringContainer started !!!");
			}
		} else {
			if (log.isInfoEnabled()) {
				log.info("SpringContainer already running !!!");
			}
		}
	}

	@Override
	public void stop() {
		log.info("Stopping {}", getClass().getSimpleName());
	}

	@ManagedOperation(description = "Stop the SpringContainer, if it is running.")
	public void stopContainer() {
		if (log.isInfoEnabled()) {
			log.info("Stopping SpringContainer...");
		}
		final AbstractApplicationContext ctx = (AbstractApplicationContext) context;
		if (ctx.isRunning()) {
			ctx.stop();
			stopTimestamp = Calendar.getInstance();
			if (log.isInfoEnabled()) {
				log.info("SpringContainer stopped !!!");
			}
		} else {
			if (log.isInfoEnabled()) {
				log.info("SpringContainer already stopped !!!");
			}
		}
	}

	@ManagedOperation(description = "Shutdown the application - terminates the JVM")
	public void shutdown() {
		if (log.isInfoEnabled()) {
			log.info("Closing SpringContainer...");
		}

		((AbstractApplicationContext) context).close();
		context = null;

		if (log.isInfoEnabled()) {
			log.info("Closed SpringContainer - Terminating JVM!!!");
		}
		System.exit(0);
	}
	
}
