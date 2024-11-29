package com.usta.Galeria.controllers;

import com.usta.Galeria.entities.ArtistaEntity;
import com.usta.Galeria.services.ArtistaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping
    public String listarArtistas(@RequestParam(name = "nombre", required = false) String nombre, Model model) {
        model.addAttribute("title", "Listado de Artistas");
        model.addAttribute("urlRegistro", "/artistas/crear");
        
        List<ArtistaEntity> artistas;
        if (nombre != null && !nombre.isEmpty()) {
            artistas = artistaService.findByNombre(nombre); // Filtrar por nombre
        } else {
            artistas = artistaService.findAll(); // Obtener todos los artistas si no hay filtro
        }
        
        model.addAttribute("artistas", artistas);
        return "artistas/listarArtistas";
    }
    

    @GetMapping("/detalle/{id}")
    public String detalleArtista(@PathVariable("id") Long idArtista, Model model) {
        model.addAttribute("title", "Detalle del Artista");
        model.addAttribute("artista", artistaService.findById(idArtista));
        return "artistas/detalleArtista";
    }

    @GetMapping("/crear")
    public String crearArtistaForm(Model model) {
        model.addAttribute("title", "Registrar Artista");
        model.addAttribute("artista", new ArtistaEntity());
        return "artistas/crearArtista";
    }

    @PostMapping("/crear")
    public String guardarArtista(@Valid @ModelAttribute("artista") ArtistaEntity artista, BindingResult result) {
        if (result.hasErrors()) {
            return "artistas/crearArtista";
        }
        artistaService.save(artista);
        return "redirect:/artistas";
    }

    @GetMapping("/editar/{id}")
    public String editarArtistaForm(@PathVariable("id") Long idArtista, Model model) {
        ArtistaEntity artista = artistaService.findById(idArtista);
        model.addAttribute("title", "Editar Artista");
        model.addAttribute("artista", artista);
        return "artistas/editarArtista";
    }

    @PostMapping("/editar/{id}")
    public String actualizarArtista(@PathVariable("id") Long idArtista,
                                    @Valid @ModelAttribute("artista") ArtistaEntity artista,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return "artistas/editarArtista";
        }
        artista.setIdArtista(idArtista);
        artistaService.save(artista);
        return "redirect:/artistas";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminarArtista(@PathVariable("id") Long idArtista) {
        artistaService.deleteById(idArtista);
        return "redirect:/artistas";
    }
}
