package org.flab.hyunsb.bootstrap.rest.actortoken;

import org.flab.hyunsb.application.dto.Actor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;

@ExtendWith(MockitoExtension.class)
public class LoginActorArgumentResolverTest {

    @InjectMocks
    private LoginActorArgumentResolver loginActorArgumentResolver;

    private MethodParameter mockMethodParameter;

    @BeforeEach
    void setUp() {
        mockMethodParameter = Mockito.mock(MethodParameter.class);
    }

    @Test
    @DisplayName("[아규먼트 리졸버 동작 성공 테스트] 동작 조건에 부합하는 경우 true를 반환한다.")
    public void resolveArgument_successTest() {
        // Given
        Mockito.when(mockMethodParameter.hasParameterAnnotation(LoginActor.class))
            .thenReturn(true);

        Mockito.when(mockMethodParameter.getParameterType())
            .thenReturn((Class) Actor.class);
        // When
        boolean result = loginActorArgumentResolver.supportsParameter(mockMethodParameter);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertTrue(result)
        );
    }

    @Test
    @DisplayName("[아규먼트 리졸버 동작 실패 테스트] 조건에 부합하는 애노테이션이 포함되어 있지 않은 경우 false를 반환한다.")
    public void resolveArgument_failureTest_excludeAnnotation() {
        // Given
        Mockito.when(mockMethodParameter.hasParameterAnnotation(LoginActor.class))
            .thenReturn(false);
        // When
        boolean result = loginActorArgumentResolver.supportsParameter(mockMethodParameter);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertFalse(result)
        );
    }

    @Test
    @DisplayName("[아규먼트 리졸버 동작 실패 테스트] 조건에 부합하지 않는 타입인 경우 false를 반환한다.")
    public void resolveArgument_failureTest() {
        // Given
        Mockito.when(mockMethodParameter.hasParameterAnnotation(LoginActor.class))
            .thenReturn(true);

        Mockito.when(mockMethodParameter.getParameterType())
            .thenReturn((Class) Long.class);
        // When
        boolean result = loginActorArgumentResolver.supportsParameter(mockMethodParameter);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertFalse(result)
        );
    }
}
