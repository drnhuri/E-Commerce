package controller;

import dto.OrderItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.OrderItemService;
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    //Siparişe ait ürünleri getirir
    @GetMapping("/{orderId]")
    public ResponseEntity<List<OrderItemDto>>getOrderItemsByOrderId(@PathVariable Long orderId){
        List<OrderItemDto> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }
}
