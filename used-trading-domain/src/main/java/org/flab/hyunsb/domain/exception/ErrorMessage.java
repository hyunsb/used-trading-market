package org.flab.hyunsb.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    LOGIN_FAILED("로그인 실패: 올바른 정보를 입력하세요."),
    PASSWORD_NOT_MATCHED("비밀번호가 일치하지 않습니다.");

    private final String message;
}
