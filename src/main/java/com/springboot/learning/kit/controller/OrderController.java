package com.springboot.learning.kit.controller;

import com.springboot.learning.kit.dto.request.OrderRequest;
import com.springboot.learning.kit.dto.response.OrderStatusResponse;
import com.springboot.learning.kit.service.OrderProcessingService;
import com.springboot.learning.kit.service.OrderStatusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.learning.kit.exception.OrderNotFoundException;
import com.springboot.learning.kit.exception.OrderValidationException;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProcessingService orderProcessingService;
    private final OrderStatusService orderStatusService;

    /**
     * Endpoint to submit an order for processing.
     *
     * @param orderRequest the order to be processed
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderProcessingService.processNewOrder(orderRequest);
            return ResponseEntity.ok(Map.of("message", "Order submitted successfully"));
        }
        catch (OrderValidationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Order validation failed: " + e.getMessage()));
        }
        catch (Exception e) {
            log.error("Error processing order: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error processing order"));
        }
    }

    /**
     * Endpoint to retrieve the order status.
     *
     * @param UUID of the order to be processed
     * @return a ResponseEntity indicating the result of the operation
     */
    @GetMapping("/status/{uuid}")
    public ResponseEntity<?> orderStatus(@PathVariable("uuid") Long uuid) {
        try {
            OrderStatusResponse status = orderStatusService.getOrderStatus(uuid);
            return ResponseEntity.ok(status);
        } catch (OrderNotFoundException e) {
            log.error("Order not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Order not found"));
        } catch (Exception e) {
            log.error("Unable to retrieve order status: {}", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Unable to retrieve order status"));
        }
    }
}
