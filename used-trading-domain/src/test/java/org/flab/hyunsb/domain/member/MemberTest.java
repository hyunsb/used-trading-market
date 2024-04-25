package org.flab.hyunsb.domain.member;

import org.flab.hyunsb.domain.exception.LoginFailureException;
import org.flab.hyunsb.domain.exception.MemberAuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberTest {

    private static final Long TEST_REGION_ID = 1L;
    private static final String TEST_EMAIL = "email";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "nickname";

    private MemberForCreate memberForCreate;

    @BeforeEach
    void setUp() {
        memberForCreate = new MemberForCreate(
            TEST_REGION_ID,
            TEST_EMAIL,
            TEST_PASSWORD,
            TEST_NICKNAME
        );
    }

    @Test
    @DisplayName("[회원 생성 성공 테스트] 파라매터로 넘어온 MemberForCreate 객체의 비밀번호를 암호화한 뒤 Member 객체로 파싱한다.")
    public void memberForCreateParsing_successTest() {
        // Given & When
        Member member = Member.from(memberForCreate);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertNotEquals(member.getPassword(), memberForCreate.password())
        );
    }

    @Nested
    @DisplayName("회원 도메인 로그인 시도 단위 테스트")
    class MemberTryToLogin {

        @Test
        @DisplayName("[로그인 시도 성공 테스트] 회원 도메인의 정보와 일치하는 정보인 경우 매서드 호출을 종료한다.")
        public void memberTryToLogin_successTest() {
            // Given
            Member member = Member.from(memberForCreate);
            MemberForLogin memberForLogin = new MemberForLogin(TEST_EMAIL, TEST_PASSWORD);
            // When & Then
            Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> member.tryToLogin(memberForLogin))
            );
        }

        @Test
        @DisplayName("[로그인 시도 실패 테스트] 회원 정보와 일치하지 않는 이메일이 주어진 경우 예외를 발생한다.")
        public void memberTryToLogin_failureTest_notMatchEmail() {
            // Given
            Member member = Member.from(memberForCreate);
            MemberForLogin memberForLogin = new MemberForLogin("notMatchedEmail", TEST_PASSWORD);
            // When & Then
            Assertions.assertAll(
                () -> Assertions.assertThrows(LoginFailureException.class, () ->
                    member.tryToLogin(memberForLogin))
            );
        }

        @Test
        @DisplayName("[로그인 시도 실패 테스트] 회원 정보와 일치하지 않는 비밀번호가 주어진 경우 예외를 발생한다.")
        public void memberTryToLogin_failureTest_notMatchPassword() {
            // Given
            Member member = Member.from(memberForCreate);
            MemberForLogin memberForLogin = new MemberForLogin(TEST_EMAIL, "notMatchedPassword");
            // When & Then
            Assertions.assertAll(
                () -> Assertions.assertThrows(LoginFailureException.class, () ->
                    member.tryToLogin(memberForLogin))
            );
        }
    }

    @Nested
    @DisplayName("회원 비밀번호 변경 단위 테스트")
    class MemberChangePassword {

        @Test
        @DisplayName("[회원 비밀번호 변경 성공 테스트] 패스워드 정보가 일치하는 경우 새로운 패스워드로 변경한다.")
        public void memberChangePassword_successTest() {
            // Given
            Member member = Member.from(memberForCreate);
            String currentPassword = TEST_PASSWORD;
            String newPassword = "newPassword";
            MemberForLogin memberForLogin = new MemberForLogin(TEST_EMAIL, newPassword);
            // When
            member.changePassword(currentPassword, newPassword);
            // Then
            Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> member.tryToLogin(memberForLogin))
            );
        }

        @Test
        @DisplayName("[회원 비밀번호 변경 실패 테스트] 패스워드 정보가 일치하는 않는 경우 예외를 발생한다.")
        public void memberTryToLogin_failureTest_notMatchPassword() {
            // Given
            Member member = Member.from(memberForCreate);
            String currentPassword = "nonMatchingPassword";
            String newPassword = "newPassword";
            // When & Then
            Assertions.assertAll(
                () -> Assertions.assertThrows(MemberAuthException.class, () ->
                    member.changePassword(currentPassword, newPassword))
            );
        }
    }
}