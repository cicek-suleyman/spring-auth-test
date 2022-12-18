package pw.cicek.spring_tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pw.cicek.spring_tutorial.entity.AuthService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder, AuthService authService)
		throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
			.userDetailsService(authService)
			.passwordEncoder(passwordEncoder)
			.and()
			.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, RequestFilter requestFilter) throws Exception {
		return http.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/auth/token")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
