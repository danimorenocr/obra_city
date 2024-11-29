package com.usta.Galeria.controllers;

import com.usta.Galeria.services.UsuarioService;
import com.usta.Galeria.entities.RolEntity;
import com.usta.Galeria.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping(value = "/register")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        model.addAttribute("title", "Registrar Usuario");
        return "register";
    }
    

    @PostMapping("/register")
    public String register(@ModelAttribute("usuario") @Valid UsuarioEntity usuario,
                           BindingResult result,
                           @RequestParam("confirmarClave") String confirmarClave,
                           Model model,
                           SessionStatus status) {
    
        // Verificar si el correo electrónico ya está registrado
        if (usuarioService.findByEmail(usuario.getEmailUsuario()) != null) {
            result.rejectValue("emailUsuario", "error.usuario", "El correo electrónico ya está registrado.");
        }
    
        if (result.hasErrors()) {
            model.addAttribute("title", "Registrar Usuario");
            return "register";
        }
    
        if (!usuario.getPasswordUsuario().equals(confirmarClave)) {
            result.rejectValue("clave", "error.usuario", "Las contraseñas no coinciden.");
            model.addAttribute("title", "Registrar Usuario");
            return "register";
        }
    
        // Codificar la contraseña
        String pass = new BCryptPasswordEncoder().encode(usuario.getPasswordUsuario());
        usuario.setPasswordUsuario(pass);
    
        // Asignar rol de lector
        RolEntity rolLector = new RolEntity();
        rolLector.setId_rol(2L);  // lector
        usuario.setIdRol(rolLector);
    
        usuarioService.save(usuario);
        status.setComplete();
    
        return "redirect:/login";
    }
    


}

