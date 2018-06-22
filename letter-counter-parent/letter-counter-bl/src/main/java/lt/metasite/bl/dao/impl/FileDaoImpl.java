package lt.metasite.bl.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import lt.metasite.bl.dao.FileDao;
import lt.metasite.bl.utils.PersistenceUtils;
import lt.metasite.model.File;
import lt.metasite.model.File_;
import lt.metasite.model.User;
import lt.metasite.model.User_;

@Repository
public class FileDaoImpl extends DaoImpl<File, Long> implements FileDao {

	public FileDaoImpl() {
		super(File.class);
	}

	@Override
	public List<File> findAll(User user) {
		final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<File> criteriaQuery = builder.createQuery(File.class);

		ParameterExpression<Long> userIdParam = builder.parameter(Long.class);
		
		Root<File> root = criteriaQuery.from(entityClass);
		Path<Long> pathUser = root.get(File_.USER).get(User_.ID);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (user != null) {
			predicates.add(builder.equal(pathUser, userIdParam));	
		} else {
			predicates.add(builder.isNull(pathUser));
		}
		
		if (!predicates.isEmpty()) {
			criteriaQuery.where(PersistenceUtils.toArray(predicates));	
		}
		criteriaQuery.orderBy(builder.asc(root.get(File_.NAME)));

		TypedQuery<File> typedQuery = getEntityManager().createQuery(criteriaQuery);
		if (user != null) {
			typedQuery.setParameter(userIdParam, user.getId());
		}
		return typedQuery.getResultList();
	}
	
	@Override
	public void removeAll(User user) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaDelete<File> criteriaQuery = builder.createCriteriaDelete(File.class);
			
		Root<File> root = criteriaQuery.from(entityClass);
		Path<Long> pathUser = root.get(File_.USER).get(User_.ID);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (user != null) {
			predicates.add(builder.equal(pathUser, user.getId()));	
		} else {
			predicates.add(builder.isNull(pathUser));
		}
		criteriaQuery.where(PersistenceUtils.toArray(predicates));		
		getEntityManager().createQuery(criteriaQuery).executeUpdate();
	}	

}
