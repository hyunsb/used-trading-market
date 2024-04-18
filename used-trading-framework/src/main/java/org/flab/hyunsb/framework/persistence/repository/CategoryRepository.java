package org.flab.hyunsb.framework.persistence.repository;

import org.flab.hyunsb.framework.persistence.entity.post.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
