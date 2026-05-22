package com.icomer.icomers.controller;

import com.icomer.icomers.model.Ventas;
import com.icomer.icomers.service.VentasServices;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentasController {

    @Autowired
    private VentasServices ventasService;

    @GetMapping
    public List<Ventas> obtenerTodas() {
        log.info("Controler: Peticion para listar todas las ventas");
        return ventasService.listarVentas();
    }

    @PostMapping
    public Ventas guardar(@RequestBody Ventas venta) {
        log.info("Controler: Peticion para guardar una venta");
        return ventasService.guardarVenta(venta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ventas> buscarPorId(@PathVariable Long id) {
        log.info("Controler: Peticion para buscar venta por id: {}", id);
        return ResponseEntity.ok(ventasService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ventas> actualizar(@PathVariable Long id, @RequestBody Ventas ventaDetalles) {
        log.info("Controler: Peticion para actualizar la venta con id {}", id);
        return ResponseEntity.ok(ventasService.actualizar(id, ventaDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Controler: Peticion para eliminar la venta con id {}", id);
        ventasService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}