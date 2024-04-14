package org.flab.hyunsb.application.service;

import lombok.RequiredArgsConstructor;
import org.flab.hyunsb.application.output.PostOutputPort;
import org.flab.hyunsb.application.usecase.post.CreatePostUseCase;
import org.flab.hyunsb.application.validator.CategoryValidator;
import org.flab.hyunsb.application.validator.RegionValidator;
import org.flab.hyunsb.domain.post.Post;
import org.flab.hyunsb.domain.post.PostForCreate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService implements CreatePostUseCase {

    private final PostOutputPort postOutputPort;
    private final RegionValidator regionValidator;
    private final CategoryValidator categoryValidator;

    @Override
    public Long createMember(PostForCreate postForCreate) {
        regionValidator.validateRegionId(postForCreate.regionId());
        categoryValidator.validateCategoryId(postForCreate.categoryId());

        Post post = Post.from(postForCreate);
        return postOutputPort.savePost(post);
    }
}
