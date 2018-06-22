package lt.metasite.bl.helper.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lt.metasite.bl.dao.UserDao;
import lt.metasite.bl.helper.UserHelper;
import lt.metasite.model.User;

@Component
public class UserHelperImpl implements UserHelper {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDao userDao;

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return (User) authentication.getPrincipal();
		}
		return null;
	}

	public boolean isCurrentUserAdmin() {
		return getCurrentUser() != null && getCurrentUser().isAdmin();
	}

	public boolean isCurrentUserGuest() {
		return getCurrentUser() != null && getCurrentUser().isGuest();
	}

	public Map<String, Boolean> createRoleMap(UserDetails userDetails) {
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

	@Override
	public UserDetails authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload user as password of authentication principal will be null after
		// authorization and password is needed for token generation
		return userDao.loadUserByUsername(username);
	}
}
