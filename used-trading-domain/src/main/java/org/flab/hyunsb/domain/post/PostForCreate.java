package org.flab.hyunsb.domain.post;

import org.flab.hyunsb.domain.post.vo.Images;

public record PostForCreate(long memberId, long regionId, long categoryId,
                            Price price, String description, Images images) {


}
