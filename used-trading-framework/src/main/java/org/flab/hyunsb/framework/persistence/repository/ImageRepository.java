package org.flab.hyunsb.framework.persistence.repository;

import org.flab.hyunsb.framework.persistence.entity.post.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
