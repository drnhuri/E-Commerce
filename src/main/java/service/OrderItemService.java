package service;

import dto.OrderItemDto;
import entity.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import repository.OrderItemRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public OrderItemService(OrderItemRepository orderItemRepository, ModelMapper modelMapper){
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    //Sipariş koduna göre ürün detaylarını getir
    public List<OrderItemDto>getOrderItemByOrderId(Long orderId){
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream().map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class)).collect(Collectors.toList());
    }
}
