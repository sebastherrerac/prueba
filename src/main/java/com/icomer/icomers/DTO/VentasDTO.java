package com.icomer.icomers.DTO;

import lombok.Data;
import java.util.List;

@Data
public class VentasDTO {
    private Long clienteId;
    private List<Long> figurasIds;
    private Long tipoPagoId;
    private String tipoEnvio;
    private Double total;
}