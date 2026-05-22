package com.icomer.icomers.DTO;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
}