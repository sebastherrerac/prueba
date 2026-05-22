package com.icomer.icomers.service;

import com.icomer.icomers.model.Ventas;
import com.icomer.icomers.repository.VentasRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class VentasServices {

    @Autowired
    private VentasRepository ventasRepository;

    //historial ventas
    public List<Ventas> listarVentas() {
        log.info("SERVICE:Listando todas las ventas");
        return ventasRepository.findAll();
    }

    // Registrar ventas
    public Ventas guardarVenta(Ventas venta) {
        log.info("SERVICE:Guardando venta con total: {}", venta.getTotal());
        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(LocalDateTime.now());
        }
        return ventasRepository.save(venta);
    }
    
    // Buscar venta por id
    public Ventas buscarPorId(Long id) {
        log.info("SERVICE:Buscando venta por ID: {}", id);
        return ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    public void eliminar(Long id) {
        log.info("SERVICE:Eliminando venta con ID: {}", id);
        ventasRepository.deleteById(id);
    }

    public Ventas actualizar(Long id, Ventas nuevosDatos) {
        log.info("SERVICE:Actualizando venta con ID: {}", id);
        Ventas ventaExistente = ventasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        
        ventaExistente.setTipoEnvio(nuevosDatos.getTipoEnvio());
        ventaExistente.setTotal(nuevosDatos.getTotal());
        
        if(nuevosDatos.getFechaVenta() != null) {
            ventaExistente.setFechaVenta(nuevosDatos.getFechaVenta());
        }

        log.info("SERVICE:Venta actualizada con ID: {}", id);
        return ventasRepository.save(ventaExistente);
    }

}