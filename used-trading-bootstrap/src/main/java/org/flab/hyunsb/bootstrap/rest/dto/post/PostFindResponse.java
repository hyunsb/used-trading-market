package org.flab.hyunsb.bootstrap.rest.dto.post;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.flab.hyunsb.framework.persistence.entity.post.PostEntity;
import org.flab.hyunsb.framework.persistence.entity.post.PostStatus;

@Getter
@AllArgsConstructor
public class PostFindResponse {

    private final Long postId;
    private final Long memberId;
    private final Long regionId;
    private final Long categoryId;

    private final PostStatus status;
    private final String title;
    private final String description;
    private final int price;

    private final String thumbnail;
    private final int viewCount;
    private final int likeCount;

    private final LocalDateTime registrationAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static PostFindResponse from(PostEntity post) {
        return new PostFindResponse(
            post.getId(), post.getMember().getId(),
            post.getRegion().getId(), post.getCategory().getId(),
            post.getStatus(), post.getTitle(), post.getDescription(),
            post.getPrice(), post.getThumbnail(),
            post.getViewCount(), post.getLikeCount(),
            post.getRegistrationAt(), post.getCreatedAt(), post.getUpdatedAt()
        );
    }
}
