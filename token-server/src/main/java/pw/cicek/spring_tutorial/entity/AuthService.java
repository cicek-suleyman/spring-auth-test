package pw.cicek.spring_tutorial.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pw.cicek.spring_tutorial.config.JwtUtil;

import java.util.Collection;

@Service
public class AuthService implements UserDetailsService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PrivilegeRepository privilegeRepository;
	private final AuthTokenRepository authTokenRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	AuthService(UserRepository userRepository,
		    RoleRepository roleRepository,
		    PrivilegeRepository privilegeRepository,
		    AuthTokenRepository authTokenRepository,
		    JwtUtil jwtUtil,
		    PasswordEncoder passwordEncoder)
	{
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.privilegeRepository = privilegeRepository;
		this.authTokenRepository = authTokenRepository;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElse(null);
	}

	public User createUser(String username, String password) {
		return createUser(username, password, null);
	}

	public User createUser(String username, String password, Collection<Role> roles) {
		User user = new User(username, passwordEncoder.encode(password));
		if(roles != null)
			user.setAuthorities(roles);
		return userRepository.save(user);
	}

	public User findUserByUsername(String username) {
		return (User) loadUserByUsername(username);
	}

	public Role findRoleByName(String name) {
		return this.roleRepository.findByAuthority(name).orElse(null);
	}

	public Privilege findPrivilegeByName(String name) {
		return this.privilegeRepository.findByName(name).orElse(null);
	}

	public void save(Privilege privilege) {
		this.privilegeRepository.save(privilege);
	}
	public void save(User user) {
		this.userRepository.save(user);
	}
	public void save(Role role) {
		this.roleRepository.save(role);
	}

	public AuthToken generateToken(User user) {
		String j_token = jwtUtil.generateToken(user);
		AuthToken token = new AuthToken(j_token, user);
		return authTokenRepository.save(token);
	}

	public boolean verifyToken(String token) {
		AuthToken authToken = authTokenRepository.findByToken(token).orElse(null);
		String username = jwtUtil.extractUsername(token);
		User user = findUserByUsername(username);

		if(authToken == null || user == null)
			return false;

		return user.getUsername().equals(authToken.getUser().getUsername());
	}

	public Collection<AuthToken> findTokens() {
		return this.authTokenRepository.findAll();
	}

	public Collection<AuthToken> findTokenByUser(String username) {
		User user = userRepository.findByUsername(username).orElse(null);
		if(user == null)
			return null;

		return this.authTokenRepository.findByUser(user);
	}

	public AuthToken findToken(String token) {
		return this.authTokenRepository.findByToken(token).orElse(null);
	}

	public void deleteToken(@NotNull AuthToken token) {
		this.authTokenRepository.delete(token);
	}

	public void deleteToken(@NotNull String token) {
		this.deleteToken(this.authTokenRepository.findByToken(token).orElse(null));
	}

}
