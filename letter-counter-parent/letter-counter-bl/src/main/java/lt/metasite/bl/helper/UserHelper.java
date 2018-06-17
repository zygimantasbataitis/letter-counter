package lt.metasite.bl.helper;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import lt.metasite.model.User;

public interface UserHelper {

	User getCurrentUser();

	boolean isCurrentUserAdmin();

	boolean isCurrentUserGuest();

	Map<String, Boolean> createRoleMap(UserDetails userDetails);

	UserDetails authenticate(String username, String password);
}
