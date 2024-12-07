package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carts")
public class Cart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    @Column(name = "total_price")
    private Double totalPrice; // Toplam fiyatı tutmak için

    // Sepetin toplam fiyatını hesaplayan metod
    public Double calculateTotalPrice() {
        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
    }

    // Sepetin fiyatını güncelle
    public void updateTotalPrice() {
        this.totalPrice = calculateTotalPrice();
    }

    // Sepeti boşaltma
    public void emptyCart() {
        cartItems.clear();
        this.totalPrice = 0.0; // Sepet boşsa fiyatı sıfırla
    }
}
