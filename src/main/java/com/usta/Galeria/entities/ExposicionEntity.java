package com.usta.Galeria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "EXPOSICIONES")
public class ExposicionEntity implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exposicion")
    private Long idExposicion;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo_exp", length = 50, unique = true, nullable = false)
    private String tituloExp;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descripcion_exp", length = 250, nullable = false)
    private String descripcionExp;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fechaInicio", nullable = false)
    private Date fechaInicio;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fechaFin", nullable = false)
    private Date fechaFin;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ubicacion_exp", length = 50, nullable = false)
    private String ubicacionExp;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "artistas_exposiciones",
            joinColumns = @JoinColumn(name = "id_exposicion", referencedColumnName = "id_exposicion"),
            inverseJoinColumns = @JoinColumn(name = "id_artista", referencedColumnName = "id_artista"))
    private Collection<ArtistaEntity> artistas;

    @Override
    public String toString() {
        return "Exposicion: " + tituloExp;
    }
}
