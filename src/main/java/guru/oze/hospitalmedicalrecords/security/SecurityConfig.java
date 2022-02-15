package guru.oze.hospitalmedicalrecords.security;

import guru.oze.hospitalmedicalrecords.security.jwt.AuthEntryPointJwt;
import guru.oze.hospitalmedicalrecords.security.jwt.AuthTokenFilter;
import guru.oze.hospitalmedicalrecords.security.jwt.JWTUtils;
import guru.oze.hospitalmedicalrecords.security.service.DomainUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTUtils jwtUtils;
    private final DomainUserDetailsService userDetailsService;

    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * Solution unable to inject authentication manager directly
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // token based distributed authentication, so no session is required
        http.cors().and()
                // CRSF is disabled. Because the session is not used, cross site csrf attack defense is disabled. Otherwise, the login cannot succeed
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Configure permissions
                .authorizeRequests()
                // Login CaptchaImage allows anonymous access
                .antMatchers("/api/v1/authenticate").permitAll()
                .antMatchers("/api/v1/register").permitAll()
                .antMatchers("/api/v1/h2-console/**").permitAll()
                // Except for all the above requests, authentication is required
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // Exit function
        http.logout().logoutUrl("/logout");

    }
}
