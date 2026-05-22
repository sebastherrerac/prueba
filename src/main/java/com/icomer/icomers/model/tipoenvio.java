package com.icomer.icomers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipos_envio")
public class tipoenvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnvio;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false, length = 50)
    private String nombreEnvio;

    @NotNull(message = "El costo no puede estar vacio")
    @Column(nullable = false)
    private Double costoEnvio;

    @Column(nullable = false)
    private Boolean activoEnvio; 

}