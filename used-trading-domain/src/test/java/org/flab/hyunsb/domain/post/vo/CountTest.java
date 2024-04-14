package org.flab.hyunsb.domain.post.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.flab.hyunsb.domain.post.exception.PostConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CountTest {

    @Test
    @DisplayName("[카운트 증가 성공 테스트] 카운트를 1 만큼 증가시킨다.")
    public void increaseCount_successTest() {
        // Given
        int originCount = 1;
        Count count = new Count(originCount);
        // When
        count.increaseCount();
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(2, count.getCount())
        );
    }

    @Test
    @DisplayName("[카운트 증가 성공 테스트] 카운트를 일정 수 만큼 증가시킨다.")
    public void increaseCount_successTest_withIncreaseNumber() {
        // Given
        int originCount = 1;
        Count count = new Count(originCount);
        int numberForIncreaseCount = 3;
        // When
        count.increaseCount(numberForIncreaseCount);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(4, count.getCount())
        );
    }
    @Test
    @DisplayName("[카운트 증감 실패 테스트] 카운트 변경 수치로 음수를 전달한 경우 예외를 발생한다.")
    public void changeCount_failureTest_requestNegativeNumber() {
        // Given
        int originCount = 0;
        Count count = new Count(originCount);
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(PostConstraintException.class, () ->
                count.decreaseCount(-1))
        );
    }

    @Test
    @DisplayName("[카운트 감소 성공 테스트] 카운트를 1 만큼 감소시킨다.")
    public void decreaseCount_successTest() {
        // Given
        int originCount = 1;
        Count count = new Count(originCount);
        // When
        count.decreaseCount();
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(0, count.getCount())
        );
    }

    @Test
    @DisplayName("[카운트 감소 성공 테스트] 카운트를 일정 수 만큼 감소시킨다.")
    public void decreaseCount_successTest_withIncreaseNumber() {
        // Given
        int originCount = 3;
        Count count = new Count(originCount);
        int numberForIncreaseCount = 2;
        // When
        count.decreaseCount(numberForIncreaseCount);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(1, count.getCount())
        );
    }

    @Test
    @DisplayName("[카운트 감소 실패 테스트] 카운트 감소 시 카운트가 0 미만이 되는 경우 예외를 발생한다.")
    public void decreaseCount_failureTest_countIsSmallerThanMinimum() {
        // Given
        int originCount = 0;
        Count count = new Count(originCount);
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(PostConstraintException.class, count::decreaseCount)
        );
    }
}