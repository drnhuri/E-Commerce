package repository;

import entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Ürün ismine göre listeleme
    List<Product> findByNameContaining(String name);

    // Ürün stok durumu kontrolü
    boolean existsByIdAndStockGreaterThan(Long productId, int stock);
}
