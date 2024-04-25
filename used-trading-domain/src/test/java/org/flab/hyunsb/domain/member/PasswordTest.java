package org.flab.hyunsb.domain.member;

import org.flab.hyunsb.domain.exception.PasswordConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    @DisplayName("[비밀번호 생성 성공 테스트] 암호화를 진행하지 않고 Password 객체를 생성한다.")
    public void passwordGenerate_successTest_notEncrypting() {
        // Given
        String originPassword = "password";
        Password password = Password.valueOf(originPassword);
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(originPassword, password.getPassword())
        );
    }

    @Test
    @DisplayName("[비밀번호 생성 성공 테스트] 암호화를 진행한 뒤 Password 객체를 생성한다.")
    public void passwordGenerate_successTest_withEncrypting() {
        // Given
        String originPassword = "password";
        Password password = Password.generateWithEncrypting(originPassword);
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertNotEquals(originPassword, password.getPassword())
        );
    }

    @Test
    @DisplayName("[비밀번호 생성 실패 테스트] 생성자로 빈 문자열이 주어지는 경우 예외를 발생한다.")
    public void passwordGenerate_failureTest_givenBlankPassword() {
        // Given
        String emptyPassword = " ";
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(PasswordConstraintException.class, () ->
                Password.valueOf(emptyPassword)),
            () -> Assertions.assertThrows(PasswordConstraintException.class, () ->
                Password.generateWithEncrypting(emptyPassword))
        );
    }

    @Test
    @DisplayName("[비밀번호 매칭 성공 테스트] Password 객체의 비밀번호와 일치하는 비밀번호가 주어지는 경우 true를 반환한다.")
    public void passwordMatching_successTest() {
        // Given
        String originPassword = "password";
        Password password = Password.generateWithEncrypting(originPassword);
        // When
        boolean actual = password.isMatch(originPassword);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertTrue(actual)
        );
    }

    @Test
    @DisplayName("[비밀번호 매칭 실패 테스트] Password 객체의 비밀번호와 일치하지 않는 비밀번호가 주어지는 경우 false를 반환한다.")
    public void passwordMatching_failureTest() {
        // Given
        String originPassword = "password";
        String misMatchPassword = "mismatchPassword";
        Password password = Password.generateWithEncrypting(originPassword);
        // When
        boolean actual = password.isMatch(misMatchPassword);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertFalse(actual)
        );
    }
}
