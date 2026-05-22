package com.icomer.icomers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipos_pago")
public class tipopago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoPago;

    @NotBlank(message = "El nombre del tipo de pago es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombreTipoPago;

    public Object getActivo() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getActivo'");
    }

    public void setActivo(Object activo) {
        
        throw new UnsupportedOperationException("Unimplemented method 'setActivo'");
    }
}