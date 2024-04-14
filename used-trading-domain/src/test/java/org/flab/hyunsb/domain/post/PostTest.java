package org.flab.hyunsb.domain.post;

import java.util.List;
import org.flab.hyunsb.domain.post.vo.Images;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PostTest {

    @Test
    @DisplayName("[post 생성 성공 테스트] PostForCreate 로부터 Post 애그리거트를 생성한다.")
    public void generatePost_successTest_fromPostForCreate() {
        // Given
        Price price = new Price(100);
        String description = "testDescription";
        String title = "testTitle";
        Images images = Images.generateImagesExcludeThumbnail(List.of("image1"));
        PostForCreate postForCreate =
            new PostForCreate(1L, 2L, 3L, price, title, description, images);
        // When
        Post post = Post.from(postForCreate);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertNull(post.getPostId()),
            () -> Assertions.assertEquals(1L, post.getMemberId()),
            () -> Assertions.assertEquals(2L, post.getRegionId()),
            () -> Assertions.assertEquals(3L, post.getCategoryId()),
            () -> Assertions.assertEquals(price, post.getPrice()),
            () -> Assertions.assertEquals(title, post.getTitle()),
            () -> Assertions.assertEquals(description, post.getDescription()),
            () -> Assertions.assertEquals(images, post.getImages())
        );
    }
}
