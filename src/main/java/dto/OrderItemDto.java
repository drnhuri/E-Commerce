package dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private Double priceAtOrder;  // Sipariş anındaki fiyat
    private int quantity;         // Sipariş edilen miktar
    private String productName;   // Ürün adı
    private String productDescription; //Ürün açıklaması
}
