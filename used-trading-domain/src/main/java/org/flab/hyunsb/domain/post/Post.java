package org.flab.hyunsb.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.domain.post.vo.Count;
import org.flab.hyunsb.domain.post.vo.Images;
import org.flab.hyunsb.domain.post.vo.Status;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Post {

    private final Long postId;
    private final Long memberId;
    private final Long regionId;
    private final Long categoryId;

    private Status status;
    private Price price;
    private String description;
    private Images images;
    private Count viewCount;
    private Count likeCount;

    public static Post from(PostForCreate postForCreate) {
        return Post.builder()
            .postId(null)
            .memberId(postForCreate.memberId())
            .regionId(postForCreate.regionId())
            .categoryId(postForCreate.categoryId())
            .status(Status.SELLING)
            .price(postForCreate.price())
            .description(postForCreate.description())
            .images(postForCreate.images())
            .viewCount(new Count())
            .likeCount(new Count())
            .build();
    }
}
