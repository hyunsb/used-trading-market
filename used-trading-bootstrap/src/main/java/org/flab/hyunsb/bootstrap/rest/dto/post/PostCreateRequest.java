package org.flab.hyunsb.bootstrap.rest.dto.post;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer thumbnailNumber;

    @NotNull
    private List<String> images;

    public PostForCreate toDomain(Long memberId, Long regionId) {
        Images images = new Images(thumbnailNumber, this.images);
        Price price = new Price(this.price);

        return new PostForCreate(memberId, regionId, categoryId, price, title, description, images);
    }
}
