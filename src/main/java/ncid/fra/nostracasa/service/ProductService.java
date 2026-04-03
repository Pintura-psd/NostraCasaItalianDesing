package ncid.fra.nostracasa.service;

import ncid.fra.nostracasa.model.Product;
import ncid.fra.nostracasa.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll (){
        return productRepository.findAll();
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Map<String, List<Product>> getProductsByCategoryGrouped(Long categoryId) {

        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getType() != null ? p.getType() : "otros",
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    public List<Product> findByNameContaining(String name) {
        if (name == null || name.isEmpty()) {
            return findAll();
        }
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Map<String, List<Product>> groupByTypeName(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getTypeName));
    }


}



