package com.example.demo.config;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ProductoRepository repo) {
        return args -> {

            if (repo.count() == 0) {
                Producto p1 = new Producto();
                p1.setNombre("PlayStation 5");
                p1.setCategoria("Consolas");
                p1.setPrecio(599000);
                p1.setImagen("https://example.com/ps5.jpg");

                Producto p2 = new Producto();
                p2.setNombre("RTX 4060 Ti");
                p2.setCategoria("Tarjetas de video");
                p2.setPrecio(499000);
                p2.setImagen("https://example.com/rtx4060.jpg");

                repo.save(p1);
                repo.save(p2);

                System.out.println("✔ Productos iniciales cargados");
            }
        };
    }
}
