package service;

import dto.ProductDto;
import entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    // Yeni ürün oluşturma
    public ProductDto createProduct(ProductDto productDto) {
        // DTO'dan Entity'ye dönüşüm
        Product product = modelMapper.map(productDto, Product.class);

        // Ürünü kaydetme
        Product savedProduct = productRepository.save(product);

        // Entity'den tekrar DTO'ya dönüşüm
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    // Entity'den DTO'ya dönüşüm
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return modelMapper.map(product, ProductDto.class);  // Entity'den DTO'ya dönüşüm
    }

    // Ürünleri listele (DTO olarak)
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))  // Entity'den DTO'ya dönüşüm
                .collect(Collectors.toList());
    }

    // Ürün güncelleme
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        modelMapper.map(productDto, existingProduct);  // DTO'dan Entity'ye dönüşüm ve güncelleme
        productRepository.save(existingProduct);
        return modelMapper.map(existingProduct, ProductDto.class);  // Güncellenmiş Entity'den DTO'ya dönüşüm
    }

    // Ürün silme
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    // Ürün arama ismiyle
    public List<ProductDto> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    // Stok kontrolü
    public boolean isStockAvailable(Long productId, int quantity) {
        return productRepository.existsByIdAndStockGreaterThan(productId, quantity);
    }
}
