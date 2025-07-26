package com.springboot.learning.kit.validator;

import com.springboot.learning.kit.dto.request.CustomerDetailsRequest;
import com.springboot.learning.kit.exception.OrderValidationException;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.AddressException;

@Component
public class CustomerDetailsValidator implements Validator<CustomerDetailsRequest> {

    @Override
    public void validate(CustomerDetailsRequest customerDetails) throws OrderValidationException {
        if (customerDetails == null) {
            throw new OrderValidationException("Invalid customer details");
        }
        validateName(customerDetails.getName());
        validatePhone(customerDetails.getPhone());
        validateEmail(customerDetails.getEmail());
    }

    private void validateName(String name) throws OrderValidationException {
        if (name == null || name.isEmpty()) {
            throw new OrderValidationException("Customer name cannot be null or empty");
        }
    }

    private void validatePhone(String phone) throws OrderValidationException {
        if (!phone.matches("^\\+?(\\d{1,3})?[-.\\s]?(\\(?\\d{3}\\)?[-.\\s]?)?(\\d[-.\\s]?){6,9}\\d$")) {
            throw new OrderValidationException("Invalid phone number format: " + phone);
        }
    }

    private void validateEmail(String email) throws OrderValidationException {
        try{
            new InternetAddress(email).validate();
        } catch (AddressException e) {
            throw new OrderValidationException("Invalid email format: " + email);
        }
    }
}