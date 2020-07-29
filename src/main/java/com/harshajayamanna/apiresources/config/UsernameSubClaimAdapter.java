package com.harshajayamanna.apiresources.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {
	private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());
	
	@Override
	public Map<String, Object> convert(Map<String, Object> claims) {
		Map<String, Object> convertedClaims = this.delegate.convert(claims);

		if (claims.containsKey("cognito:groups")) {
			convertedClaims.put("authorities", claims.get("cognito:groups"));
		}

		if (claims.containsKey("username")) {
			convertedClaims.put("user_name", claims.get("username"));
		}

		return convertedClaims;
	}
}