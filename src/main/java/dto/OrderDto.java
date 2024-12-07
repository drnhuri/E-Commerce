package dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String orderCode;
    private Long customerId;
    private List<OrderItemDto> orderProducts;
    private Double totalPrice; // Siparişin toplam fiyatı
}
