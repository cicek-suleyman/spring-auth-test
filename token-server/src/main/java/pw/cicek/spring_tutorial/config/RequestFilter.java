package pw.cicek.spring_tutorial.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pw.cicek.spring_tutorial.entity.AuthService;

import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final AuthService authService;

	public RequestFilter(JwtUtil jwtUtil, AuthService authService) {
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException
	{
		final String authHeader = request.getHeader("Authorization");
		String jwt = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.replace("Bearer ", "");
			username = jwtUtil.extractUsername(jwt);
		}

		if (username != null && authService.getAuthentication() == null) {
			UserDetails userDetails = authService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
