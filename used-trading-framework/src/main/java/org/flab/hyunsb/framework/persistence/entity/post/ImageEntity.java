package org.flab.hyunsb.framework.persistence.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flab.hyunsb.domain.post.vo.Images;
import org.flab.hyunsb.framework.persistence.entity.basetime.BaseTimeEntity;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "image")
public class ImageEntity extends BaseTimeEntity {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;

    @Column(nullable = false)
    private String imageUrl;

    public static List<ImageEntity> from(Long postId, Images images) {
        return images.getImages().stream()
            .map(image -> ImageEntity.from(postId, image))
            .collect(Collectors.toList());
    }

    private static ImageEntity from(Long postId, String image) {
        return new ImageEntity(null, PostEntity.valueOf(postId), image);
    }
}
