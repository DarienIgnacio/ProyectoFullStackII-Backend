package com.example.demo.service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> getAll() {
        return repo.findAll();
    }

    public Producto getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Producto create(Producto p) {
        return repo.save(p);
    }

    public Producto update(Long id, Producto p) {
        if (!repo.existsById(id)) return null;

        p.setId(id);
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
