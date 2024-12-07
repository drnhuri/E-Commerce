package dto;

import lombok.Data;
import java.util.List;

@Data

public class CartDto {
    private Long id;
    private List<ProductDto> products;
    private Double totalPrice; // Sepet toplam fiyatı
}
