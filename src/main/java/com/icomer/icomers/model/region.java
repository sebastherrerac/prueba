package com.icomer.icomers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "regiones")
public class region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegion;

    @NotBlank(message = "El nombre de la region no puede estar vacio")
    @Column(nullable = false, length = 50)
    private String nombreRegion;

    @Column(nullable = false)
    private Boolean activo;

}