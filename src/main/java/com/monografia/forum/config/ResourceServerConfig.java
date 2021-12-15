package com.monografia.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private JwtTokenStore tokenStore;

	private static final String[] AUTH = {"oauth/token"};
	
	private static final String[] GET = { "/categorias/**", "/subcategorias/**" };

	//private static final String[] OPERATOR_OR_ADMIN = { };

	private static final String[] ADMIN = { "/categorias/**", "/subcategorias/**" };

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(AUTH).permitAll()
			.antMatchers(HttpMethod.GET, GET).permitAll()
			//.antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")
			.antMatchers(ADMIN).hasAnyRole("ADMIN")
			.anyRequest().authenticated();
	}


}
