package pw.cicek.spring_tutorial.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pw.cicek.spring_tutorial.entity.AuthService;
import pw.cicek.spring_tutorial.entity.User;

import java.util.Map;

@RestController
@RequestMapping(value = "auth", method = RequestMethod.POST)
public class AuthAPI {
	private final AuthService authService;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthAPI(AuthService authService, AuthenticationManager authenticationManager) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
	}

	@RequestMapping(value = "token")
	public ResponseEntity<?> login(@RequestBody Map<String, Object> request) {
		if(!request.containsKey("username") || !request.containsKey("password"))
			return ResponseEntity.badRequest().build();

		Authentication auth = null;
		try {
			auth = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(request.get("username"), request.get("password")));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(authService.generateToken((User)auth.getPrincipal()).getToken());
	}

	@RequestMapping(value = "/")
	public ResponseEntity<?>  home(HttpServletRequest request) {
		User user = (User)authService.getAuthentication().getPrincipal();
		return ResponseEntity.ok(authService.findUserByUsername(user.getUsername()));
	}
}
