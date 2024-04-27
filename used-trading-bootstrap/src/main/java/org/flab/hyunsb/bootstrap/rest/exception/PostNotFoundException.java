package org.flab.hyunsb.bootstrap.rest.exception;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(Long postId) {
        super("postId에 해당하는 게시글이 존재하지 않습니다. : " + postId);
    }
}
