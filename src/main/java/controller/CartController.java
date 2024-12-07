package controller;

import dto.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private CartService cartService;

    // Müşteriye ait sepeti al
    @GetMapping("/{customerId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long customerId) {
        CartDto cartDto = cartService.getCartForCustomer(customerId);
        return ResponseEntity.ok(cartDto);
    }

    // Sepete ürün ekle
    @PostMapping("/{customerId}/add")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long customerId,
                                                    @RequestParam Long productId,
                                                    @RequestParam int quantity) {
        CartDto cartDto = cartService.addProductToCart(customerId, productId, quantity);
        return ResponseEntity.ok(cartDto);
    }

    // Sepetten ürün çıkar
    @DeleteMapping("/{customerId}/remove/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable Long customerId,
                                                         @PathVariable Long productId) {
        CartDto cartDto = cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok(cartDto);
    }

    // Sepeti boşalt
    @DeleteMapping("/{customerId}/empty")
    public ResponseEntity<Void> emptyCart(@PathVariable Long customerId) {
        cartService.emptyCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
