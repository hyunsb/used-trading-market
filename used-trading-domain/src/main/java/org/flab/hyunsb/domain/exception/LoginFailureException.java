package org.flab.hyunsb.domain.exception;

import static org.flab.hyunsb.domain.exception.ErrorMessage.LOGIN_FAILED;

public class LoginFailureException extends MemberAuthException {

    public LoginFailureException() {
        super(LOGIN_FAILED);
    }
}
