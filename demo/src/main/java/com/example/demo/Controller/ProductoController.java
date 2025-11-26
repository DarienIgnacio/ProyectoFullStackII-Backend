package com.levelup.backend.controller;

import com.levelup.backend.entity.Product;
import com.levelup.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String categoria
    ) {

        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(service.search(search));
        }

        if (categoria != null && !categoria.isEmpty()) {
            return ResponseEntity.ok(service.getByCategory(categoria));
        }

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product producto = service.getById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product p) {
        return ResponseEntity.ok(service.save(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product p) {

        Product existing = service.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        p.setId(id);
        return ResponseEntity.ok(service.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        Product existing = service.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.ok("Producto eliminado");
    }
}
