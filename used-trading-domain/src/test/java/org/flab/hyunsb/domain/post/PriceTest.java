package org.flab.hyunsb.domain.post;

import org.flab.hyunsb.domain.post.exception.PostConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 1_000_000_000})
    @DisplayName("[Price 생성 실패 테스트] 범위를 벗어나는 가격이 주어진 경우 예외를 발생한다.")
    public void createPrice_failureTest(int won) {
        // Given & When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(PostConstraintException.class, () ->
                new Price(won))
        );
    }
}