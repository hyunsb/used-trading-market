package org.flab.hyunsb.framework.persistence.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.application.output.CategoryOutputPort;
import org.flab.hyunsb.framework.persistence.entity.post.CategoryEntity;
import org.flab.hyunsb.framework.persistence.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryOutputPort {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Long> findIdById(Long id) {
        return categoryRepository.findById(id)
            .map(CategoryEntity::getId)
            .or(Optional::empty);
    }
}
