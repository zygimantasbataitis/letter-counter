package lt.metasite.bl.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import lt.metasite.bl.dao.NewsEntryDao;
import lt.metasite.model.NewsEntry;
import lt.metasite.model.NewsEntry_;

@Repository
public class NewsEntryDaoImpl extends DaoImpl<NewsEntry, Long> implements NewsEntryDao {

	public NewsEntryDaoImpl() {
		super(NewsEntry.class);
	}

	@Override
	public List<NewsEntry> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<NewsEntry> criteriaQuery = builder.createQuery(NewsEntry.class);

		Root<NewsEntry> root = criteriaQuery.from(NewsEntry.class);
		criteriaQuery.orderBy(builder.desc(root.get(NewsEntry_.DATE)));

		TypedQuery<NewsEntry> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
