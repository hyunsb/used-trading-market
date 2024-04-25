package org.flab.hyunsb.framework.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.application.output.PostOutputPort;
import org.flab.hyunsb.domain.post.Post;
import org.flab.hyunsb.framework.persistence.entity.post.ImageEntity;
import org.flab.hyunsb.framework.persistence.entity.post.PostEntity;
import org.flab.hyunsb.framework.persistence.repository.ImageRepository;
import org.flab.hyunsb.framework.persistence.repository.PostRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostPersistenceAdapter implements PostOutputPort {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Long savePost(Post post) {
        PostEntity postEntity = PostEntity.from(post);

        postEntity = postRepository.save(postEntity);

        Long postId = postEntity.getId();
        imageRepository.saveAll(ImageEntity.from(postId, post.getImages()));

        return postId;
    }
}
