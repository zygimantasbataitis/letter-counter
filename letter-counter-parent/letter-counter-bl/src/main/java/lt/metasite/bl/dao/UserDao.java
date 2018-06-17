package lt.metasite.bl.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import lt.metasite.model.User;

public interface UserDao extends Dao<User, Long>, UserDetailsService {

	User findByUserName(String userName);

	List<User> findAllWithReadblePasses();
}