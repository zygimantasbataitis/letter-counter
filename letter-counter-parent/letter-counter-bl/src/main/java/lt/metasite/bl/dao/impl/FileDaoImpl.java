package lt.metasite.bl.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import lt.metasite.bl.dao.FileDao;
import lt.metasite.model.File;
import lt.metasite.model.File_;

@Repository
public class FileDaoImpl extends DaoImpl<File, Long> implements FileDao {

	public FileDaoImpl() {
		super(File.class);
	}

	@Override
	public List<File> findAll() {
		final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<File> criteriaQuery = builder.createQuery(File.class);

		Root<File> root = criteriaQuery.from(File.class);
		criteriaQuery.orderBy(builder.asc(root.get(File_.NAME)));

		TypedQuery<File> typedQuery = getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	@Override
	public void removeAll() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaDelete<File> criteriaQuery = builder.createCriteriaDelete(File.class);
		Root<File> root = criteriaQuery.from(entityClass);
		TypedQuery<File> typedQuery = (TypedQuery<File>) getEntityManager().createQuery(criteriaQuery);
		typedQuery.executeUpdate();
	}	

}
