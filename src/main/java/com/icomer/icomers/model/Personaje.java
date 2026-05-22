package com.icomer.icomers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del personaje es obligatorio")
    private String nombre;

    // Hatsune Miku, Naruto, Goku, etc.
    private String franquicia; 
}