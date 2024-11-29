package com.usta.Galeria.dao;

import com.usta.Galeria.entities.ArtistaEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistaDao extends JpaRepository<ArtistaEntity, Long> {

    // Consulta personalizada para obtener el detalle del artista
    @Query("SELECT AU FROM ArtistaEntity AU WHERE AU.idArtista = ?1")
    public ArtistaEntity viewDetails(Long idArtista);
     public List<ArtistaEntity> findByNombreArtContainingIgnoreCase(String nombre);
}
