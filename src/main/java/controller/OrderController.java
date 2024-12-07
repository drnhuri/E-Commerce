package controller;

import dto.OrderDto;
import entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Yeni sipariş oluşturma
    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam Long customerId, @RequestBody List<OrderItem> orderItems) {
        OrderDto orderDto = orderService.placeOrder(customerId, orderItems);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    // Sipariş koduna göre sipariş getirme
    @GetMapping("/{orderCode}")
    public ResponseEntity<OrderDto> getOrderForCode(@PathVariable String orderCode) {
        OrderDto orderDto = orderService.getOrderForCode(orderCode);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    // Müşterinin tüm siparişlerini listeleme
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersForCustomer(@PathVariable Long customerId) {
        List<OrderDto> orderDtos = orderService.getAllOrdersForCustomer(customerId);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    // Sepete ürün ekleme
    @PostMapping("/cart/add")
    public ResponseEntity<Void> addProductToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        orderService.addProductToCart(customerId, productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Siparişte ürün güncelleme
    @PutMapping("/{orderId}/update-product")
    public ResponseEntity<Void> updateProductInOrder(@PathVariable Long orderId, @RequestParam Long productId, @RequestParam int quantity) {
        orderService.updateProductInOrder(orderId, productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Siparişi iptal etme
    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
