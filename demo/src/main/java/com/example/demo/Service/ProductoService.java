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
        Producto existente = repo.findById(id).orElse(null);
        if (existente == null) return null;

        existente.setNombre(p.getNombre());
        existente.setCategoria(p.getCategoria());
        existente.setPrecio(p.getPrecio());
        existente.setImagen(p.getImagen());

        return repo.save(existente);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
