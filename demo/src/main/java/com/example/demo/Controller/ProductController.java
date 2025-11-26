package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> getAll() {
        return productoService.getAll();
    }

    @GetMapping("/{id}")
    public Producto getById(@PathVariable String id) {
        return productoService.getById(id);
    }

    @PostMapping
    public Producto create(@RequestBody Producto p) {
        return productoService.create(p);
    }
}
