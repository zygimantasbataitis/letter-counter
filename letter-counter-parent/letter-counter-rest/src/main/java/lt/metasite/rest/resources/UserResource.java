package lt.metasite.rest.resources;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lt.metasite.bl.dao.UserDao;
import lt.metasite.bl.helper.UserHelper;
import lt.metasite.bl.security.TokenTransfer;
import lt.metasite.bl.security.TokenUtils;
import lt.metasite.bl.security.UserTransfer;
import lt.metasite.model.User;
import lt.metasite.model.enums.DocumentIdType;
import lt.metasite.rest.utils.RestPaths;

@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserHelper userHelper;

	private User user;

	@GetMapping(RestPaths.ID_PATH)
	public User read(@PathVariable(RestPaths.ID) Long id) {
		return userDao.find(id);
	}

	@PostMapping
	public User store(@RequestBody @Valid User user) {
		return userDao.save(user);
	}

	@DeleteMapping(RestPaths.ID_PATH)
	public void delete(@PathVariable(RestPaths.ID) Long id) {
		userDao.delete(id);
	}

	@GetMapping
	public List<User> list() {
		return userDao.findAllWithReadblePasses();
	}

	/**
	 * Retrieves the currently logged in user.
	 * 
	 * @return A transfer containing the username and the roles.
	 */
	@GetMapping("/detail")
	public UserTransfer getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		return new UserTransfer(userHelper.getCurrentUser().getSummary(), userHelper.createRoleMap(userDetails));
	}

	/**
	 * Authenticates a user and creates an authentication token.
	 * 
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return A transfer containing the authentication token.
	 */
	@PostMapping("/authenticate")
	public TokenTransfer authenticate(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		return new TokenTransfer(TokenUtils.createToken(userHelper.authenticate(username, password)));
	}

	@PostMapping("/register")
	public TokenTransfer register(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) {

		user = userDao.findByUserName(username);
		if (user == null) {
			userDao.save(new User(passwordEncoder.encode(password), firstname, lastname, username));
		}

		return new TokenTransfer(TokenUtils.createToken(userHelper.authenticate(username, password)));
	}

	@GetMapping("/documentIdTypes")
	public List<DocumentIdType> materialTypes() {
		return Arrays.asList(DocumentIdType.values());
	}

}