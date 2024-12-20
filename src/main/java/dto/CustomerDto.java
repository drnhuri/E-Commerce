package dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String name;
    private String email;
    private CartDto cart; // Sepet bilgisi de eklenebilir.
}
