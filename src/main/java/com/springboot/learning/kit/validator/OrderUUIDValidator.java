package com.springboot.learning.kit.validator;

import com.springboot.learning.kit.exception.OrderValidationException;
import org.springframework.stereotype.Component;

@Component
public class OrderUUIDValidator implements Validator<Long> {

    @Override
    public void validate(Long orderUUID) throws OrderValidationException {
        if (orderUUID == null || orderUUID <= 0) {
            throw new OrderValidationException("Invalid order UUID");
        }
    }
}