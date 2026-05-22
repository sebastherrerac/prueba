package com.icomer.icomers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ventas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "La venta debe tener un cliente")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "venta_detalle",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "figura_id")
    )
    private List<Figuras> figuras;

    @ManyToOne
    @JoinColumn(name = "tipopago_id")
    @NotNull(message = "Debe seleccionar un método de pago")
    private tipopago tipoPago;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    @NotNull(message = "El tipo de envío es obligatorio")
    private String tipoEnvio; 

    private Double total;

    @PrePersist
    protected void onCreate() {
        this.fechaVenta = LocalDateTime.now();
    }
}