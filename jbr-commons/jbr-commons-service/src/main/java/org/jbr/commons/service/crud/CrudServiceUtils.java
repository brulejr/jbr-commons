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
package org.jbr.commons.service.crud;

import java.util.List;
import java.util.UUID;

import org.jbr.commons.service.DuplicateEntityException;
import org.jbr.commons.service.EntityServiceException;
import org.jbr.commons.service.NullEntityException;
import org.jbr.commons.service.ServiceUtilsSupport;
import org.jbr.commons.service.event.CountEntityFailureEvent;
import org.jbr.commons.service.event.CountEntitySuccessEvent;
import org.jbr.commons.service.event.CreateEntityFailureEvent;
import org.jbr.commons.service.event.CreateEntitySuccessEvent;
import org.jbr.commons.service.event.DeleteAllEntitiesFailureEvent;
import org.jbr.commons.service.event.DeleteAllEntitiesSuccessEvent;
import org.jbr.commons.service.event.DeleteEntityFailureEvent;
import org.jbr.commons.service.event.DeleteEntitySuccessEvent;
import org.jbr.commons.service.event.EntityServiceFailureEvent.ErrorType;
import org.jbr.commons.service.event.FindEntityFailureEvent;
import org.jbr.commons.service.event.FindEntitySuccessEvent;
import org.jbr.commons.service.event.RetrieveEntitiesFailureEvent;
import org.jbr.commons.service.event.RetrieveEntitiesSuccessEvent;
import org.jbr.commons.service.event.UpdateEntityFailureEvent;
import org.jbr.commons.service.event.UpdateEntitySuccessEvent;
import org.springframework.dao.DataIntegrityViolationException;

import com.google.common.eventbus.EventBus;

/**
 * TODO - type header
 * 
 * @author <a href="mailto:brulejr@gmail.com">Jon Brule</a>
 */
public class CrudServiceUtils<B, I, N> extends ServiceUtilsSupport {

	public interface CountCallback<B> {
		public long count(B criteria) throws EntityServiceException;
	}

	public interface CreateCallback<B> {
		public void create(B bean) throws EntityServiceException;
	}

	public interface DeleteAllCallback<B> {
		public void deleteAll() throws EntityServiceException;
	}

	public interface DeleteByIdCallback<I> {
		public void delete(I beanId) throws EntityServiceException;
	}

	public interface DeleteByUuidCallback {
		public void delete(UUID uuid) throws EntityServiceException;
	}

	public interface DeleteCallback<B> {
		public void delete(B bean) throws EntityServiceException;
	}

	public interface FindByIdCallback<B, I> {
		public B findById(I id) throws EntityServiceException;
	}

	public interface FindByNameCallback<E, N> {
		public E findByName(N name) throws EntityServiceException;
	}

	public interface FindByUUIDCallback<E> {
		public E findByUUID(UUID uuid) throws EntityServiceException;
	}

	public interface RetrieveCallback<B> {
		public List<B> retrieve(B criteria) throws EntityServiceException;
	}

	public interface UpdateCallback<B> {
		public B update(B bean) throws EntityServiceException;
	}

	public long countEntities(
			final Class<B> beanClass,
			final B criteria,
			final CountCallback<B> callback,
			final EventBus eventBus
			) throws EntityServiceException {

		try {

			final long count = callback.count(criteria);
			postEvent(new CountEntitySuccessEvent<B, I, N>(criteria), eventBus);
			return count;

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			postEvent(new CountEntityFailureEvent<B, I, N>(criteria), eventBus);
			throw new EntityServiceException(
					"Unable to count " + beanClass.getSimpleName() + " due to an exception!", criteria, e);
		}
	}

	public B createEntity(
			final Class<B> beanClass,
			final B bean,
			final CreateCallback<B> callback,
			final EventBus eventBus
			) throws DuplicateEntityException, EntityServiceException {

		try {

			if (bean == null) {
				throw new NullEntityException(beanClass.getSimpleName() + " is null!");
			}
			callback.create(bean);
			postEvent(new CreateEntitySuccessEvent<B, I, N>(bean), eventBus);
			return bean;

		} catch (final DataIntegrityViolationException e) {
			postEvent(new CreateEntityFailureEvent<B, I, N>(bean, ErrorType.DUPLICATE), eventBus);
			throw new DuplicateEntityException(beanClass.getSimpleName() + "  is a duplicate!", bean);
		} catch (final NullEntityException e) {
			postEvent(new CreateEntityFailureEvent<B, I, N>(bean, ErrorType.NULL), eventBus);
			throw e;
		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			postEvent(new CreateEntityFailureEvent<B, I, N>(bean), eventBus);
			throw new EntityServiceException(
					"Unable to create " + beanClass.getSimpleName() + " due to an exception!", bean, e);
		}
	}

	public void deleteAllEntities(
			final Class<B> beanClass,
			final DeleteAllCallback<B> callback,
			final EventBus eventBus
			) throws EntityServiceException {
		try {

			callback.deleteAll();
			postEvent(new DeleteAllEntitiesSuccessEvent<B, I, N>(), eventBus);

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			postEvent(new DeleteAllEntitiesFailureEvent<B, I, N>(), eventBus);
			throw new EntityServiceException(
					"Unable to delete all " + beanClass.getSimpleName() + " due to an exception!", e);
		}
	}

	public void deleteEntity(
			final Class<B> beanClass,
			final I beanId,
			final DeleteByIdCallback<I> callback,
			final EventBus eventBus
			) throws EntityServiceException {
		try {

			callback.delete(beanId);
			postEvent(new DeleteEntitySuccessEvent<B, I, N>(beanId), eventBus);

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			postEvent(new DeleteEntityFailureEvent<B, I, N>(beanId), eventBus);
			throw new EntityServiceException(
					"Unable to delete " + beanClass.getSimpleName() + " due to an exception!", beanId, e);
		}
	}

	public void deleteEntity(
			final Class<B> beanClass,
			final UUID uuid,
			final DeleteByUuidCallback callback,
			final EventBus eventBus
			) throws EntityServiceException {
		try {

			callback.delete(uuid);
			postEvent(new DeleteEntitySuccessEvent<B, I, N>(uuid), eventBus);

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			postEvent(new DeleteEntityFailureEvent<B, I, N>(uuid), eventBus);
			throw new EntityServiceException(
					"Unable to delete " + beanClass.getSimpleName() + " due to an exception!", uuid, e);
		}
	}

	public B findById(
			final Class<B> beanClass,
			final I entityId,
			final FindByIdCallback<B, I> callback,
			final EventBus eventBus
			) throws EntityServiceException {
		try {

			final B bean = callback.findById(entityId);
			final FindEntitySuccessEvent<B, I, N> event = new FindEntitySuccessEvent<>();
			event.setEntityID(entityId);
			postEvent(event, eventBus);
			return bean;

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			final FindEntityFailureEvent<B, I, N> event = new FindEntityFailureEvent<>();
			event.setEntityID(entityId);
			postEvent(event, eventBus);
			throw new EntityServiceException(
					"Unable to find " + beanClass.getSimpleName() + " due to an exception!", entityId, e);
		}
	}

	public B findByName(
			final Class<B> beanClass,
			final N entityName,
			final FindByNameCallback<B, N> callback,
			final EventBus eventBus
			) throws EntityServiceException {
		try {

			final B bean = callback.findByName(entityName);
			final FindEntitySuccessEvent<B, I, N> event = new FindEntitySuccessEvent<>();
			event.setEntityName(entityName);
			postEvent(event, eventBus);
			return bean;

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			final FindEntityFailureEvent<B, I, N> event = new FindEntityFailureEvent<>();
			event.setEntityName(entityName);
			postEvent(event, eventBus);
			throw new EntityServiceException(
					"Unable to find " + beanClass.getSimpleName() + " due to an exception!", entityName, e);
		}
	}

	public B findByUUID(
			final Class<B> beanClass,
			final UUID entityUUID,
			final FindByUUIDCallback<B> callback,
			final EventBus eventBus
			) throws EntityServiceException {

		try {

			final B bean = callback.findByUUID(entityUUID);
			final FindEntitySuccessEvent<B, I, N> event = new FindEntitySuccessEvent<>();
			event.setEntityUUID(entityUUID);
			postEvent(event, eventBus);
			return bean;

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			final FindEntityFailureEvent<B, I, N> event = new FindEntityFailureEvent<>();
			event.setEntityUUID(entityUUID);
			postEvent(event, eventBus);
			throw new EntityServiceException(
					"Unable to find " + beanClass.getSimpleName() + " due to an exception!", entityUUID, e);
		}
	}

	protected B makeBeanInstance(final Class<B> beanClass) {
		try {
			return beanClass.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public List<B> retrieveEntities(
			final Class<B> beanClass,
			final B criteria,
			final RetrieveCallback<B> callback,
			final EventBus eventBus
			) throws EntityServiceException {
		try {

			final List<B> results = callback.retrieve(criteria);
			postEvent(new RetrieveEntitiesSuccessEvent<B, I, N>(criteria), eventBus);
			return results;

		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			postEvent(new RetrieveEntitiesFailureEvent<B, I, N>(criteria), eventBus);
			throw new EntityServiceException(
					"Unable to retrieive " + beanClass.getSimpleName() + "s due to an exception!", criteria, e);
		}
	}

	public B updateEntity(
			final Class<B> beanClass,
			final B bean,
			final UpdateCallback<B> callback,
			final EventBus eventBus
			) throws EntityServiceException {
		try {

			// ensure the given bean is not null
			if (bean == null) {
				throw new NullEntityException(beanClass.getSimpleName() + " instance is null!");
			}

			// update the bean
			final B result = callback.update(bean);
			postEvent(new UpdateEntitySuccessEvent<B, I, N>(bean), eventBus);
			return result;

		} catch (final NullEntityException e) {
			postEvent(new UpdateEntityFailureEvent<B, I, N>(bean, ErrorType.NULL), eventBus);
			throw e;
		} catch (final EntityServiceException e) {
			throw e;
		} catch (final Exception e) {
			postEvent(new UpdateEntityFailureEvent<B, I, N>(bean), eventBus);
			throw new EntityServiceException(
					"Unable to update " + beanClass.getSimpleName() + " due to an exception!", bean, e);
		}
	}

}
