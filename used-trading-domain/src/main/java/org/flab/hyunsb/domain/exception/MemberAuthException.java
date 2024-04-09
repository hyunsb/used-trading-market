package org.flab.hyunsb.domain.exception;

public class MemberAuthException extends RuntimeException {

    public MemberAuthException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
