package com.springboot.learning.kit.service;

import com.springboot.learning.kit.dto.request.OrderRequest;
import com.springboot.learning.kit.exception.OrderValidationException;
import com.springboot.learning.kit.validator.CustomerDetailsValidator;
import com.springboot.learning.kit.validator.OrderTypeValidator;
import com.springboot.learning.kit.validator.OrderUUIDValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidationService {

    private final CustomerDetailsValidator customerDetailsValidator;

    private final OrderTypeValidator orderTypeValidator;
    private final OrderUUIDValidator orderUUIDValidator;

    /**
     * Validates the given order.
     *
     * @param orderRequest the order to be validated
     * @throws OrderValidationException if the order is invalid
     */
    public void validateOrder(OrderRequest orderRequest) {
        orderTypeValidator.validate(orderRequest.getOrderType());
        this.validateOrderUUID(orderRequest.getUUID());
        customerDetailsValidator.validate(orderRequest.getCustomerDetails());
    }

    /**
     * Validates the order UUID.
     *
     * @param orderUUID the UUID of the order to be validated
     * @throws OrderValidationException if the order UUID is invalid
     */
    public void validateOrderUUID(Long orderUUID) {
        orderUUIDValidator.validate(orderUUID);
    }
}
