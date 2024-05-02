package org.flab.hyunsb.bootstrap.rest.adapter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flab.hyunsb.application.dto.Actor;
import org.flab.hyunsb.bootstrap.rest.actortoken.LoginActor;
import org.flab.hyunsb.bootstrap.rest.dto.post.PostDetailResponse;
import org.flab.hyunsb.bootstrap.rest.dto.post.PostFindResponse;
import org.flab.hyunsb.bootstrap.rest.exception.PostNotFoundException;
import org.flab.hyunsb.framework.persistence.adapter.PostPersistenceAdapter;
import org.flab.hyunsb.framework.persistence.entity.post.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostRestQueryAdapter {

    private final PostPersistenceAdapter postPersistenceAdapter;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PostFindResponse> findAll(@LoginActor Actor actor,
        @PageableDefault Pageable pageable) {

        List<PostEntity> posts = postPersistenceAdapter.findAllPost(actor.regionId(), pageable);

        return posts.stream().map(PostFindResponse::from).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{postId}")
    public PostDetailResponse findOne(@PathVariable(value = "postId") Long postId) {

        PostEntity postEntity = postPersistenceAdapter.findOne(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));

        return PostDetailResponse.from(postEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categories")
    public List<PostFindResponse> findAllByCategoryId(
        @LoginActor Actor actor, @PageableDefault Pageable pageable,
        @RequestParam(name = "categoryId") Long categoryId) {

        List<PostEntity> posts =
            postPersistenceAdapter.findAllPostByCategoryId(actor.regionId(), categoryId, pageable);

        return posts.stream().map(PostFindResponse::from).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/keywords")
    public List<PostFindResponse> findAllByKeyword(
        @LoginActor Actor actor, @PageableDefault Pageable pageable,
        @RequestParam(name = "keyword") String keyword) {

        List<PostEntity> posts =
            postPersistenceAdapter.findAllPostByKeyword(actor.regionId(), keyword, pageable);

        return posts.stream().map(PostFindResponse::from).toList();
    }
}
