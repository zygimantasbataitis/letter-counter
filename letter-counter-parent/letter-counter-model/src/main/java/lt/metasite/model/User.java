package lt.metasite.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lt.metasite.model.enums.DocumentIdType;
import lt.metasite.model.enums.RoleType;
import lt.metasite.model.interfaces.IEntity;
import lt.metasite.model.utils.Consts;
import lt.metasite.model.utils.LConst;

@Entity
@Table(name = "user", schema = "public")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements IEntity, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "user_name", length = LConst.DEFAULT)
	private String userName;	

	@Column(length = LConst.DEFAULT)
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles = new HashSet<String>();

	@Column(name = "first_name", length = LConst.DEFAULT)
	private String firstName;

	@Column(name = "last_name", length = LConst.DEFAULT)
	private String lastName;

	@Column(name = "email", length = LConst.DEFAULT)
	private String email;

	@Column(name = "contacts", length = LConst.DEFAULT)
	private String contacts;

	@Column(name = "document_id", length = LConst.L50)
	@Enumerated(EnumType.STRING)
	private DocumentIdType documentId;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	@Transient
	private boolean enabled;
	
	@Transient
	private String displayPassword;
	
	@Transient
	private boolean accountNonExpired;
	
	@Transient
	private boolean accountNonLocked;
	
	@Transient
	private boolean credentialsNonExpired;
	
	@Transient
	private boolean guest;
	
	@Transient
	private boolean admin;	
	
	public User() {
	}

	public User(String email, String passwordHash) {
		this.email = email;
		this.password = passwordHash;
	}

	public User(String password, String firstname, String lastname, String username) {
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = username;
		this.userRoles.add(new UserRole(this));
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<String> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void addRole(String role) {
		this.roles.add(role);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (CollectionUtils.isEmpty(getUserRoles())) {
			return Collections.emptyList();
		}
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		getUserRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRoleType().toString())));

		return authorities;
	}

	@Override
	public String getUsername() {
		return getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public DocumentIdType getDocumentId() {
		return documentId;
	}

	public void setDocumentId(DocumentIdType documentId) {
		this.documentId = documentId;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getDisplayPassword() {
		return displayPassword;
	}

	public void setDisplayPassword(String displayPassword) {
		this.displayPassword = displayPassword;
	}
	
	public boolean isGuest(){
		return getUserRoles().stream().anyMatch(r -> r.getRoleType().equals(RoleType.GUEST));
	}
	
	public boolean isAdmin(){
		return getUserRoles().stream().anyMatch(r -> r.getRoleType().equals(RoleType.ADMIN));
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getSummary() {
		return getFirstName() + Consts.SPACE + getLastName();
		
	}

}