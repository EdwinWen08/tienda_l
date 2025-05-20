package com.tienda;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {
    //@Configuration: Le indica a Spring que esta clase contiene definiciones de configuración, como si fuera un archivo application.properties pero en Java
    //implements WebMvcConfigurer: Permite personalizar la configuración del MVC de Spring, como rutas, recursos estáticos, etc.

    @Override
    public void addViewControllers(ViewControllerRegistry registro) { //Esto agrega rutas web "simples" sin necesidad de crear un @Controller separado.
        registro.addViewController("/").setViewName("index");  //Cuando un usuario entra a la raíz (http://localhost:8080/), se mostrará la vista llamada index.html.
        registro.addViewController("/ejemplo2").setViewName("ejemplo2"); // Si el usuario va a /ejemplo2, se mostrará la plantilla ejemplo2.html.
        registro.addViewController("/multimedia").setViewName("multimedia");
         registro.addViewController("/iframes").setViewName("iframes");
    }

    /* El siguiente método se utilizar para publicar en la nube, independientemente  */
    @Bean
    public SpringResourceTemplateResolver templateResolver_0() {  //indica a Thymeleaf dónde encontrar las plantillas HTML que debe renderizar
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver(); //Crea una nueva instancia del objeto que resolverá las plantillas (archivos HTML).
        resolver.setPrefix("classpath:/templates"); //Establece el prefijo para las rutas de las plantillas. "classpath:/templates" significa que busca los archivos HTML en la carpeta src/main/resources/templates
        resolver.setSuffix(".html"); //Solo buscará archivos que terminen en .html
        resolver.setTemplateMode(TemplateMode.HTML); //Indica que los archivos deben interpretarse como HTML (otros modos posibles son TEXT, XML, etc.).
        resolver.setOrder(0); //Da prioridad a este resolver. Si tienes varios TemplateResolver, este se usará primero
        resolver.setCheckExistence(true); //Verifica si el archivo existe antes de intentar cargarlo, evitando errores innecesarios si falta un HTML
        return resolver;
    }
}

