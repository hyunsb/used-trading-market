package org.flab.hyunsb.framework.persistence.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flab.hyunsb.domain.post.Post;
import org.flab.hyunsb.framework.persistence.entity.basetime.BaseTimeEntity;
import org.flab.hyunsb.framework.persistence.entity.member.MemberEntity;
import org.flab.hyunsb.framework.persistence.entity.region.RegionEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity(name = "post")
public class PostEntity extends BaseTimeEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String thumbnail;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<ImageEntity> images;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    private Integer viewCount;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime registrationAt;

    @PrePersist
    private void prePersistence() {
        likeCount = 0;
        viewCount = 0;
    }

    public static PostEntity valueOf(Long id) {
        return PostEntity.builder().id(id).build();
    }

    public static PostEntity from(Post post) {
        return PostEntity.builder()
            .category(CategoryEntity.valueOf(post.getCategoryId()))
            .member(MemberEntity.valueOf(post.getMemberId()))
            .region(RegionEntity.valueOf(post.getRegionId()))
            .status(PostStatus.valueOf(post.getStatus()))
            .title(post.getTitle())
            .description(post.getDescription())
            .price(post.getPrice().getPrice())
            .thumbnail(post.getImages().getThumbnail())
            .likeCount(post.getLikeCount().getCount())
            .viewCount(post.getViewCount().getCount())
            .build();
    }
}
