package org.flab.hyunsb.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.flab.hyunsb.domain.exception.LoginFailureException;

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

    public void tryToLogin(MemberForLogin memberForLogin) {
        if (!isMatchingEmail(memberForLogin.email()) ||
            !password.isMatch(memberForLogin.password())) {
            throw new LoginFailureException();
        }
    }

    private boolean isMatchingEmail(String email) {
        return this.email.equals(email);
    }

    public String getPassword() {
        return password.getPassword();
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (!password.isMatch(currentPassword)) {
            throw new IllegalArgumentException();
        }
        password = Password.generateWithEncrypting(newPassword);
    }
}
