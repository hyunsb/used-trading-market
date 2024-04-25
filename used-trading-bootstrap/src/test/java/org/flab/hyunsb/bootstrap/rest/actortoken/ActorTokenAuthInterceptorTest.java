package org.flab.hyunsb.bootstrap.rest.actortoken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.flab.hyunsb.application.dto.Actor;
import org.flab.hyunsb.application.usecase.member.ActorTokenAuthUseCase;
import org.flab.hyunsb.bootstrap.rest.exception.ActorTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ActorTokenAuthInterceptorTest {

    @InjectMocks
    private ActorTokenAuthInterceptor actorTokenAuthInterceptor;

    @Mock
    private ActorTokenAuthUseCase actorTokenAuthUseCase;

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private Object mockHandler;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockHandler = new Object();
    }

    @Test
    @DisplayName("[엑터 토큰 파싱 성공 테스트] 유효한 토큰이 제공된 경우 preHandle 호출 시 true를 반환한다.")
    public void actorTokenParsing_successTest() throws Exception {
        // Given
        Mockito.when(mockRequest.getHeader("Authorization"))
            .thenReturn("valid_token");

        Actor actor = new Actor(1L, 1L);
        Mockito.when(actorTokenAuthUseCase.authenticate(ArgumentMatchers.anyString()))
            .thenReturn(actor);

        // When
        boolean result =
            actorTokenAuthInterceptor.preHandle(mockRequest, mockResponse, mockHandler);

        // Then
        Assertions.assertAll(
            () -> Assertions.assertTrue(result)
        );
    }

    @Test
    @DisplayName("[엑터 토큰 파싱 실패 테스트] 토큰이 존재하지 않는 경우 예외를 발생한다.")
    public void actorTokenParsing_failureTest() {
        // Given
        Mockito.when(mockRequest.getHeader("Authorization"))
            .thenReturn(null);

        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertThrows(ActorTokenException.class, () ->
                actorTokenAuthInterceptor.preHandle(mockRequest, mockResponse, mockHandler))
        );
    }
}
