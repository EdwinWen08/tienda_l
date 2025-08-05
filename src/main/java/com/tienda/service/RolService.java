package com.tienda.service;

import com.tienda.domain.Rol;
import com.tienda.repository.RolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    @Autowired  //Se usa para crear automaticamente una unica intancia esta clase o elemento
    private RolRepository rolRepository;

    @Transactional(readOnly = true) //Se usa para indicar que se hara una transaccion a una BD de solo lectura
    public List<Rol> getRoles() { //Devuelve arraylist con registros de la consulta
        //Se usa "activos" si se desea limitar la vista a solo las rols activas
        var lista = rolRepository.findAll(); //Devuelve lista de rol, porque en repositorio le dijimos
        return lista;
    }

    @Transactional(readOnly = true)
    public Rol getRol(Rol rol) { //Recuperar 1 obj rol 

        return rolRepository.findById(rol.getIdRol()).orElse(null); //Busca registro en tabla, si encuentra devuelve rol, sino devuelve null
    }

    @Transactional
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

    @Transactional
    public boolean delete(Rol rol) {
        try {
            rolRepository.delete(rol); //Si este delete va bien, retorna true
            rolRepository.flush(); //Delete queda medianamente cubierto
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
