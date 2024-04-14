package org.flab.hyunsb.application.usecase.post;

import org.flab.hyunsb.domain.post.PostForCreate;

public interface CreatePostUseCase {

    Long createMember(PostForCreate postForCreate);
}
