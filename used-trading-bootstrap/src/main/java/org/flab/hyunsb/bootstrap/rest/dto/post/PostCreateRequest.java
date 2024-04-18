package org.flab.hyunsb.bootstrap.rest.dto.post;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.flab.hyunsb.domain.post.PostForCreate;
import org.flab.hyunsb.domain.post.Price;
import org.flab.hyunsb.domain.post.vo.Images;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotNull
    private Long categoryId;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Integer price;

    private Integer thumbnailNumber;

    @NotNull
    private List<String> images;

    public PostForCreate toDomain(Long memberId, Long regionId) {
        Images images = generateImages(thumbnailNumber, this.images);
        Price price = new Price(this.price);

        return new PostForCreate(memberId, regionId, categoryId, price, title, description, images);
    }

    private Images generateImages(Integer thumbnailNumber, List<String> images) {
        if (thumbnailNumber == null) {
            return Images.generateImagesExcludeThumbnail(images);
        }
        return Images.generateImagesIncludeThumbnail(thumbnailNumber, images);
    }

}
