package com.example.onestep.validator;

import com.example.onestep.repository.KhachHangRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }

        // Check if email already exists
        return !khachHangRepository.existsByEmail(email.trim().toLowerCase());
    }
}
