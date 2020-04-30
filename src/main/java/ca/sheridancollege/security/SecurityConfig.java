package ca.sheridancollege.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired LoginAccessDeniedHandler loginAccessDeniedHandler;
	@Autowired @Lazy UserDetailsServiceImp userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			
		.antMatchers(HttpMethod.GET, "/signup").permitAll()
		.antMatchers(HttpMethod.POST, "/processSignUp").permitAll()

		.antMatchers("/administrator", "/account", "/employee").authenticated()
		
		.antMatchers("/administrator").hasRole("ADMIN")
		.antMatchers("/searchUserAccountA").hasRole("ADMIN")
		.antMatchers("/searchEmployeeA").hasRole("ADMIN")
		
		.antMatchers("/employee").hasRole("EMPLOYEE")
		.antMatchers("/account").hasRole("USER")
		
		.antMatchers("/user/**",
					 "/admin/**", 
					 "/employee/**").authenticated()
			
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/employee/**").hasAnyRole("EMPLOYEE", "ADMIN")
	    .antMatchers("/user/**").hasRole("USER")
			
	    .antMatchers("/", "/css/**", "/js/**", "/img/**", "/font/**").permitAll().and()
		    
		.formLogin().loginPage("/login").permitAll().and()
		
		.logout()
		.invalidateHttpSession(true).clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout").permitAll().and()
		    
		.exceptionHandling().accessDeniedHandler(loginAccessDeniedHandler);
	}	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authorize) throws Exception {
		authorize.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
