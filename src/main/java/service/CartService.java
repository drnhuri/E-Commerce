package service;

import dto.CartDto;
import entity.Cart;
import entity.CartItem;
import entity.Customer;
import entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import repository.CartRepository;
import repository.CustomerRepository;
import repository.ProductRepository;

@Service
public class CartService {

    private CartRepository cartRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository, ModelMapper modelMapper){
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    // Müşteriye ait sepete erişim
    public CartDto getCartForCustomer(Long customerId) {
        // Müşteri var mı kontrolü
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));

        // Sepet var mı kontrolü
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

        // Cart nesnesini CartDto'ya dönüştürme
        return modelMapper.map(cart, CartDto.class);
    }

    // Sepete ürün ekleme
    public CartDto addProductToCart(Long customerId, Long productId, int quantity) {
        // Müşteri var mı kontrolü
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));

        // Sepet var mı kontrolü, yoksa yeni sepet oluşturulur
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElse(new Cart());

        // Ürün var mı kontrolü
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        // Ürün sepete ekleniyor
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);

        cart.getCartItems().add(cartItem);

        // Sepetin toplam fiyatını hesapla
        double totalPrice = cart.calculateTotalPrice();

        // Sepetin toplam fiyatını DTO'da güncelle
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        cartDto.setTotalPrice(totalPrice);

        // Sepeti kaydet
        cartRepository.save(cart);

        // Güncellenen sepeti DTO'ya dönüştürüp döndür
        return cartDto;
    }

    // Sepetten ürün çıkarma
    public CartDto removeProductFromCart(Long customerId, Long productId) {
        // Müşteri ve sepeti bulma
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

        // Sepetten ürün çıkarma
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));

        // Sepetin toplam fiyatını tekrar hesapla
        double totalPrice = cart.calculateTotalPrice();

        // DTO'yu güncelle
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        cartDto.setTotalPrice(totalPrice);

        // Güncellenen sepeti kaydet
        cartRepository.save(cart);

        // DTO'ya dönüştürüp döndür
        return cartDto;
    }

    // Sepeti boşaltma
    public void emptyCart(Long customerId) {
        // Müşteri ve sepeti bulma
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

        // Sepeti boşaltma
        cart.emptyCart();

        // Sepeti kaydet
        cartRepository.save(cart);
    }
}
