package org.flab.hyunsb.framework.persistence.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flab.hyunsb.domain.member.Member;
import org.flab.hyunsb.domain.member.Password;
import org.flab.hyunsb.framework.persistence.entity.basetime.BaseTimeEntity;
import org.flab.hyunsb.framework.persistence.entity.region.RegionEntity;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity(name = "member")
public class MemberEntity extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private MemberEntity(Long id) {
        this.id = id;
    }

    public Member toDomain() {
        return new Member(id, region.getId(), email, Password.valueOf(password), nickname);
    }

    public static MemberEntity from(Member member) {
        return new MemberEntity(
            member.getId(),
            RegionEntity.valueOf(member.getRegionId()),
            member.getEmail(),
            member.getPassword(),
            member.getNickname()
        );
    }

    public static MemberEntity valueOf(Long id) {
        return new MemberEntity(id);
    }
}
