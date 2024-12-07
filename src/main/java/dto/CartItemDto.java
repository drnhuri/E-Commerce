package dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDto {

    private Long id;
    private Long cartId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
