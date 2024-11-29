package com.usta.Galeria.dao;

import com.usta.Galeria.entities.ExposicionEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ExposicionDao extends CrudRepository<ExposicionEntity, Long> {

    @Query("SELECT expo FROM ExposicionEntity expo WHERE expo.idExposicion = ?1")
    ExposicionEntity viewDetails(Long idExpo);

    List<ExposicionEntity> findByUbicacionExpContainingIgnoreCase(String ubicacion);
}

