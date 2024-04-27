package org.flab.hyunsb.bootstrap.rest.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.flab.hyunsb.application.dto.Actor;
import org.flab.hyunsb.application.usecase.member.ActorTokenAuthUseCase;
import org.flab.hyunsb.bootstrap.config.ActorTokenConfig;
import org.flab.hyunsb.bootstrap.restdocs.AbstractRestDocsTests;
import org.flab.hyunsb.framework.persistence.adapter.PostPersistenceAdapter;
import org.flab.hyunsb.framework.persistence.entity.member.MemberEntity;
import org.flab.hyunsb.framework.persistence.entity.post.CategoryEntity;
import org.flab.hyunsb.framework.persistence.entity.post.PostEntity;
import org.flab.hyunsb.framework.persistence.entity.post.PostStatus;
import org.flab.hyunsb.framework.persistence.entity.region.RegionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

@WebMvcTest(PostRestQueryAdapter.class)
class PostRestQueryAdapterTest extends AbstractRestDocsTests {

    @MockBean
    private PostPersistenceAdapter postPersistenceAdapter;

    @MockBean
    private ActorTokenAuthUseCase actorTokenAuthUseCase;

    private long sequence;

    @Test
    @DisplayName("[게시글 전체 조회 핸들러 성공 테스트] 토큰에 존재하는 지역정보를 기반으로 전체 게시글을 조회한다.")
    public void findAllPost_successTest() throws Exception {
        // Given
        String requestUrl = "/api/v1/posts";
        String testToken = "testToken";

        when(actorTokenAuthUseCase.authenticate(anyString()))
            .thenReturn(new Actor(1L, 1L));

        List<PostEntity> mockPostEntities = generateTestPostEntities(10);
        when(postPersistenceAdapter.findAllPost(anyLong(), any(Pageable.class)))
            .thenReturn(mockPostEntities);

        // When & Then
        mockMvc.perform(get(requestUrl)
                .header(ActorTokenConfig.ACTOR_TOKEN_HEADER, testToken)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(10));
    }

    private List<PostEntity> generateTestPostEntities(int number) {
        List<PostEntity> postEntities = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            postEntities.add(new PostEntity(
                sequence++, CategoryEntity.valueOf(1L),
                MemberEntity.valueOf(1L), RegionEntity.valueOf(1L),
                PostStatus.SELLING, "TestTitle", "TestDescription", 1000,
                "TestThumbnail", List.of(), 1, 1,
                LocalDateTime.now()
            ));
        }
        return postEntities;
    }

    @Test
    @DisplayName("[게시글 상세 조회 핸들러 성공 테스트] 게시글 아이디를 기반으로 상세 데이터를 조회한다.")
    public void findOnePost_successTest() throws Exception {
        // Given
        String requestUrl = "/api/v1/posts/{postId}";
        Long postId = 1L;
        String testToken = "testToken";

        when(actorTokenAuthUseCase.authenticate(anyString()))
            .thenReturn(new Actor(1L, 1L));

        PostEntity postEntity = generateTestPostEntities(1).get(0);
        when(postPersistenceAdapter.findOne(anyLong()))
            .thenReturn(Optional.of(postEntity));

        // When & Then
        mockMvc.perform(get(requestUrl, postId)
                .header(ActorTokenConfig.ACTOR_TOKEN_HEADER, testToken)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(15))
            .andExpect(jsonPath("$.memberId").value(1));
    }

    @Test
    @DisplayName("[게시글 상세 조회 핸들러 실패 테스트] 유효하지 않은 게시글 아이디가 주어진 경우 예외를 발생한다.")
    public void findOnePost_failureTest_invalidPostId() throws Exception {
        // Given
        String requestUrl = "/api/v1/posts/{postId}";
        Long invalidPostId = 1L;
        String testToken = "testToken";

        when(actorTokenAuthUseCase.authenticate(anyString()))
            .thenReturn(new Actor(1L, 1L));

        when(postPersistenceAdapter.findOne(anyLong()))
            .thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get(requestUrl, invalidPostId)
                .header(ActorTokenConfig.ACTOR_TOKEN_HEADER, testToken)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[게시글 카테고리 검색 핸들러 성공 테스트] 카테고리 아이디를 기반으로 포스트를 조회한다.")
    public void findPostByCategory_successTest() throws Exception {
        // Given
        String requestUrl = "/api/v1/posts/categories";
        Long categoryId = 1L;
        String testToken = "testToken";

        when(actorTokenAuthUseCase.authenticate(anyString()))
            .thenReturn(new Actor(1L, 1L));

        List<PostEntity> mockPostEntities = generateTestPostEntities(10);
        when(postPersistenceAdapter.findAllPostByCategoryId(
            anyLong(), anyLong(), any(Pageable.class))
        ).thenReturn(mockPostEntities);

        // When & Then
        mockMvc.perform(get(requestUrl)
                .param("categoryId", String.valueOf(categoryId))
                .header(ActorTokenConfig.ACTOR_TOKEN_HEADER, testToken)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(10));
    }

    @Test
    @DisplayName("[게시글 키워드 검색 핸들러 성공 테스트] 키워드를 기반으로 포스트를 조회한다.")
    public void findPostByKeyword_successTest() throws Exception {
        // Given
        String requestUrl = "/api/v1/posts/keywords";
        String keyword = "keyword";
        String testToken = "testToken";

        when(actorTokenAuthUseCase.authenticate(anyString()))
            .thenReturn(new Actor(1L, 1L));

        List<PostEntity> mockPostEntities = generateTestPostEntities(10);
        when(postPersistenceAdapter.findAllPostByKeyword(
            anyLong(), anyString(), any(Pageable.class))
        ).thenReturn(mockPostEntities);

        // When & Then
        mockMvc.perform(get(requestUrl)
                .param("keyword", keyword)
                .header(ActorTokenConfig.ACTOR_TOKEN_HEADER, testToken)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(10));
    }
}
