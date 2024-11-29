package com.usta.Galeria.security;

import com.usta.Galeria.dao.usuarioDao;
import com.usta.Galeria.entities.UsuarioEntity;
import com.usta.Galeria.entities.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import org.hibernate.Hibernate;
import java.util.Collection;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private usuarioDao usuarioDao;

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioEntity usuario = usuarioDao.findByEmailUsuario(username);

    if (usuario == null) {
        System.out.println("Usuario no encontrado: " + username);
        throw new UsernameNotFoundException("El usuario no existe");
    }

    System.out.println("Usuario encontrado: " + usuario.getEmailUsuario());
    System.out.println("Contraseña almacenada (cifrada): " + usuario.getPasswordUsuario());

    return new User(
            usuario.getEmailUsuario(),
            usuario.getPasswordUsuario(),
            mapearAutoridadesRol(usuario.getIdRol())
    );
}


    private Collection<? extends GrantedAuthority> mapearAutoridadesRol(RolEntity rol) {
        return List.of(new SimpleGrantedAuthority(rol.getRol()));
    }
}
