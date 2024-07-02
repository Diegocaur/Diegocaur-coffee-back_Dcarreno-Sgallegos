package cl.ucm.coffee.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    //@Autowired
    //public SecurityConfig(JwtFilter jwtFilter) {
    //    this.jwtFilter = jwtFilter;
   // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()


                //Metodos para el Coffee
                .requestMatchers(HttpMethod.GET, "/api/coffee/listacoffees").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/coffee/coffename").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/coffee/crear").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/coffee/actualizar").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/coffee/borrar").hasRole("ADMIN")
                //Metods Auth
                .requestMatchers(HttpMethod.PUT, "/api/auth/block").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/auth/unlock").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/auth/update").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()
                //Metodos para los Testimonios
                .requestMatchers(HttpMethod.POST, "/api/testimonial/crear").hasRole("CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/testimonial/coffeeid").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
