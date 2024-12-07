package service;

import dto.OrderDto;
import entity.Order;
import entity.OrderItem;
import entity.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import repository.OrderRepository;
import repository.CustomerRepository;
import repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // Yeni bir sipariş oluşturma
    public OrderDto placeOrder(Long customerId, List<OrderItem> orderItems) {
        // Müşteri var mı kontrolü
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Sipariş oluşturma
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);
        order.calculateTotalPrice(); // Toplam fiyat hesaplanır

        // Sipariş kaydedilir
        Order savedOrder = orderRepository.save(order);

        // OrderDto dönüşü
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    // Sipariş koduna göre sipariş getirme
    public OrderDto getOrderForCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Dönüşüm
        return modelMapper.map(order, OrderDto.class);
    }

    // Müşteriye ait tüm siparişleri getirme
    public List<OrderDto> getAllOrdersForCustomer(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        // Dönüşüm
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    public void addProductToCart(Long customerId, Long productId, int quantity) {
        // Sepet ve ürünle ilgili işlemler yapılacak, sepete ürün ekleme
    }

    public void updateProductInOrder(Long orderId, Long productId, int newQuantity) {
        // Sipariş güncellenebilir
    }

    public void cancelOrder(Long orderId) {
        // Siparişin iptal edilmesi veya silinmesi
    }
}
