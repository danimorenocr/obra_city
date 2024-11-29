package com.usta.Galeria.services;

import com.usta.Galeria.dao.ExposicionDao;
import com.usta.Galeria.entities.ExposicionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExposicionServiceImplement implements ExposicionService {

    @Autowired
    private ExposicionDao dao;

    @Override
 public List<ExposicionEntity> findByUbicacion(String ubicacion) {
        return dao.findByUbicacionExpContainingIgnoreCase(ubicacion);
    }

    @Override
    public List<ExposicionEntity> findAll() {
        return (List<ExposicionEntity>) dao.findAll();
    }

    @Override
public void save(ExposicionEntity exposicion) {
    dao.save(exposicion);
}


    @Override
    public ExposicionEntity findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
    dao.deleteById(id);
    }

    @Override
    public ExposicionEntity actualizarExposicionEntity(ExposicionEntity libro) {
        return dao.save(libro);
    }


    @Override
    @Transactional(readOnly = true)
    public ExposicionEntity viewDetails(Long id) {
        return dao.viewDetails(id);
    }
}
