package org.flab.hyunsb.domain.post.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flab.hyunsb.domain.post.exception.PostConstraintException;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Count {

    private int count;

    public void increaseCount() {
        increaseCount(1);
    }

    public void increaseCount(int number) {
        validateNumberForChangeCount(number);
        changeCount(number);
    }

    private void validateNumberForChangeCount(int number) {
        if (number <= 0) {
            throw new PostConstraintException("변경할 수는 음수일 수 없음");
        }
    }

    private void changeCount(int number) {
        count += number;
    }

    public void decreaseCount() {
        decreaseCount(1);
    }

    public synchronized void decreaseCount(int number) {
        validateNumberForChangeCount(number);
        validateIsPossibleDecrease(number);
        changeCount(-number);
    }

    private void validateIsPossibleDecrease(int number) {
        if (count - number < 0) {
            throw new PostConstraintException("카운트를 감소시킬 수 없음");
        }
    }
}
