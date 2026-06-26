package com.mycompany.CasoUnoKendallArtavia.Controller;

import com.mycompany.CasoUnoKendallArtavia.Domain.Categoria;
import com.mycompany.CasoUnoKendallArtavia.Domain.Servicio;
import com.mycompany.CasoUnoKendallArtavia.Service.CategoriaService;
import com.mycompany.CasoUnoKendallArtavia.Service.ServicioService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    private final ServicioService servicioService;
    private final CategoriaService categoriaService;
    private final MessageSource messageSource;

    public ServicioController(
            ServicioService servicioService,
            CategoriaService categoriaService,
            MessageSource messageSource) {
        this.servicioService = servicioService;
        this.categoriaService = categoriaService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var servicios = servicioService.getServicios(false);
        var categorias = categoriaService.getCategorias(false);

        model.addAttribute("servicios", servicios);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalServicios", servicios.size());

        return "/servicio/listado";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Servicio servicio,
            @RequestParam Integer idCategoria,
            RedirectAttributes redirectAttributes) {

        Optional<Categoria> categoriaOpt = categoriaService.getCategoria(idCategoria);

        if (categoriaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("categoria.error01", null, Locale.getDefault()));
            return "redirect:/servicio/listado";
        }

        servicio.setCategoria(categoriaOpt.get());
        servicioService.save(servicio);

        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));

        return "redirect:/servicio/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idServicio, RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";

        try {
            servicioService.delete(idServicio);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "servicio.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "servicio.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "servicio.error03";
        }

        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));

        return "redirect:/servicio/listado";
    }

    @GetMapping("/modificar/{idServicio}")
    public String modificar(@PathVariable("idServicio") Integer idServicio, Model model, RedirectAttributes redirectAttributes) {

        Optional<Servicio> servicioOpt = servicioService.getServicio(idServicio);

        if (servicioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("servicio.error01", null, Locale.getDefault()));
            return "redirect:/servicio/listado";
        }

        var categorias = categoriaService.getCategorias(false);

        model.addAttribute("servicio", servicioOpt.get());
        model.addAttribute("categorias", categorias);

        return "/servicio/modifica";
    }
}