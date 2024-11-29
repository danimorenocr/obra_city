package com.usta.Galeria.services;


import com.usta.Galeria.dao.ObraDao;
import com.usta.Galeria.entities.ObraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraServiceImplement implements ObraService {

    @Autowired
    private ObraDao obraDao;

    @Override
    public List<ObraEntity> findAll() {
        return (List<ObraEntity>) obraDao.findAll();
    }

    @Override
    public void save(ObraEntity obra) {
        obraDao.save(obra);
    }

    @Override
    public ObraEntity findById(Long id) {
        return obraDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        obraDao.deleteById(id);
    }

    @Override
    public ObraEntity actualizarObraEntity(ObraEntity obra) {
        return obraDao.save(obra);
    }

    @Override
    public ObraEntity viewDetails(Long id) {
        return findById(id);
    }
}
