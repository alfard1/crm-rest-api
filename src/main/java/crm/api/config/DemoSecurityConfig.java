package crm.api.config;

import crm.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to our security data source
    @Autowired
    private UserService userService;
	
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/api/customers/list/**").hasRole("EMPLOYEE")
				.antMatchers("/api/customers/new/**").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/api/customers/update/**").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/api/customers/delete/**").hasRole("ADMIN")
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	// # Disabled CSRF
	// Spring Security 5 has CSRF enabled by default. For API I need to send over CSRF tokens.
	// However, CSRF generally does not apply for REST APIs. CSRF protection is a request that could be processed
	// by a browser by normal users. Here we have only REST service that is used by non-browser clients.
	// More details: http://www.tothenew.com/blog/fortifying-your-rest-api-using-spring-security/

	// # Disabled sessions
	// To avoid using cookies for sesson tracking. This can force the REST client to enter user name and password
	// for each request (not always, but happens).
	// More details: http://www.baeldung.com/spring-security-session


	//bcrypt bean definition
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		return auth;
	}
}
