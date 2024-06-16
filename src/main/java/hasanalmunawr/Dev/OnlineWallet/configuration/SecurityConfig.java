package hasanalmunawr.Dev.OnlineWallet.configuration;


import hasanalmunawr.Dev.OnlineWallet.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationManager authenticationManager;
    private final LogoutHandler logoutHandler;

    private static String[] URL_PERMISSIONS = {
            "/api/v1/auth/**",
            "/swagger-ui.*html",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/api/v1/auth/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(URL_PERMISSIONS).permitAll()
                                .anyRequest().authenticated()
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(STATELESS))
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint((request, response, authException) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationManager(authenticationManager)
                .httpBasic(withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> {
                                        SecurityContextHolder.clearContext();
                                    response.setStatus(HttpServletResponse.SC_OK);
                                })

                )
                .build();
    }




}
