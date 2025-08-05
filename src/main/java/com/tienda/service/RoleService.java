package com.tienda.service;

import com.tienda.domain.Role;
import com.tienda.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired  //Se usa para crear automaticamente una unica intancia esta clase o elemento
    private RoleRepository roleRepository;

    @Transactional(readOnly = true) //Se usa para indicar que se hara una transaccion a una BD de solo lectura
    public List<Role> getRoles() { //Devuelve arraylist con registros de la consulta
        //Se usa "activos" si se desea limitar la vista a solo las roles activas
        var lista = roleRepository.findAll(); //Devuelve lista de role, porque en repositorio le dijimos
        return lista;
    }

    @Transactional(readOnly = true)
    public Role getRole(Role role) { //Recuperar 1 obj role 

        return roleRepository.findById(role.getRol()).orElse(null); //Busca registro en tabla, si encuentra devuelve role, sino devuelve null
    }

    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public boolean delete(Role role) {
        try {
            roleRepository.delete(role); //Si este delete va bien, retorna true
            roleRepository.flush(); //Delete queda medianamente cubierto
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
