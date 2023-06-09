package edu.ntnu.idatt2106_2023_06.backend.config;

import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 Configuration class for Spring Security. Enables Web Security and provides a SecurityFilterChain bean.
 Uses a JwtAuthenticationFilter for authentication and an AuthenticationProvider for authentication management.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    /**
     Configures the security filter chain.

     @param http HttpSecurity object used for configuration
     @return A SecurityFilterChain object
     @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
        logger.info("Configuring security filter chain");
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests(authorize ->
                                authorize.requestMatchers("/home", "/login", "/user/register", "/about", "/swagger/**", "/docs/**", "/swagger-ui/**", "/user/load/{username}", "/fridge/loadAll", "/fridge/loadAllId")
                                        .permitAll()
                                        .requestMatchers("/user/**", "/auth/**", "/admin/**", "/listing/user/**", "/fridge/**", "/item/**", "/recipe/**", "/notification/**")
                                        .permitAll().anyRequest().authenticated()
                )
//            .logout(logout ->
//                    logout.logoutUrl("/logout")
//                            .clearAuthentication(true)
//                            .invalidateHttpSession(true)
//                            .deleteCookies("JSESSION", "remember-me")//todo yeah?
//            )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    /**
     Configures CORS mapping for the application.

     @return A WebMvcConfigurer object
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }

}