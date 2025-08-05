package com.tienda.controller;

import com.tienda.domain.Item;
import com.tienda.domain.Producto;
import com.tienda.service.ConstanteService;
import com.tienda.service.ItemService;
import com.tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarritoController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductoService productoService;

    //El metodo que agrega items al carrito
    @GetMapping("/carrito/agregar/{idProducto}")
    public ModelAndView agregar(Model model, Item item) {
        Item item2 = itemService.getItem(item);
        if (item2 == null) {
            Producto producto = productoService.getProducto(item);
            item2 = new Item(producto);
        }
        itemService.save(item2);
        var lista = itemService.getItems();
        var carritoTotalVenta = itemService.getTotal();
        model.addAttribute("listaTotal", lista.size());
        model.addAttribute("listaItems", lista);
        model.addAttribute("carritoTotal", carritoTotalVenta);
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }

    @Autowired
    private ConstanteService constanteService;
    
    @GetMapping("/carrito/listado")
    public String listado(Model model) {
        var lista = itemService.getItems();
        double totalVenta = itemService.getTotal();
        model.addAttribute("items", lista);
        model.addAttribute("carritoTotal", totalVenta);
        
        double tCambio = Double.parseDouble(constanteService.getConstantePorAtributo("dolar").getValor());
        
        model.addAttribute("totalDolares", (double) (Math.round(totalVenta/tCambio*100))/100);
        
        model.addAttribute("precioVenta", tCambio);
        
        return ("/carrito/listado");
    }

    @GetMapping("/carrito/eliminar/{idProducto}")
    public String eliminar(Item item) {
        item = itemService.getItem(item);

        itemService.delete(item);

        return "redirect:/carrito/listado";
    }

    @GetMapping("/carrito/modificar/{idProducto}")
    public String modificar(Model model, Item item) {
        item = itemService.getItem(item);

        model.addAttribute("item", item);

        return "/carrito/modifica";
    }
    
    @PostMapping("/carrito/guardar")
    public String guardar(Item item) {

        itemService.update(item);

        return "redirect:/carrito/listado";
    }
    
    @GetMapping("/facturar/carrito")
    public String facturar() {
        itemService.facturar();
        return "redirect:/";
    }
}
