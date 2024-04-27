package org.flab.hyunsb.domain.member;

import static org.flab.hyunsb.domain.exception.ErrorMessage.PASSWORD_NOT_MATCHED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.flab.hyunsb.domain.exception.LoginFailureException;
import org.flab.hyunsb.domain.exception.MemberAuthException;

@Getter
@AllArgsConstructor
public class Member {

    private Long id;
    private Long regionId;
    private String email;
    private Password password;
    private String nickname;

    public static Member from(MemberForCreate memberForCreate) {
        return new Member(
            null,
            memberForCreate.regionId(),
            memberForCreate.email(),
            Password.generateWithEncrypting(memberForCreate.password()),
            memberForCreate.nickname()
        );
    }

    public Member tryToLogin(MemberForLogin memberForLogin) throws LoginFailureException {
        if (!isMatchingEmail(memberForLogin.email()) ||
            !password.isMatch(memberForLogin.password())) {
            throw new LoginFailureException();
        }
        return this;
    }

    private boolean isMatchingEmail(String email) {
        return this.email.equals(email);
    }

    public String getPassword() {
        return password.getPassword();
    }

    public Member changePassword(String currentPassword, String newPassword) throws MemberAuthException {
        if (!password.isMatch(currentPassword)) {
            throw new MemberAuthException(PASSWORD_NOT_MATCHED);
        }
        password = Password.generateWithEncrypting(newPassword);
        return this;
    }
}
