package org.flab.hyunsb.application.output;

import org.flab.hyunsb.domain.post.Post;

public interface PostOutputPort {

    Long savePost(Post post);
}
