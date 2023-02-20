package checker.security;

import checker.filter.CustomAuthenticationFilter;
import checker.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)));
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // ENDPOINT-URI FULL ACCESS
        /*http.authorizeHttpRequests()
                .requestMatchers("/login", "/token/refresh/**")
                        .permitAll();*/

        // ENDPOINTURI SECURIZATE ADMIN
        //http.authorizeHttpRequests().requestMatchers("/**").hasAuthority("ADMIN");

        //http.authorizeHttpRequests().requestMatchers("/roles/**").hasAuthority("ADMIN");

        // ENDPOINTURI SECURIZATE STUDENT
        //http.authorizeHttpRequests().requestMatchers("/subjects/**").hasAuthority("STUDENT");

        // ENDPOINTURI SECURIZATE PROFESSOR
        //http.authorizeHttpRequests().requestMatchers("/groups/**").hasAuthority("PROFESSOR");


        http.authorizeHttpRequests((authz) -> authz
                .anyRequest()
                //.authenticated());
                .permitAll());


        //http.addFilter(customAuthenticationFilter);
        //http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().disable();


        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
