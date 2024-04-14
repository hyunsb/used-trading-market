package org.flab.hyunsb.domain.post.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.flab.hyunsb.domain.post.exception.PostConstraintException;

@Getter
@AllArgsConstructor
public class Images {

    private String thumbnail;
    private List<String> images;

    public static Images generateImagesExcludeThumbnail(List<String> images) {
        validateImages(images);
        return generateImagesIncludeThumbnail(0, images);
    }

    private static void validateImages(List<String> images) {
        if (images.isEmpty()) {
            throw new PostConstraintException("이미지는 필수");
        }
    }

    public static Images generateImagesIncludeThumbnail(int thumbnailNumber, List<String> images) {
        validateImages(images);
        validateThumbnailNumber(thumbnailNumber, images.size());
        return new Images(images.get(thumbnailNumber), images);
    }

    private static void validateThumbnailNumber(int thumbnailNumber, int imageNumber) {
        if (thumbnailNumber >= imageNumber) {
            throw new PostConstraintException("썸네일 번호가 유효하지 않음");
        }
    }
}
