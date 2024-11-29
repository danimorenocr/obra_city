package com.usta.Galeria.services;

import com.usta.Galeria.dao.ArtistaDao;
import com.usta.Galeria.entities.ArtistaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistaServiceImplement implements ArtistaService {

    @Autowired
    private ArtistaDao artistaDAO;

    @Override
    public List<ArtistaEntity> findAll() {
        return artistaDAO.findAll();
    }

    @Override
    public ArtistaEntity findById(Long id) {
        return artistaDAO.findById(id).orElse(null);
    }

    @Override
    public List<ArtistaEntity> findByNombre(String nombre) {
        return artistaDAO.findByNombreArtContainingIgnoreCase(nombre); // Usando el nombre para buscar
    }
    
    @Override
    public void save(ArtistaEntity artista) {
        artistaDAO.save(artista);
    }

    @Override
    public void deleteById(Long id) {
        artistaDAO.deleteById(id);
    }
}
