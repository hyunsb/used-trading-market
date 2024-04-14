package org.flab.hyunsb.domain.post.vo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.flab.hyunsb.domain.post.exception.PostConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImagesTest {

    @Test
    @DisplayName("[이미지 생성 성공 테스트] 썸네일 정보가 포함되지 않은 이미지 생성 시 첫 번째 이미지를 썸네일로 지정한다.")
    public void imagesGeneration_successTest_excludeThumbnail() {
        // Given
        List<String> imageUrls = List.of("image1", "image2", "image3");
        // When
        Images images = Images.generateImagesExcludeThumbnail(imageUrls);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals("image1", images.getThumbnail()),
            () -> Assertions.assertEquals(imageUrls, images.getImages())
        );
    }

    @Test
    @DisplayName("[이미지 생성 성공 테스트] 썸네일 정보가 포함된 이미지를 생성한다.")
    public void imagesGeneration_successTest_includeThumbnail() {
        // Given
        int thumbnailNumber = 2;
        List<String> imageUrls = List.of("image1", "image2", "image3");
        // When
        Images images = Images.generateImagesIncludeThumbnail(thumbnailNumber, imageUrls);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals("image3", images.getThumbnail()),
            () -> Assertions.assertEquals(imageUrls, images.getImages())
        );
    }

    @Test
    @DisplayName("[이미지 생성 실패 테스트] 이미지 리스트가 빈 경우 예외를 발생한다.")
    public void imagesGeneration_failureTest_emptyImages() {
        // Given
        List<String> emptyList = List.of();
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(PostConstraintException.class, () ->
                Images.generateImagesExcludeThumbnail(emptyList))
        );
    }

    @Test
    @DisplayName("[이미지 생성 실패 테스트] 썸네일 번호가 이미지 개수보다 큰 경우 예외를 발생한다.")
    public void imagesGeneration_successTest_invalidThumbnailNumber() {
        // Given
        List<String> imageUrls = List.of("image1", "image2", "image3");
        int thumbnailNumber = imageUrls.size() + 1;

        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(PostConstraintException.class, () ->
                Images.generateImagesIncludeThumbnail(thumbnailNumber, imageUrls))
        );
    }
}