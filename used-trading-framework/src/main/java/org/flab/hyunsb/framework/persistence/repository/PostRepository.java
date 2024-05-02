package org.flab.hyunsb.framework.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.flab.hyunsb.framework.persistence.entity.post.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query(value = """
            select p
            from post p
            where p.region.id in (:regionIds)
                and p.status not in ('HIDE', 'SOLD')
            order by p.updatedAt
         """)
    List<PostEntity> findByRegionIdIn(@Param("regionIds") List<Long> regionIds, Pageable pageable);

    @Query(value = """
            select p
            from post p
            join fetch p.images i
            where p.id = :postId
        """)
    Optional<PostEntity> findByPostId(@Param("postId") Long postId);

    @Query(value = """
            select p
            from post p
            where p.region.id in (:regionIds)
                and p.category.id = :categoryId
                and p.status not in ('HIDE', 'SOLD')
            order by p.updatedAt
        """)
    List<PostEntity> findByCategoryIdAndRegionIdIn(
        @Param("categoryId") Long categoryId,
        @Param("regionIds") List<Long> regionIds,
        Pageable pageable);

    @Query(value = """
            select p
            from post p
            join category c on c.id = p.category.id
            where concat(p.title, p.description, c.name) like %:keyword%
                and p.region.id in (:regionIds) and p.status not in ('HIDE', 'SOLD')
            order by p.updatedAt
        """)
    List<PostEntity> findByKeywordAndRegionIdIn(
        @Param("keyword") String keyword,
        @Param("regionIds") List<Long> regionIds,
        Pageable pageable
    );
}
