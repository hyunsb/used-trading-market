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

    /*
    * 카운트 감소 작업에서 동시성 문제 밠애시 카운트가 음수로 변경될 가능성이 존재한다.
    * 카운트 증가 작업은 데이터가 누락되어도 문제가 되지 않으니
    * AtomicInteger로 동시성을 처리할 경우 성능 오버헤드가 발생할 수 있다.
    * 따라서 카운트 감소 작업만 동기화 처리한다.
    * */
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
