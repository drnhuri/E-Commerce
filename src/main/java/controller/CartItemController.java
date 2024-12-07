package controller;

import dto.CartItemDto;
import service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carts/{cartId}/items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // Sepete ürün ekleme
    @PostMapping
    public ResponseEntity<CartItemDto> addProductToCartItem(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        CartItemDto cartItemDto = cartItemService.addProductToCartItem(cartId, productId, quantity);
        return ResponseEntity.ok(cartItemDto);
    }

    // Sepetten ürün çıkarma
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeProductFromCartItem(@PathVariable Long cartItemId) {
        cartItemService.removeProductFromCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    // Sepet öğelerini listeleme
    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable Long cartId) {
        List<CartItemDto> cartItemDtos = cartItemService.getCartItems(cartId);
        return ResponseEntity.ok(cartItemDtos);
    }
}
