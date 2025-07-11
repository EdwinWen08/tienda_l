package com.tienda;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
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
        registro.addViewController("/login").setViewName("login");
        registro.addViewController("/registro/nuevo").setViewName("/registro/nuevo");
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

    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                .requestMatchers("/", "/index", "/errores/**",
                        "/carrito/**", "/pruebas/**", "/reportes/**",
                        "/registro/**", "/js/**", "/webjars/**", "/fav/**")
                .permitAll()
                .requestMatchers(
                        "/producto/nuevo", "/producto/guardar",
                        "/producto/modificar/**", "/producto/eliminar/**",
                        "/categoria/nuevo", "/categoria/guardar",
                        "/categoria/modificar/**", "/categoria/eliminar/**",
                        "/usuario/nuevo", "/usuario/guardar",
                        "/usuario/modificar/**", "/usuario/eliminar/**",
                        "/reportes/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        "/producto/listado",
                        "/categoria/listado",
                        "/usuario/listado"
                ).hasAnyRole("ADMIN", "VENDEDOR")
                .requestMatchers("/facturar/carrito")
                .hasRole("USER")
                )
                .formLogin((form) -> form
                .loginPage("/login").permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    /* El siguiente método se utiliza para completar la clase no es 
    realmente funcional, la próxima semana se reemplaza con usuarios de BD */
    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username("juan")
                .password("{noop}123")
                .roles("USER", "VENDEDOR", "ADMIN")
                .build();
        UserDetails sales = User.builder()
                .username("rebeca")
                .password("{noop}456")
                .roles("USER", "VENDEDOR")
                .build();
        UserDetails user = User.builder()
                .username("pedro")
                .password("{noop}789")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, sales, admin);
    }

    //SEMANA 9
}
