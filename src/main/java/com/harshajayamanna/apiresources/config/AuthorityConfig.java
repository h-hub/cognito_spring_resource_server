package com.harshajayamanna.apiresources.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.time.Duration;
import java.util.Collection;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorityConfig extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuerUri;

	private static final String AUTHORITY_PREFIX = "ROLE_";

	private static final String CLAIM_ROLES = "cognito:groups";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/status").permitAll().anyRequest().authenticated().and()
				.oauth2ResourceServer().jwt();
	}

	@Bean
	public JwtDecoder jwtDecoder() {

		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);

		OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
				new JwtTimestampValidator(Duration.ofSeconds(60)), new JwtIssuerValidator(issuerUri));

		jwtDecoder.setJwtValidator(withClockSkew);
		jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());

		return jwtDecoder;
	}

	private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(getJwtGrantedAuthoritiesConverter());
		return jwtAuthenticationConverter;
	}

	private Converter<Jwt, Collection<GrantedAuthority>> getJwtGrantedAuthoritiesConverter() {
		JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
		converter.setAuthorityPrefix(AUTHORITY_PREFIX);
		converter.setAuthoritiesClaimName(CLAIM_ROLES);
		return converter;
	}

}