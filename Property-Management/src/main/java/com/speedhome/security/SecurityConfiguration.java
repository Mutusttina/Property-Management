
package com.speedhome.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	@Autowired
	MyEntryPoint entryPoint;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		 return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(getPasswordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.headers().frameOptions().sameOrigin();

			http.csrf().disable().authorizeRequests()
				.antMatchers("/user/authenticate").permitAll()
				.antMatchers(HttpMethod.POST,"/user").permitAll()
				//.antMatchers(HttpMethod.POST,"/users").hasAnyAuthority("ADMIN","LANDLORD")
				.antMatchers(HttpMethod.POST,"/property").hasAnyAuthority("ADMIN","LANDLORD")
				.antMatchers(HttpMethod.PUT,"/property").hasAnyAuthority("ADMIN","LANDLORD")
				.antMatchers("/property/_approve","/property/_approve/**").hasAnyAuthority("ADMIN")
				.antMatchers("/property/_search","/property/_search").hasAnyAuthority("ADMIN","LANDLORD","USER")
				.antMatchers("/property/hello").hasAuthority("ADMIN")
					.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().exceptionHandling().authenticationEntryPoint(entryPoint);


	 	http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);

	}


}
