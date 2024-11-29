package com.usta.Galeria.services;


import com.usta.Galeria.dao.usuarioDao;
import com.usta.Galeria.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImplement implements UsuarioService {

    @Autowired
    private usuarioDao usuarioDao;


    @Override
    public List<UsuarioEntity> findAll() {
        return (List<UsuarioEntity>) usuarioDao.findAll();
    }

    @Override
    public void save(UsuarioEntity usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    public UsuarioEntity findById(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        usuarioDao.deleteById(id);
    }

    @Override
    public UsuarioEntity actualizarUsuarioEntity(UsuarioEntity usuario) {
        return usuarioDao.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEntity viewDetails(String email) {
        return usuarioDao.findByEmailUsuario(email);
    }
    public UsuarioEntity findByEmail(String email) {
        return usuarioDao.findByEmailUsuario(email); // Asegúrate de tener este método en tu repositorio.
    }
    
}
