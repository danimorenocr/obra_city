package com.usta.Galeria.services;

import com.usta.Galeria.entities.ExposicionEntity;

import java.util.List;

public interface ExposicionService {

    public List<ExposicionEntity> findAll();
    public void save(ExposicionEntity exposicion);
    public ExposicionEntity findById(Long id);
    public void deleteById(Long id);
    public ExposicionEntity actualizarExposicionEntity(ExposicionEntity exposicion);
    public List<ExposicionEntity> findByUbicacion(String ubicacion);
    public ExposicionEntity viewDetails(Long id);

}
