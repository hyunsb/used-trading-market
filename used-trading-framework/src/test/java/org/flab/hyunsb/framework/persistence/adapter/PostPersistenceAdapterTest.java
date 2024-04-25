package org.flab.hyunsb.framework.persistence.adapter;

import java.util.List;
import org.flab.hyunsb.domain.post.Post;
import org.flab.hyunsb.domain.post.PostForCreate;
import org.flab.hyunsb.domain.post.Price;
import org.flab.hyunsb.domain.post.vo.Images;
import org.flab.hyunsb.framework.persistence.entity.member.MemberEntity;
import org.flab.hyunsb.framework.persistence.entity.post.CategoryEntity;
import org.flab.hyunsb.framework.persistence.entity.region.RegionEntity;
import org.flab.hyunsb.framework.persistence.repository.CategoryRepository;
import org.flab.hyunsb.framework.persistence.repository.MemberRepository;
import org.flab.hyunsb.framework.persistence.repository.RegionRepository;
import org.flab.hyunsb.framework.repository.annotation.RepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class PostPersistenceAdapterTest {

    @Autowired
    private PostPersistenceAdapter postPersistenceAdapter;

    private RegionEntity testRegion;
    private MemberEntity testMember;
    private CategoryEntity testCategory;

    @BeforeEach
    void setUp(
        @Autowired RegionRepository regionRepository,
        @Autowired MemberRepository memberRepository,
        @Autowired CategoryRepository categoryRepository) {

        testRegion = regionRepository.save(new RegionEntity(1L, "경기도", "시흥시", 0.0, 0.0));

        MemberEntity member = new MemberEntity(1L, testRegion, "email", "password", "nickname");
        testMember = memberRepository.save(member);

        testCategory = categoryRepository.save(new CategoryEntity(1L, "전자제품"));
    }

    @Test
    @DisplayName("[post 생성 성공 테스트] post 도메인이 주어진 경우 post를 영속화 한 뒤 Id를 반환한다.")
    public void Test() {
        // Given
        Images images = new Images(List.of("Image1", "Image1", "Image1"));
        Price price = new Price(1000);

        PostForCreate postForCreate = new PostForCreate(
            testMember.getId(), testRegion.getId(), testCategory.getId(),
            price, "title", "description", images);

        Post post = Post.from(postForCreate);
        // When
        Long id = postPersistenceAdapter.savePost(post);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(1L, id)
        );
    }
}