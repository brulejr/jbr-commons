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
package org.jbr.commons.container.http;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;

/**
 * Provides an embeddable Jetty web server.
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public abstract class HttpServerSupport implements Lifecycle, ApplicationContextAware {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private Server server;
	private QueuedThreadPool threadPool;

	private int port = 8080;
	private int maxThreads = 1;
	private int minThreads = 1;

	private int requestBufferSize = 1024 * 16;

	private int responseBufferSize;
	private String name;
	private String accessLogDirectory;

	private ApplicationContext applicationContext;

	protected abstract void configureApplication(final Server server)
			throws Exception;

	protected Connector configureConnector(final QueuedThreadPool threadPool) {
		final SelectChannelConnector connector = new SelectChannelConnector();
		connector.setName(getName());
		connector.setPort(port);

		connector.setThreadPool(threadPool);

		if (requestBufferSize > 0) {
			connector.setRequestBufferSize(requestBufferSize);
		}
		if (responseBufferSize > 0) {
			connector.setResponseBufferSize(responseBufferSize);
		}

		return connector;
	}

	protected QueuedThreadPool configuredThreadPool() {
		threadPool = new QueuedThreadPool(maxThreads);
		threadPool.setMinThreads(minThreads);
		threadPool.setName(getName());
		return threadPool;
	}

	protected RequestLog createRequestLog() {
		final NCSARequestLog _log = new NCSARequestLog();
		final File _logPath = new File(getAccessLogDirectory()
				+ "yyyy_mm_dd.request.log");
		_logPath.getParentFile().mkdirs();
		_log.setFilename(_logPath.getPath());
		_log.setRetainDays(30);
		_log.setExtended(false);
		_log.setAppend(true);
		_log.setLogTimeZone("UTC");
		_log.setLogLatency(true);
		return _log;
	}

	@PreDestroy
	public void destroy() {
		log.info("Destroying Jetty HTTPServer");
		stop();
		server.destroy();
	}

	protected String getAccessLogDirectory() {
		return accessLogDirectory;
	}

	protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@ManagedAttribute(description = "The number of idle Jetty HTTP server threads")
	public int getIdleThreads() {
		return threadPool.getIdleThreads();
	}

	@ManagedAttribute(description = "The maximum thread idle time")
	public int getMaxIdleTimeMs() {
		return threadPool.getMaxIdleTimeMs();
	}

	@ManagedAttribute(description = "The maximum job queue size")
	public int getMaxQueued() {
		return threadPool.getMaxQueued();
	}

	@ManagedAttribute(description = "The maximum number of threads allocated to this Jetty HTTP server")
	public int getMaxThreads() {
		return maxThreads;
	}

	@ManagedAttribute(description = "The minimum number of threads allocated to this Jetty HTTP server")
	public int getMinThreads() {
		return minThreads;
	}

	@ManagedAttribute(description = "The name given to this Jetty HTTP server")
	public String getName() {
		return name;
	}

	@ManagedAttribute(description = "The TCP/IP port on which this Jetty HTTP server listens")
	public int getPort() {
		return port;
	}

	@ManagedAttribute
	public int getRequestBufferSize() {
		return requestBufferSize;
	}

	@ManagedAttribute
	public int getResponseBufferSize() {
		return responseBufferSize;
	}

	@ManagedAttribute(description = "Returns max threads")
	public int getThreadPoolMaxThreads() {
		return threadPool.getMaxThreads();
	}

	@ManagedAttribute(description = "Returns min threads")
	public int getThreadPoolMinThreads() {
		return threadPool.getMinThreads();
	}

	@PostConstruct
	public void init() throws Exception {

		log.info("Initalizing HTTPServer");

		// initialize core server
		server = new Server();
		threadPool = configuredThreadPool();
		server.setConnectors(new Connector[] { configureConnector(threadPool) });
		server.setThreadPool(threadPool);

		// setup the application
		configureApplication(server);

		// start web server
		start();

	}

	@Override
	@ManagedAttribute(description = "Returns true if the HTTP server is running")
	public boolean isRunning() {
		return server.isRunning();
	}

	public void setAccessLogDirectory(final String accessLogDirectory) {
		this.accessLogDirectory = accessLogDirectory;
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setMaxThreads(final int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public void setMinThreads(final int minThreads) {
		this.minThreads = minThreads;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPort(final int port) {
		if (log.isDebugEnabled()) {
			log.debug("Setting HttpServer port to [" + port + "]");
		}
		this.port = port;
	}

	public void setRequestBufferSize(final int requestBufferSize) {
		this.requestBufferSize = requestBufferSize;
	}

	public void setResponseBufferSize(final int responseBufferSize) {
		this.responseBufferSize = responseBufferSize;
	}

	@Override
	@ManagedOperation(description = "Starts the HTTP server")
	public void start() {
		if (!isRunning()) {
			try {
				log.info("Starting the '{}' Jetty HTTP server...", getName());
				server.start();
			} catch (final Exception e) {
				throw new HttpServerException(e);
			}
		}
	}

	@Override
	@ManagedOperation(description = "Stops the HTTP server")
	public void stop() {
		if (isRunning()) {
			try {
				log.info("Stopping the '{}' Jetty HTTP server...", getName());
				server.stop();
			} catch (final Exception e) {
				throw new HttpServerException(e);
			}
		}

	}

}
