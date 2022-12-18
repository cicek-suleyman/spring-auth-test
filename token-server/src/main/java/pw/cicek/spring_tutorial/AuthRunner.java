package pw.cicek.spring_tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pw.cicek.spring_tutorial.entity.AuthService;
import pw.cicek.spring_tutorial.entity.Privilege;
import pw.cicek.spring_tutorial.entity.Role;
import pw.cicek.spring_tutorial.entity.User;

import java.util.Collections;

@Component
public class AuthRunner implements ApplicationRunner {
	private final AuthService authService;

	@Autowired
	public AuthRunner(AuthService authService) {
		this.authService = authService;
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user = authService.findUserByUsername(Main.username);
		Role role = authService.findRoleByName("ROOT");
		Privilege privilege = authService.findPrivilegeByName("ALL");

		if(privilege == null) {
			privilege = new Privilege("ALL");
			authService.save(privilege);
		}

		if(role == null) {
			role = new Role("ROOT");
			role.addPrivilege(privilege);
			authService.save(role);
		}

		if(user == null) {
			user = authService.createUser(Main.username,Main.password, Collections.singleton(role));
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setAccountNonExpired(true);;
			user.setEnabled(true);
			authService.save(user);
		}
	}
}
