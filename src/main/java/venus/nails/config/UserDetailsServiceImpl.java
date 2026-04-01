package venus.nails.config;

import venus.nails.modelo.Usuario;
import venus.nails.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        System.out.println("🔍 Buscando usuario: " + correo);
        
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> {
                    System.out.println("❌ Usuario no encontrado: " + correo);
                    return new UsernameNotFoundException("Usuario no encontrado: " + correo);
                });

        System.out.println("✅ Usuario encontrado: " + usuario.getNombre() + " rol: " + usuario.getRol());

        return new User(
                usuario.getCorreo(),
                usuario.getContrasena(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
        );
    }
}