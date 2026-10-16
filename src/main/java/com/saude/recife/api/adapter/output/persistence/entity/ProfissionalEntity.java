package com.saude.recife.api.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
        name = "profissional",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_registro_conselho_uf",
                        columnNames = {
                                "registro_profissional",
                                "conselho",
                                "uf_registro"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profissional")
    private Long id;

    @Column(
            name = "cpf",
            nullable = false,
            unique = true,
            length = 14
    )
    private String cpf;

    @Column(
            name = "conselho",
            nullable = false,
            length = 20
    )
    private String conselho;

    @Column(
            name = "registro_profissional",
            nullable = false,
            length = 20
    )
    private String registroProfissional;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(
            name = "uf_registro",
            nullable = false,
            length = 2,
            columnDefinition = "CHAR(2)"
    )
    private String ufRegistro;

    @Column(
            name = "ativo",
            nullable = false
    )
    private boolean ativo;

    @Column(
            name = "id_usuario",
            nullable = false,
            unique = true
    )
    private Long idUsuario;
}