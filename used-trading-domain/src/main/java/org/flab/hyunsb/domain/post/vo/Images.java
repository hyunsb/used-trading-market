package org.flab.hyunsb.domain.post.vo;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.flab.hyunsb.domain.post.exception.PostConstraintException;

@Getter
public class Images {

    private static final int DEFAULT_THUMBNAIL_NUMBER = 0;

    private String thumbnail;
    private List<String> images;

    public Images(List<String> images) {
        this(DEFAULT_THUMBNAIL_NUMBER, images);
    }

    public Images(int thumbnailNumber, List<String> images) {
        validateImages(images);
        validateThumbnailNumber(thumbnailNumber, images.size());

        thumbnail = images.get(thumbnailNumber);
        this.images = images;
    }

    private void validateImages(List<String> images) {
        if (images.isEmpty()) {
            throw new PostConstraintException("이미지는 필수");
        }
    }

    private void validateThumbnailNumber(int thumbnailNumber, int imageNumber) {
        if (thumbnailNumber >= imageNumber) {
            throw new PostConstraintException("썸네일 번호가 유효하지 않음");
        }
    }

    public List<String> getImages() {
        return Collections.unmodifiableList(images);
    }
}
