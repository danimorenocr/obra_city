package com.usta.Galeria.dao;

import com.usta.Galeria.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface usuarioDao extends CrudRepository<UsuarioEntity, Long> {


    @Transactional
    @Query("SELECT u FROM UsuarioEntity u WHERE u.emailUsuario = :email")
UsuarioEntity findByEmailUsuario(@Param("email") String email);
//findby es para buscar por un campo en especifico



}
