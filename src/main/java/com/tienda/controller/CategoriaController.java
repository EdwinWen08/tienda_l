package com.tienda.controller; //No como add view controller > controlador para ruta categoria/listado, gestiona consultas que empiezan con /categoria

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
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
@RequestMapping("/categoria") //todo este mapeo se gestiona en esta clase
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) { //Se ejecuta cuando llega a ruta
        var lista = categoriaService.getCategorias(false); //Obtener lista categorias

        model.addAttribute("categorias", lista); //Para pasar info de programacion a listado (atributo,arraylist)

        return "/categoria/listado"; //Devolviendo pagina listado en folder categoria
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/guardar")
    public String guardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            String ruta = firebaseStorageService.cargaImagen(imagenFile,
                    "categoria",
                    categoria.getIdCategoria());
            categoria.setRutaImagen(ruta);
        }
        categoriaService.save(categoria);
        //Falta el redirect....
        return "redirect:/categoria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Categoria categoria,
            RedirectAttributes redirectAttributes) {
        categoria = categoriaService.getCategoria(categoria);
        if (categoria == null) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("categoria.error01", null, Locale.getDefault()));
        } else if (false) {
            //La categoria tiene productos se modifica en semana 8
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("categoria.error02", null, Locale.getDefault()));
        } else if (categoriaService.delete(categoria)) {
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        } else {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("categoria.error03", null, Locale.getDefault()));
        }
        return "redirect:/categoria/listado";
    }

    @PostMapping("/modificar")
    public String modificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }
}
