package com.tienda.service;

import com.tienda.domain.Constante;
import com.tienda.repository.ConstanteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstanteService {

    @Autowired
    private ConstanteRepository constanteRepository;

    @Transactional(readOnly = true)
    public List<Constante> getConstantes() {

        var lista = constanteRepository.findAll();

        return lista;
    }

    @Transactional(readOnly = true)
    public Constante getConstante(Constante constante) {

        return constanteRepository.findById(constante.getIdConstante()).orElse(null);
    }

    @Transactional
    public void save(Constante constante) {
        constanteRepository.save(constante);
    }

    @Transactional
    public boolean delete(Constante constante) {
        try {
            constanteRepository.delete(constante);
            constanteRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Constante getConstantePorAtributo(String atributo) {
        return constanteRepository.findByAtributo(atributo);
    }
}