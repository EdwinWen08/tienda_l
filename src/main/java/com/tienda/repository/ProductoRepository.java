package com.tienda.repository;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //Consulta ampliada para filtraer los productos en un rango de precio.
    public List<Producto> findByPrecioBetweenOrderByPrecio(
            double precioInf, double precioSup);

    //COnsulta JPQL similar...
    @Query(value = "SELECT a FROM Producto a WHERE a.precio BETWEEN :precioInf and :precioSup ORDER BY a.precio ASC")
    public List<Producto> consultaJPQL(double precioInf, double precioSup);

    //Consulta SQL
    @Query(nativeQuery = true, value = "SELECT a FROM Producto a WHERE a.precio BETWEEN :precioInf and :precioSup ORDER BY a.precio ASC")
    public List<Producto> consultaSQL(double precioInf, double precioSup);

}
