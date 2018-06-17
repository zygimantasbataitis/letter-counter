package lt.metasite.bl.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lt.metasite.bl.dao.UserDao;
import lt.metasite.model.User;
import lt.metasite.model.User_;

@Repository
@Component(value = "userDetailService")
public class UserDaoImpl extends DaoImpl<User, Long> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDetails user = findByUserName(email);
		if (null == user) {
			throw new UsernameNotFoundException("The user with name " + email + " was not found");
		}

		return user;
	}

	@Override
	public User findByUserName(String userName) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> userNamePath = root.get(User_.USER_NAME);
		criteriaQuery.where(builder.equal(userNamePath, userName));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}
	
	@Override
	public List<User> findAllWithReadblePasses() {
		List<User> users = findAll();
		users.forEach(u -> u.setDisplayPassword(u.getPassword()));
		return users;
	}

}
