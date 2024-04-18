package org.flab.hyunsb.application.service.post;

import java.util.List;
import org.flab.hyunsb.application.exception.constraint.ConstraintException;
import org.flab.hyunsb.application.output.PostOutputPort;
import org.flab.hyunsb.application.service.PostService;
import org.flab.hyunsb.application.validator.CategoryValidator;
import org.flab.hyunsb.application.validator.RegionValidator;
import org.flab.hyunsb.domain.post.Post;
import org.flab.hyunsb.domain.post.PostForCreate;
import org.flab.hyunsb.domain.post.Price;
import org.flab.hyunsb.domain.post.vo.Images;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostOutputPort postOutputPort;

    @Mock
    private RegionValidator regionValidator;

    @Mock
    private CategoryValidator categoryValidator;

    private PostForCreate testPostForCreate;

    @BeforeEach
    void setUp() {
        Images images = Images.generateImagesExcludeThumbnail(List.of("image1", "image2"));
        testPostForCreate =
            new PostForCreate(1L, 1L, 1L, new Price(0), "title", "description", images);
    }

    @Test
    @DisplayName("[게시글 생성 성공 테스트] 게시글을 생성한다.")
    public void createPost_successTest() {
        // Given
        Mockito.doNothing().when(regionValidator).validateRegionId(ArgumentMatchers.anyLong());
        Mockito.doNothing().when(categoryValidator).validateCategoryId(ArgumentMatchers.anyLong());
        Mockito.when(postOutputPort.savePost(ArgumentMatchers.any(Post.class))).thenReturn(1L);

        // When
        Long postId = postService.createPost(testPostForCreate);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(1L, postId)
        );
    }

    @Test
    @DisplayName("[게시글 생성 실패 테스트] 지역 검증에 실패하는 경우 예외를 발생한다.")
    public void createPost_failureTest_invalidRegionId() {
        // Given
        Mockito.doThrow(ConstraintException.class)
            .when(regionValidator)
            .validateRegionId(ArgumentMatchers.anyLong());

        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(ConstraintException.class, () ->
                postService.createPost(testPostForCreate))
        );
    }

    @Test
    @DisplayName("[게시글 생성 실패 테스트] 카테고리 검증에 실패하는 경우 예외를 발생한다.")
    public void createPost_failureTest_invalidCategoryId() {
        // Given
        Mockito.doNothing().when(regionValidator).validateRegionId(ArgumentMatchers.anyLong());
        Mockito.doThrow(ConstraintException.class)
            .when(categoryValidator).validateCategoryId(ArgumentMatchers.anyLong());

        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(ConstraintException.class, () ->
                postService.createPost(testPostForCreate))
        );
    }
}
