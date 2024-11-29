package com.usta.Galeria.services;


import com.usta.Galeria.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    public List<UsuarioEntity> findAll();
    public void save(UsuarioEntity usuario);
    public UsuarioEntity findById(Long id);
    public void deleteById(Long id);
    public UsuarioEntity actualizarUsuarioEntity(UsuarioEntity usuario);
    public UsuarioEntity viewDetails(String email); //llamarlo igual en todo lado
    public UsuarioEntity findByEmail(String email);

}
