package org.flab.hyunsb.framework.persistence.adapter;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.application.output.PostOutputPort;
import org.flab.hyunsb.domain.post.Post;
import org.flab.hyunsb.framework.persistence.entity.post.ImageEntity;
import org.flab.hyunsb.framework.persistence.entity.post.PostEntity;
import org.flab.hyunsb.framework.persistence.repository.ImageRepository;
import org.flab.hyunsb.framework.persistence.repository.PostRepository;
import org.flab.hyunsb.framework.persistence.repository.RegionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostPersistenceAdapter implements PostOutputPort {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public Long savePost(Post post) {
        PostEntity postEntity = PostEntity.from(post);

        postEntity = postRepository.save(postEntity);

        Long postId = postEntity.getId();
        imageRepository.saveAll(ImageEntity.from(postId, post.getImages()));

        return postId;
    }

    @Transactional(readOnly = true)
    public List<PostEntity> findAllPost(Long regionId, Pageable pageable) {
        List<Long> regionIds = findNearFiveRegionFrom(regionId);
        return postRepository.findByRegionIdIn(regionIds, pageable);
    }

    private List<Long> findNearFiveRegionFrom(Long regionId) {
        return regionRepository.findNearFiveByRegionIdFrom(regionId);
    }

    public Optional<PostEntity> findOne(Long postId) {
        return postRepository.findByPostId(postId);
    }

    public List<PostEntity> findAllPostByCategoryId(Long regionId, Long categoryId, Pageable pageable) {
        List<Long> regionIds = findNearFiveRegionFrom(regionId);
        return postRepository.findByCategoryIdAndRegionIdIn(categoryId, regionIds, pageable);
    }

    public List<PostEntity> findAllPostByKeyword(Long regionId, String keyword, Pageable pageable) {
        List<Long> regionIds = findNearFiveRegionFrom(regionId);
        return postRepository.findByKeywordAndRegionIdIn(keyword, regionIds, pageable);
    }
}
