package org.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
	
	
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	 
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.inMemoryAuthentication().//
					withUser("test").password("test").roles("USER").and().//
					withUser("admin").password("admin").roles("USER", "ADMIN");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.httpBasic().and().authorizeRequests().//
					antMatchers(HttpMethod.POST, "/emp").hasRole("ADMIN").//
					antMatchers(HttpMethod.DELETE, "/emp/").hasRole("ADMIN").//
					antMatchers(HttpMethod.PUT, "/emp/**").hasRole("ADMIN").
					antMatchers(HttpMethod.POST, "/dept").hasRole("ADMIN").//
					antMatchers(HttpMethod.DELETE, "/dept/").hasRole("ADMIN").//
					antMatchers(HttpMethod.PUT, "/dept/**").hasRole("ADMIN").and().//
					csrf().disable();
		}
		
	}
	

}