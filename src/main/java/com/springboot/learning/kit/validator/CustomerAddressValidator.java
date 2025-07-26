package com.springboot.learning.kit.validator;

import com.springboot.learning.kit.dto.request.CustomerAddressRequest;
import com.springboot.learning.kit.exception.OrderValidationException;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressValidator implements Validator<CustomerAddressRequest> {

    @Override
    public void validate(CustomerAddressRequest customerDetails) throws OrderValidationException {
        if (customerDetails == null) {
            throw new OrderValidationException("Invalid customer address");
        }
        validateStreet(customerDetails.getStreet());
        validateCity(customerDetails.getCity());
        validateZIP(customerDetails.getZipCode());
        validateCountry(customerDetails.getCountry());
    }

    private void validateStreet(String name) throws OrderValidationException {
        if (name == null || name.isEmpty()) {
            throw new OrderValidationException("Customer street name cannot be null or empty");
        }
    }

    private void validateCity(String name) throws OrderValidationException {
        if (name == null || name.isEmpty()) {
            throw new OrderValidationException("Customer city name cannot be null or empty");
        }
    }

    private void validateZIP(String zip) throws OrderValidationException {
        if (zip == null || zip.isEmpty()) {
            throw new OrderValidationException("Customer zip code cannot be null or empty");
        }
        if(!zip.matches("\\d{5,6}")){
            throw new OrderValidationException("Customer zip code is not valid");
        }
    }

    private void validateCountry(String name) throws OrderValidationException {
        if (name == null || name.isEmpty()) {
            throw new OrderValidationException("Customer country name cannot be null or empty");
        }
    }
}