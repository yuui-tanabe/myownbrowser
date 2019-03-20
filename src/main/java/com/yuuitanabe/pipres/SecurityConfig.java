package com.yuuitanabe.pipres;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	    /*
	     * { @inheritDoc }
	     */
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests().anyRequest().authenticated();
	        http.addFilterAfter(new CSRFCookieFilter(), CsrfFilter.class)
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                    .and().logout().logoutSuccessUrl("/").permitAll();
            http.authorizeRequests().antMatchers("/error").permitAll();
	    }
}
