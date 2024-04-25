package org.flab.hyunsb.bootstrap.rest.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.flab.hyunsb.application.dto.Actor;
import org.flab.hyunsb.application.usecase.member.ActorTokenAuthUseCase;
import org.flab.hyunsb.application.usecase.post.CreatePostUseCase;
import org.flab.hyunsb.bootstrap.rest.dto.post.PostCreateRequest;
import org.flab.hyunsb.bootstrap.restdocs.AbstractRestDocsTests;
import org.flab.hyunsb.domain.post.PostForCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(PostRestAdapter.class)
class PostRestAdapterTest extends AbstractRestDocsTests {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreatePostUseCase createPostUseCase;

    @MockBean
    private ActorTokenAuthUseCase actorTokenAuthUseCase;

    @ParameterizedTest
    @ValueSource(ints = {1, 0})
    @DisplayName("[게시글 등록 핸들러 성공 테스트] 게시글 등록 성공 시 해당 게시글의 식별자를 반환한다.")
    public void registrationMember_successTest(Integer thumbnailNumber) throws Exception {
        // Given
        String requestUrl = "/api/v1/posts";
        when(actorTokenAuthUseCase.authenticate(ArgumentMatchers.anyString()))
            .thenReturn(new Actor(1L, 1L));

        String requestBody = objectMapper.writeValueAsString(
            new PostCreateRequest(
                1L, "title", "description", 1000, thumbnailNumber, List.of("Image1", "Image2"))
        );

        when(createPostUseCase.createPost(any(PostForCreate.class)))
            .thenReturn(1L);

        // When & Then
        mockMvc.perform(post(requestUrl)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "testToken"))
            .andExpect(status().isCreated());
    }
}
