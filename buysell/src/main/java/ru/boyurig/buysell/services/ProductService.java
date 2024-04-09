package ru.boyurig.buysell.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.boyurig.buysell.models.Product;
import ru.boyurig.buysell.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts(String title) {
        if(title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        log.info("Saving new {}", product);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product toDelete = productRepository.findById(id).orElse(null);

        if(toDelete != null){
            productRepository.deleteById(id);
            log.info("Delete product {}", toDelete);
        } else {
            log.info("trying to delete not exist product id:{} ", id);
        }
    }

    public Product getProductById(Long id) {
            return productRepository.findById(id).orElse(null);
    }
}
