package service;

import dto.CartItemDto;
import entity.CartItem;
import entity.Cart;
import entity.Product;
import repository.CartItemRepository;
import repository.CartRepository;
import repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    // Sepet öğesini ekleme
    public CartItemDto addProductToCartItem(Long cartId, Long productId, int quantity) {
        // Sepet ve ürün kontrolü
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        // Yeni CartItem oluşturma
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        // Sepet öğesini kaydet
        cartItem = cartItemRepository.save(cartItem);

        // DTO'ya dönüştürme ve fiyatları set etme
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
        cartItemDto.setPrice(BigDecimal.valueOf(product.getPrice()));  // BigDecimal'e dönüştürme
        cartItemDto.setTotalPrice(calculateTotalPrice(cartItem));  // Toplam fiyat hesaplama

        return cartItemDto;
    }

    // Sepetten ürün çıkarma
    public void removeProductFromCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Sepet öğesi bulunamadı"));
        cartItemRepository.delete(cartItem);
    }

    // Sepet öğelerini listeleme
    public List<CartItemDto> getCartItems(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems.stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
                    cartItemDto.setPrice(BigDecimal.valueOf(cartItem.getProduct().getPrice()));  // BigDecimal'e dönüştürme
                    cartItemDto.setTotalPrice(calculateTotalPrice(cartItem));  // Toplam fiyat hesaplama
                    return cartItemDto;
                })
                .collect(Collectors.toList());
    }

    // Sepet öğesinin toplam fiyatını hesapla
    private BigDecimal calculateTotalPrice(CartItem cartItem) {
        BigDecimal price = BigDecimal.valueOf(cartItem.getProduct().getPrice());
        return price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}
