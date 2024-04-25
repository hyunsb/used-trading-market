package org.flab.hyunsb.domain.exception;

public class PasswordConstraintException extends MemberAuthException {

    public PasswordConstraintException() {
        super(ErrorMessage.CREATE_PASSWORD_FAILED);
    }
}
