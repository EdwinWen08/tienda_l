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

        var categorias = categoriaService.getCategorias(true); //Ocupamos inyectar las categorias y usar en agregar producto (seleccionar)
        model.addAttribute("categorias", categorias);

        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}") //Hacer filtrado de prod en las categorias
    public String listado(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria); //Recuperar registro cat completo

        model.addAttribute("productos", categoria.getProductos());

        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        return "/pruebas/listado";
    }

    @GetMapping("/listado2") //Al hacer click en consultas en la web
    public String listado2(Model model) {
        var lista = productoService.getProductos(false);
        model.addAttribute("productos", lista);

        return "/pruebas/listado2";
    }

    @PostMapping("/query1") //Al hacer click en consultas en la web
    public String consulta1(@RequestParam() double precioInf, @RequestParam() double precioSup, Model model) {
        var lista = productoService.query1(precioInf, precioSup);
        model.addAttribute("productos", lista);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query2") //Al hacer click en consultas en la web
    public String consulta2(@RequestParam() double precioInf, @RequestParam() double precioSup, Model model) {
        var lista = productoService.query2(precioInf, precioSup);
        model.addAttribute("productos", lista);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query3") //Al hacer click en consultas en la web
    public String consulta3(@RequestParam() double precioInf, @RequestParam() double precioSup, Model model) {
        var lista = productoService.query3(precioInf, precioSup);
        model.addAttribute("productos", lista);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

}
