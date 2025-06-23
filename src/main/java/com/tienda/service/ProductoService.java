package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired  //Se usa para crear automaticamente una unica intancia esta clase o elemento
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true) //Se usa para indicar que se hara una transaccion a una BD de solo lectura
    public List<Producto> getProductos(boolean activos) { //Devuelve arraylist con registros de la consulta
        //Se usa "activos" si se desea limitar la vista a solo las productos activas
        var lista = productoRepository.findAll(); //Devuelve lista de producto, porque en repositorio le dijimos

        if (activos) { //Si solo se quieren los registros de productos activas
            lista.removeIf(e -> !e.isActivo()); //Borrar registros cat inactivas, e los elementos...
        }

        return lista;
    }

    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {

        return productoRepository
                .findById(producto.getIdProducto())
                .orElse(null);
    }

    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional
    public boolean delete(Producto producto) {
        try {
            productoRepository.delete(producto);
            productoRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
