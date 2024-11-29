package com.usta.Galeria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "USUARIOS")
public class UsuarioEntity implements Serializable {
    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_usuario")
    private Long idUsuario;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre_usu",length = 40,nullable = false)
    private String nombreUsuario;


    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email_usu",length = 100, unique = true, nullable = false)
    private String emailUsuario;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password_usu",length = 100, nullable = false)
    private String passwordUsuario;

    @NotNull
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RolEntity idRol;
}
