package com.bid.smc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

/*	@Autowired 
    private UserDetailsService userDetailsService;  
	
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth)
	            throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }*/

	/* @Bean
	 public Md5PasswordEncoder passwordEncoder() throws Exception {
	   return new Md5PasswordEncoder();
	 }*/
	 
    @Override
      protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()   // only to test swagger
            .antMatchers(HttpMethod.POST,"/multipleSave").permitAll()  // only to test file upload from UI /savefile
            .antMatchers("/index.jsp").permitAll()
            .antMatchers("/dist/**").permitAll()// only to test file upload from UI /savefile
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and()
            // We filter the api/login requests
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)
            // And filter other requests to check the presence of JWT in header
            .addFilterBefore(new JWTAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class);
      }
    
     @Bean
   	public BCryptPasswordEncoder passwordEncoder() {
   		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
   		return bCryptPasswordEncoder;
   	} 

}
