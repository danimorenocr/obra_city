package com.usta.Galeria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "OBRAS")
public class ObraEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obra")
    private Long idObra;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo_obra", length = 50, unique = true, nullable = false)
    private String tituloObra;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descripcion_obra", length = 250, nullable = false)
    private String descripcionObra;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tecnica_obra", length = 100, nullable = false)
    private String tecnicaObra;

    // Asegúrate de permitir una longitud adecuada para URLs largas
    @Size(min = 1, max = 500)  // Considera un tamaño adecuado para URLs largas
    @Column(name = "foto_obra", length = 500, nullable = false) // Puede ser nullable si la imagen no es obligatoria
    private String fotoObra;

    @NotNull
    @JoinColumn(name = "id_artista", referencedColumnName = "id_artista")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ArtistaEntity idArtista;
}   