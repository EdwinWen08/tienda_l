package com.tienda.service;

import com.tienda.domain.Categoria;
import com.tienda.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired  //Se usa para crear automaticamente una unica intancia esta clase o elemento
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true) //Se usa para indicar que se hara una transaccion a una BD de solo lectura
    public List<Categoria> getCategorias(boolean activos) { //Devuelve arraylist con registros de la consulta
        //Se usa "activos" si se desea limitar la vista a solo las categorias activas
        var lista = categoriaRepository.findAll(); //Devuelve lista de categoria, porque en repositorio le dijimos

        if (activos) { //Si solo se quieren los registros de categorias activas
            lista.removeIf(e -> !e.isActivo()); //Borrar registros cat inactivas, e los elementos...
        }

        return lista;
    }

    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {

        return categoriaRepository
                .findById(categoria.getIdCategoria())
                .orElse(null);
    }

    @Transactional
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Transactional
    public boolean delete(Categoria categoria) {
        try {
            categoriaRepository.delete(categoria);
            categoriaRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
