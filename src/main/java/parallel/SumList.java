package parallel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class SumList implements Callable<Integer> {
    private List<Integer> list;

    public SumList(List<Integer> list) {
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {
        return list.stream().collect(Collectors.summingInt(i -> i));
    }
}
