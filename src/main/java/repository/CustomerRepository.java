package repository;

import entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    // Müşteriyi e-posta adresine göre bulma
    Optional<Customer> findByEmail(String email);

    // Müşteri ID'sine göre sepeti getirme
    Optional<Customer> findByIdWithCart(Long customerId);
}
