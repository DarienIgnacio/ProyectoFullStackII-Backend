package com.levelup.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.levelup.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
