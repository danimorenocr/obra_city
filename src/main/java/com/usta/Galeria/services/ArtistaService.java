package com.usta.Galeria.services;

import com.usta.Galeria.entities.ArtistaEntity;

import java.util.List;

public interface ArtistaService {
    List<ArtistaEntity> findAll();
    ArtistaEntity findById(Long id);
    void save(ArtistaEntity artista);
    void deleteById(Long id);
     public List<ArtistaEntity> findByNombre(String nombre);
}
