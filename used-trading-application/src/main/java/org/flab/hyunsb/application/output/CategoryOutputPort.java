package org.flab.hyunsb.application.output;

import java.util.Optional;

public interface CategoryOutputPort {

    Optional<Long> findIdById(Long id);
}
