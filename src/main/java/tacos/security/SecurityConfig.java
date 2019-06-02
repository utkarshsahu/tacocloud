package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//PasswordEncoder enc = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// The order of the rules matter. Authentication to the /design and /orders come higher in the
		// precedence order while authenticating requests
		
		// access() method in antMatchers() allows you to write richer security rules in Spring Expression
		// Language. Allows for IP address matching, anonymous users, remember me protocols
		http
			.authorizeRequests()
				.antMatchers("/design", "/orders")
					.hasRole("USER")
				.antMatchers("/","/**").permitAll()
			.and()
				.logout()
					.logoutSuccessUrl("/")
			.and()
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/design");
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}
