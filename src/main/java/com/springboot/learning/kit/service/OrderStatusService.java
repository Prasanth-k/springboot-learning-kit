package com.springboot.learning.kit.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.learning.kit.domain.Order;
import com.springboot.learning.kit.domain.OrderItem;
import com.springboot.learning.kit.dto.response.OrderItemStatusResponse;
import com.springboot.learning.kit.dto.response.OrderStatusResponse;
import com.springboot.learning.kit.exception.OrderNotFoundException;
import com.springboot.learning.kit.repository.OrderItemRepository;
import com.springboot.learning.kit.repository.OrderStatusRepository;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidationService orderValidationService;

    public OrderStatusResponse getOrderStatus(Long uuid) {

        orderValidationService.validateOrderUUID(uuid);
        Order order = orderStatusRepository.findById(uuid)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with UUID: " + uuid));

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(uuid);

        if(orderItems.isEmpty()) {
            throw new OrderNotFoundException("No items found for order with UUID: " + uuid);
        }

        return OrderStatusResponse.builder()
                .orderId(uuid)
                .orderType(order.getOrderType().name())
                .items(convertEntityToDTO(orderItems))
                .build();
    }

    private List<OrderItemStatusResponse> convertEntityToDTO(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> 
                    OrderItemStatusResponse.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .status(item.getStatus())
                        .build())
                .toList();
    }

    public void saveOrderStatus() {

    }
}
