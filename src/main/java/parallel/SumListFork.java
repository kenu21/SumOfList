package parallel;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SumListFork extends RecursiveTask<Integer> {
    private static final int BORDER = 250000;
    private List<Integer> list;
    private int start;
    private int end;

    public SumListFork(List<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start <= BORDER) {
            for (int i = start; i < end; i++) {
                sum += list.get(i);
            }
            return sum;
        } else {
            int middle = (start + end) / 2;
            SumListFork sumListFork1 = new SumListFork(list, start, middle);
            SumListFork sumListFork2 = new SumListFork(list, middle, end);
            sumListFork1.fork();
            sumListFork2.fork();
            sum = sumListFork1.join() + sumListFork2.join();
        }
        return sum;
    }
}
