package repository;

import entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Sepet ID'sine göre CartItem'ları getir
    List<CartItem> findByCartId(Long cartId);

    // Ürün ID'sine göre CartItem'ları getir (İlgili ürüne ait tüm sepet öğelerini al)
    List<CartItem> findByProductId(Long productId);
}
