package com.tienda.controller; //No como add view controller > controlador para ruta producto/listado, gestiona consultas que empiezan con /producto

import com.tienda.domain.Categoria;
import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
import com.tienda.service.FirebaseStorageService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = productoService.getProductos(false);
        model.addAttribute("productos", lista);

        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listado(Categoria categoria,Model model) {
        categoria=categoriaService.getCategoria(categoria);
        
        
        model.addAttribute("productos", categoria.getProductos());

        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        return "/pruebas/listado";
    }
//Semana8
}
