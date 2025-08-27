package org.example.ss_2022_c15_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaqueTokenConfigurer -> opaqueTokenConfigurer
                                        // implement the converter if you want custom data from the introspection endpoint
//                                .authenticationConverter(OpaqueTokenAuthenticationConverter)
                                        .introspectionUri("http://localhost:8080/oauth2/introspect") // should be in properties file
                                        .introspectionClientCredentials("client", "secret")
                                // not hard coded in production (just for demonstration); it should be injected from a vault or a safe environment
                        )
        );

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.anyRequest().authenticated());

        return http.build();
    }
}