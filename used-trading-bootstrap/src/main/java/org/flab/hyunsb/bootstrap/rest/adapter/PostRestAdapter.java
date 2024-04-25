package org.flab.hyunsb.bootstrap.rest.adapter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flab.hyunsb.application.dto.Actor;
import org.flab.hyunsb.application.usecase.post.CreatePostUseCase;
import org.flab.hyunsb.bootstrap.rest.actortoken.LoginActor;
import org.flab.hyunsb.bootstrap.rest.dto.post.PostCreateRequest;
import org.flab.hyunsb.domain.post.PostForCreate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostRestAdapter {

    private final CreatePostUseCase createPostUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long createPost(@LoginActor Actor actor,
        @Valid @RequestBody PostCreateRequest postCreateRequest) {

        PostForCreate postForCreate = postCreateRequest.toDomain(actor.actorId(), actor.regionId());

        return createPostUseCase.createPost(postForCreate);
    }
}
