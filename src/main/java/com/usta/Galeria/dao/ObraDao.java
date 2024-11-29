package com.usta.Galeria.dao;

import com.usta.Galeria.entities.ObraEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ObraDao extends CrudRepository<ObraEntity, Long> { // Cambiar Integer a Long
    @Transactional
    @Query("SELECT OB FROM ObraEntity OB WHERE OB.idObra = ?1")
    public ObraEntity viewDetails(Long id); // El parámetro también debe ser Long
}
