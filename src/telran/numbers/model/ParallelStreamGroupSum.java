package telran.numbers.model;

import java.util.Arrays;

public class ParallelStreamGroupSum extends NumberSum {

    public ParallelStreamGroupSum(int[][] numbersGroup) {
        super(numbersGroup);
    }

    @Override
    public int computeSum() {
        return Arrays.stream(numbersGroup).parallel()
                .flatMapToInt(Arrays::stream).sum();
    }

}
