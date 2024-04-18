package org.flab.hyunsb.framework.persistence.entity.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.domain.post.vo.Status;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    RESERVED("예약 중"),
    SOLD("판매 완료"),
    SELLING("판매 중"),
    HIDE("숨김");

    private final String status;

    public static PostStatus valueOf(Status status) {
        return PostStatus.valueOf(status.name());
    }
}
