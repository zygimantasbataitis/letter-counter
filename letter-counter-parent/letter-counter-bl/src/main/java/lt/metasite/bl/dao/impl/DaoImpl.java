package lt.metasite.bl.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import lt.metasite.bl.dao.Dao;
import lt.metasite.model.interfaces.IEntity;

@Transactional
public class DaoImpl<T extends IEntity, I> implements Dao<T, I> {

	private EntityManager entityManager;

	protected Class<T> entityClass;

	public DaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<T> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> criteriaQuery = builder.createQuery(this.entityClass);

		criteriaQuery.from(this.entityClass);

		TypedQuery<T> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public T find(I id) {
		return this.getEntityManager().find(this.entityClass, id);
	}

	@Override
	public T save(T entity) {
		if (entity.getId() != null) {
			getEntityManager().merge(entity);
			getEntityManager().flush();
		} else {
			getEntityManager().persist(entity);
		}
		return entity;
	}

	@Override
	public void delete(I id) {
		if (id == null) {
			return;
		}

		T entity = this.find(id);
		if (entity == null) {
			return;
		}

		this.getEntityManager().remove(entity);
	}

}