package com.tienda.service;

import com.tienda.domain.Rol;
import com.tienda.domain.Usuario;
import com.tienda.repository.RolRepository;
import com.tienda.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private HttpSession session; //Variables de session
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Se busca el usuario en la BD
        Usuario usuario = usuarioRepository.findByUsername(username);
        
        if (usuario==null) {
            throw new UsernameNotFoundException(username);
        }
        //Si estamos aca... si se encontro
        session.removeAttribute("imagenUsuario");
        session.setAttribute("imagenUsuario", usuario.getRutaImagen());
        
        
        var roles=new ArrayList<GrantedAuthority>();
        for (Rol rol : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_"+rol.getNombre()));
        }
        //se devuelve el usaurio de tipo userDetails
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
