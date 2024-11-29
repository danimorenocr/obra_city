package com.usta.Galeria.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ROL")
public class RolEntity implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_rol")
    private Long id_rol;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "rol",length = 20,nullable = false)
    private String rol;

    public RolEntity(String rol) {
        super();
        this.rol = rol;
    }
    public RolEntity() {

    }
}
