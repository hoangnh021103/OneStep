package com.example.onestep.validator;

import com.example.onestep.repository.KhachHangRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public void initialize(UniquePhoneNumber constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }

        // Check if phone number already exists
        return !khachHangRepository.existsBySoDienThoai(phoneNumber.trim());
    }
}
