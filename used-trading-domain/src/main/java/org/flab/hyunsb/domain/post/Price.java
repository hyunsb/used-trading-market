package org.flab.hyunsb.domain.post;

import lombok.Getter;
import org.flab.hyunsb.domain.post.exception.PostConstraintException;

@Getter
public class Price {

    private static final int MIN_PRICE = 0;
    private static final int MAX_PRICE = 999_999_999;

    private int price;

    public Price(int price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new PostConstraintException();
        }
    }

    private void changePrice(int price) {
        validatePrice(price);
        this.price = price;
    }
}
