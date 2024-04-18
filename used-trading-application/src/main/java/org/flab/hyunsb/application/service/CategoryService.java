package org.flab.hyunsb.application.service;

import static org.flab.hyunsb.application.exception.message.RegionErrorMessage.INVALID_REGION_ID;

import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.application.exception.constraint.ConstraintException;
import org.flab.hyunsb.application.output.CategoryOutputPort;
import org.flab.hyunsb.application.validator.CategoryValidator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService implements CategoryValidator {

    private final CategoryOutputPort categoryOutputPort;

    @Override
    public void validateCategoryId(Long id) {
        categoryOutputPort.findIdById(id).orElseThrow(() ->
            new ConstraintException(INVALID_REGION_ID.getMessage()));
    }
}
