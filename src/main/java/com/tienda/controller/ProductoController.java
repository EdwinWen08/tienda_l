package com.tienda.controller; //No como add view controller > controlador para ruta producto/listado, gestiona consultas que empiezan con /producto

import com.tienda.domain.Producto;
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
@RequestMapping("/producto") //todo este mapeo se gestiona en esta clase
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listado(Model model) { //Se ejecuta cuando llega a ruta
        var lista = productoService.getProductos(false); //Obtener lista productos

        model.addAttribute("productos", lista); //Para pasar info de programacion a listado (atributo,arraylist)

        return "/producto/listado"; //Devolviendo pagina listado en folder producto
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/guardar")
    public String guardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            String ruta = firebaseStorageService.cargaImagen(imagenFile,
                    "producto",
                    producto.getIdProducto());
            producto.setRutaImagen(ruta);
        }
        productoService.save(producto);
        //Falta el redirect....
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Producto producto,
            RedirectAttributes redirectAttributes) {
        producto = productoService.getProducto(producto);
        if (producto == null) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("producto.error01", null, Locale.getDefault()));
        } else if (false) {
            //La producto tiene productos se modifica en semana 8
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("producto.error02", null, Locale.getDefault()));
        } else if (productoService.delete(producto)) {
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        } else {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("producto.error03", null, Locale.getDefault()));
        }
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String modificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "/producto/modifica";
    }
}
