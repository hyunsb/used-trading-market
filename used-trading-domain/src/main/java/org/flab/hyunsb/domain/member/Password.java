package org.flab.hyunsb.domain.member;

import lombok.Getter;
import org.flab.hyunsb.domain.exception.PasswordConstraintException;
import org.flab.hyunsb.domain.member.encryptor.Encryptor;
import org.flab.hyunsb.domain.member.encryptor.PasswordEncryptor;

@Getter
public class Password {

    private static final Encryptor ENCRYPTOR = PasswordEncryptor.getInstance();

    private final String password;

    private Password(String password) {
        validatePassword(password);
        this.password = password;
    }

    private static void validatePassword(String password) {
        if (password.isBlank()) {
            throw new PasswordConstraintException();
        }
    }

    public static Password valueOf(String password) {
        return new Password(password);
    }

    public static Password generateWithEncrypting(String password) {
        validatePassword(password);
        String encryptedPassword = ENCRYPTOR.encrypt(password);
        return new Password(encryptedPassword);
    }

    public boolean isMatch(String password) {
        String encryptedPassword = ENCRYPTOR.encrypt(password);
        return this.password.equals(encryptedPassword);
    }
}
