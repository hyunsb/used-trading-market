package org.flab.hyunsb.domain.exception;

public class PasswordNotMatchedException extends MemberAuthException {

    public PasswordNotMatchedException() {
        super(ErrorMessage.PASSWORD_NOT_MATCHED);
    }
}
