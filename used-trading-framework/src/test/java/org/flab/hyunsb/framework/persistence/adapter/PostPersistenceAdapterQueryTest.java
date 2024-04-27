package org.flab.hyunsb.framework.persistence.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import org.flab.hyunsb.framework.persistence.entity.post.PostEntity;
import org.flab.hyunsb.framework.persistence.repository.ImageRepository;
import org.flab.hyunsb.framework.persistence.repository.PostRepository;
import org.flab.hyunsb.framework.persistence.repository.RegionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class PostPersistenceAdapterQueryTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private PostPersistenceAdapter postPersistenceAdapter;

    @Test
    @DisplayName("[post 리스트 조회 성공 테스트] regionId를 기반으로 post리스트를 조회한뒤 반환한다.")
    public void findAllByRegionId_successTest() {
        // Given
        Long regionId = 1L;
        Pageable pageable = Pageable.ofSize(10);

        Mockito.when(regionRepository.findNearFiveByRegionIdFrom(anyLong()))
            .thenReturn(List.of(1L, 2L, 3L, 4L, 5L));

        Mockito.when(postRepository.findByRegionIdIn(anyList(), any(Pageable.class)))
            .thenReturn(List.of(new PostEntity(), new PostEntity(), new PostEntity()));

        // When
        List<PostEntity> posts = postPersistenceAdapter.findAllPost(regionId, pageable);

        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(3, posts.size())
        );
    }

    @Test
    @DisplayName("[post 리스트 조회 성공 테스트] categoryId를 기반으로 post리스트를 조회한 뒤 반환한다.")
    public void findAllByCategoryIdAndRegionId_successTest() {
        // Given
        Long regionId = 1L;
        Long categoryId = 1L;
        Pageable pageable = Pageable.ofSize(10);

        Mockito.when(regionRepository.findNearFiveByRegionIdFrom(anyLong()))
            .thenReturn(List.of(1L, 2L, 3L, 4L, 5L));

        Mockito.when(postRepository.findByCategoryIdAndRegionIdIn(
            anyLong(), anyList(), any(Pageable.class))
        ).thenReturn(List.of(new PostEntity(), new PostEntity(), new PostEntity()));

        // When
        List<PostEntity> posts =
            postPersistenceAdapter.findAllPostByCategoryId(categoryId, regionId, pageable);

        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(3, posts.size())
        );
    }

    @Test
    @DisplayName("[post 리스트 조회 성공 테스트] keyword를 기반으로 post리스트를 조회한 뒤 반환한다.")
    public void findAllByKeywordAndRegionId_successTest() {
        // Given
        Long regionId = 1L;
        String keyword = "keyword";
        Pageable pageable = Pageable.ofSize(10);

        Mockito.when(regionRepository.findNearFiveByRegionIdFrom(anyLong()))
            .thenReturn(List.of(1L, 2L, 3L, 4L, 5L));

        Mockito.when(postRepository.findByKeywordAndRegionIdIn(
            anyString(), anyList(), any(Pageable.class))
        ).thenReturn(List.of(new PostEntity(), new PostEntity(), new PostEntity()));

        // When
        List<PostEntity> posts =
            postPersistenceAdapter.findAllPostByKeyword(regionId, keyword, pageable);

        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(3, posts.size())
        );
    }
}
