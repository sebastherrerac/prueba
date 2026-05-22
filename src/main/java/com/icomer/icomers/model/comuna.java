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
@Table(name = "comunas")
public class comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomuna;

    @NotBlank(message = "El nombre de la comuna no puede estar vacio")
    @Column(nullable = false, length = 50)
    private String nombrecomuna;

    @Column(nullable = false)
    private Boolean activocomuna;


    @NotNull(message = "El ID de la region es obligatorio")
    private Integer regionid; 


}