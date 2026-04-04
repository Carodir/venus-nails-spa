package venus.nails.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.UsuarioRepositorio;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            String correo = authentication.getName();
            String rol = authentication.getAuthorities().iterator().next().getAuthority();
            System.out.println("✅ Login exitoso, rol: " + rol);

            // Guardar usuario en sesión

            Usuario usuario = usuarioRepo.findByCorreo(correo).orElse(null);
            request.getSession().setAttribute("usuarioLogueado", usuario);
            request.getSession().setAttribute("rol", usuario.getRol());

            switch (rol) {
                case "ROLE_admin" -> response.sendRedirect("/admin/panel");
                case "ROLE_manicurista" -> response.sendRedirect("/manicurista/panel");
                default -> response.sendRedirect("/cliente/panel");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/registro", "/css/**", "/js/**", "/images/**", "/api/**",
                                "/api/pagos/**")
                        .permitAll()
                        .requestMatchers("/admin/**", "/usuarios/**", "/servicios/**",
                                "/pagos/**", "/horarios/**", "/resenas/**",
                                "/citas/**")
                        .hasAuthority("ROLE_admin")
                        .requestMatchers("/cliente/**").hasAuthority("ROLE_cliente")
                        .requestMatchers("/manicurista/**").hasAuthority("ROLE_manicurista")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("correo")
                        .passwordParameter("contrasena")
                        .successHandler(successHandler())
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}