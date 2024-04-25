package org.flab.hyunsb.application.service.category;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import org.flab.hyunsb.application.exception.constraint.ConstraintException;
import org.flab.hyunsb.application.exception.message.RegionErrorMessage;
import org.flab.hyunsb.application.output.CategoryOutputPort;
import org.flab.hyunsb.application.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryOutputPort categoryOutputPort;

    @Test
    @DisplayName("[카테고리 식별자 검증 성공 테스트] 유효한 식별자가 주어진 경우 void를 반환한다.")
    public void validateCategoryId_successTest() {
        // Given
        Long categoryId = 1L;
        Mockito.when(categoryOutputPort.findIdById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(1L));
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertDoesNotThrow(() ->
                categoryService.validateCategoryId(categoryId))
        );
    }

    @Test
    @DisplayName("[카테고리 식별자 검증 실패 테스트] 유효하지 않은 식별자가 주어진 경우 예외를 발생한다.")
    public void validateCategoryId_failureTest() {
        // Given
        Long categoryId = 1L;
        Mockito.when(categoryOutputPort.findIdById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.empty());
        // When & Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> categoryService.validateCategoryId(categoryId))
                .isInstanceOf(ConstraintException.class)
                .hasMessage(RegionErrorMessage.INVALID_REGION_ID.getMessage())
        );
    }
}
