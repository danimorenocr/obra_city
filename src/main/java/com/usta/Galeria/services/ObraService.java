package com.usta.Galeria.services;


import com.usta.Galeria.entities.ArtistaEntity;
import com.usta.Galeria.entities.ObraEntity;

import java.util.List;

public interface ObraService {
    public List<ObraEntity> findAll();
    public void save(ObraEntity obra);
    public ObraEntity findById(Long id);
    public void deleteById(Long id);
    public ObraEntity actualizarObraEntity(ObraEntity obra);
    public ObraEntity viewDetails(Long id);
}
