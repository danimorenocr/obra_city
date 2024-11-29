package com.usta.Galeria.controllers;

import com.usta.Galeria.entities.ArtistaEntity;
import com.usta.Galeria.entities.ObraEntity;
import com.usta.Galeria.services.ArtistaService;
import com.usta.Galeria.services.ObraService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/obra")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private ArtistaService artistaService;

    /**
     * Listar todas las obras
     */
    @GetMapping
    public String listarObrasObra(Model model) {
        model.addAttribute("title", "Listado de Obras");
        model.addAttribute("obras", obraService.findAll());
        return "obras/listarObras";
    }

    @RequestMapping(value = "/detalleObra/{id}")
    public String detalleObra(Model model, @PathVariable(value = "id") Long idExpo) {
        model.addAttribute("title", "Detalle de la obra");
        model.addAttribute("detalleObra", obraService.viewDetails(idExpo));
        return "obras/detalleObra";
    }

    @GetMapping("/crearObra")
    public String crearObraObra(Model model) {
        List<ArtistaEntity> artistas = artistaService.findAll();
        model.addAttribute("title", "Crear Nueva Obra");
        model.addAttribute("obra", new ObraEntity()); // Crear un nuevo objeto ObraEntity
        model.addAttribute("artistas", artistas); // Traer todos los artistas
        return "obras/crearObra"; // Vista para crear obras
    }

    @PostMapping("/crearObra")
    public String guardarObra(
            @Valid ObraEntity obra,
            @RequestParam("foto") MultipartFile foto,
            BindingResult result,
            @RequestParam("artista") Long artistaId,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor, verifica los datos del formulario.");
            return "obras/crearObra";
        }

        try {
            // Establecer artista
            ArtistaEntity artista = artistaService.findById(artistaId);
            if (artista == null) {
                model.addAttribute("error", "El artista seleccionado no existe.");
                return "obras/crearObra";
            }
            obra.setIdArtista(artista);

            // Guardar imagen
            String nombreImagen = guardarImagen(foto);
            if (nombreImagen == null) {
                model.addAttribute("error", "No se pudo guardar la imagen.");
                return "obras/crearObra";
            }
            obra.setFotoObra(nombreImagen);

            // Guardar obra
            obraService.save(obra);
            return "redirect:/obra";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la obra: " + e.getMessage());
            return "obras/crearObra";
        }
    }

    /**
     * Guardar imagen en ImgBB
     */
    private String guardarImagen(MultipartFile imagen) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://api.imgbb.com/1/upload");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.addTextBody("key", "c12a28d9bb2df7a329679344f061c2c5", ContentType.TEXT_PLAIN);
            builder.addBinaryBody("image", imagen.getInputStream(), ContentType.DEFAULT_BINARY,
                    imagen.getOriginalFilename());

            httpPost.setEntity(builder.build());

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                String responseString = EntityUtils.toString(responseEntity);
                JSONObject jsonResponse = new JSONObject(responseString);

                if (jsonResponse.getBoolean("success")) {
                    return jsonResponse.getJSONObject("data").getString("url");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si algo falla
    }

    /**
     * Mostrar formulario de edición
     */
    @GetMapping("/editarObra/{id}")
    public String editarObra(@PathVariable("id") Long id, Model model) {
        ObraEntity obra = obraService.findById(id);
        List<ArtistaEntity> artistas = artistaService.findAll();
        if (obra != null) {
            model.addAttribute("obra", obra);  // Pasar "obra" en lugar de "obraEdit"
            model.addAttribute("artistas", artistas);
            return "obras/editarObra";
        } else {
            model.addAttribute("error", "La obra no existe.");
            return "error/404";
        }
    }

    /**
     * Actualizar obra
     */
    @PostMapping("/editarObra/{id}")
    public String actualizarObra(
            @PathVariable("id") Long id,
            @ModelAttribute("obra") ObraEntity obraEditada,
            @RequestParam("foto") MultipartFile foto,
            BindingResult result,
            @RequestParam("artista") Long artistaId) {

        if (result.hasErrors()) {
            return "error500";
        }

        try {
            ObraEntity obraExistente = obraService.findById(id);
            if (obraExistente != null) {
                obraExistente.setTituloObra(obraEditada.getTituloObra());
                obraExistente.setDescripcionObra(obraEditada.getDescripcionObra());
                obraExistente.setTecnicaObra(obraEditada.getTecnicaObra());

                // Actualizar artista
                if (artistaId != null) {
                    ArtistaEntity artista = artistaService.findById(artistaId);
                    if (artista != null) {
                        obraExistente.setIdArtista(artista); // Asigna el artista completo
                    }
                }

                // Actualizar imagen si se proporciona
                if (!foto.isEmpty()) {
                    String nuevaImagen = guardarImagen(foto);
                    if (nuevaImagen != null) {
                        obraExistente.setFotoObra(nuevaImagen);
                    }
                }

                obraService.save(obraExistente);
            }
            return "redirect:/obra";
        } catch (Exception e) {
            e.printStackTrace();
            return "error500";
        }
    }

    @RequestMapping(value = "/eliminarObra/{id}")
    public String eliminarById(@PathVariable(value = "id") Long id) throws IOException{
        if (id>0){
            ObraEntity obra = obraService.findById(id);
            if (obra != null){
                obraService.save(obra);
                obraService.deleteById(id);
            }
        }else {
            return "redirect:/error500";
        }
        return "redirect:/obra";
    }


}
