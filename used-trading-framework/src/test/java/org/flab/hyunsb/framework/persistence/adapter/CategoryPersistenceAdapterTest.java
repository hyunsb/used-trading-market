package org.flab.hyunsb.framework.persistence.adapter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.flab.hyunsb.framework.persistence.entity.post.CategoryEntity;
import org.flab.hyunsb.framework.persistence.repository.CategoryRepository;
import org.flab.hyunsb.framework.repository.annotation.RepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class CategoryPersistenceAdapterTest {

    @Autowired
    private CategoryPersistenceAdapter categoryPersistenceAdapter;

    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp(@Autowired CategoryRepository categoryRepository) {
        CategoryEntity testCategory = new CategoryEntity(1L, "전자제품");
        categoryEntity = categoryRepository.save(testCategory);
    }

    @Test
    @DisplayName("[카테고리 아이디 조회 성공 테스트] 주어진 식별자로 검색한 카테고리의 Id를 반환한다.")
    public void findIdByCategoryId_successTest_validId() {
        // Given & When
        Optional<Long> optionalId = categoryPersistenceAdapter.findIdById(categoryEntity.getId());
        // Then
        Assertions.assertAll(
            () -> Assertions.assertTrue(optionalId.isPresent()),
            () -> Assertions.assertEquals(categoryEntity.getId(), optionalId.get())
        );
    }

    @Test
    @DisplayName("[카테고리 아이디 조회 성공 테스트] 식별자로 검색한 결과가 없는 경우 빈 Optioanl을 반환한다.")
    public void findIdByCategoryId_successTest_invalidId() {
        // Given & When
        Long invalidId = categoryEntity.getId() + 1;
        Optional<Long> optionalId = categoryPersistenceAdapter.findIdById(invalidId);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertFalse(optionalId.isPresent())
        );
    }
}