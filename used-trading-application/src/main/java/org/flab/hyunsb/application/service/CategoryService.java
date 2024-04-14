package org.flab.hyunsb.application.service;

import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.application.output.CategoryOutputPort;
import org.flab.hyunsb.application.validator.CategoryValidator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService implements CategoryValidator {

    private final CategoryOutputPort categoryOutputPort;

    @Override
    public void validateCategoryId(Long id) {
        categoryOutputPort.findIdById(id).orElseThrow(IllegalArgumentException::new);
    }
}
