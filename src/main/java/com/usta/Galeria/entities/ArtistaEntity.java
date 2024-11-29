package com.usta.Galeria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "ARTISTAS")
public class ArtistaEntity implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artista")
    private Long idArtista;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre_aut", length = 40, nullable = false)
    private String nombreArt;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "apellido_aut", length = 40, nullable = false)
    private String apellidoArt;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "pais_art", length = 50, nullable = false)
    private String paisArt;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fechaNac", nullable = false)
    private Date fechaNac;

    @NotNull
    @Column(name = "disciplina_artista", length = 50, nullable = false)
    private String disciplinaArtista;

    @ManyToMany(mappedBy = "artistas")
    private Set<ExposicionEntity> exposiciones;

    @Override
    public String toString() {
        return nombreArt + " " + apellidoArt;
    }
}
