package repository;

import entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Sipariş koduna göre sipariş bulma
    Optional<Order> findByOrderCode(String orderCode);

    // Müşterinin tüm siparişlerini listeleme
    List<Order> findByCustomerId(Long customerId);
}
