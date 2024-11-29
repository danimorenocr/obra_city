package com.usta.Galeria.controllers;

import com.usta.Galeria.entities.ArtistaEntity;
import com.usta.Galeria.entities.ExposicionEntity;
import com.usta.Galeria.services.ArtistaService;
import com.usta.Galeria.services.ExposicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exposiciones")
public class ExposicionController {

    @Autowired
    private ExposicionService exposicionService;

    @Autowired
    private ArtistaService artistaService;

    // Método para listar todas las exposiciones o buscar por ubicación
    @GetMapping
    public String listarExposiciones(Model model,
            @RequestParam(value = "ubicacion", required = false) String ubicacion) {
        model.addAttribute("title", "Listado de Exposiciones");
        model.addAttribute("urlRegistro", "/exposiciones/crearExposicion");

        if (ubicacion != null && !ubicacion.isEmpty()) {
            model.addAttribute("exposiciones", exposicionService.findByUbicacion(ubicacion));
        } else {
            model.addAttribute("exposiciones", exposicionService.findAll());
        }

        return "exposiciones/listarExposiciones";
    }

    // Método para mostrar el formulario de creación de exposiciones
    @GetMapping("/crearExposicion")
    public String crearExposicionForm(Model model) {
        model.addAttribute("title", "Registrar Exposición");
        model.addAttribute("exposicion", new ExposicionEntity());

        // Obtener lista de artistas
        List<ArtistaEntity> listaArtistas = artistaService.findAll();
        if (listaArtistas == null || listaArtistas.isEmpty()) {
            listaArtistas = new ArrayList<>();
        }
        model.addAttribute("listaArtistas", listaArtistas);
        return "exposiciones/crearExposicion";
    }

    // Método para guardar una exposición
    @PostMapping
    public String guardarExposicion(@ModelAttribute ExposicionEntity exposicion) {
        exposicionService.save(exposicion);
        return "redirect:/exposiciones";
    }

@GetMapping("/editarExposicion/{id}")
public String editarExposicion(Model model, @PathVariable(value = "id") Long idexposicion) {
    model.addAttribute("title", "Editar exposición");
    model.addAttribute("exposicionEdit", exposicionService.findById(idexposicion));
    List<ArtistaEntity> artistas = artistaService.findAll();
    model.addAttribute("artistas", artistas);
    return "exposiciones/editarExposicion";
}

@PostMapping("/editarExposicion/{id}")
public String editarExposicion(@ModelAttribute("exposicionEdit") ExposicionEntity exposicionEntity,
                                @PathVariable(value = "id") Long idExposicion,
                                @RequestParam(value = "artistas") List<Long> artistasIds,
                                BindingResult result) throws IOException {
    if (result.hasErrors()) {
        return "error500";
    }
    ExposicionEntity exposicionAuxiliar = exposicionService.findById(idExposicion);
    exposicionAuxiliar.setIdExposicion(idExposicion);
    exposicionAuxiliar.setTituloExp(exposicionEntity.getTituloExp());
    exposicionAuxiliar.setDescripcionExp(exposicionEntity.getDescripcionExp());
    exposicionAuxiliar.setFechaInicio(exposicionEntity.getFechaInicio());
    exposicionAuxiliar.setFechaFin(exposicionEntity.getFechaFin());

    exposicionAuxiliar.getArtistas().clear();
    if (artistasIds != null) {
        for (Long artistaId : artistasIds) {
            ArtistaEntity artista = artistaService.findById(artistaId);
            if (artista != null) {
                exposicionAuxiliar.getArtistas().add(artista);
            }
        }
    }
    exposicionService.actualizarExposicionEntity(exposicionAuxiliar);
    return "redirect:/exposiciones";
}


    
// Método para eliminar una exposición
 @RequestMapping(value = "/eliminarExposicion/{id}")
    public String eliminarById(@PathVariable(value = "id") Long id) throws IOException{
        if (id>0){
            ExposicionEntity expo = exposicionService.findById(id);
            if (expo != null){
                expo.getArtistas().clear();
                exposicionService.save(expo);
                exposicionService.deleteById(id);
            }
        }else {
            return "redirect:/error500";
        }
        return "redirect:/exposiciones";
    }
}
