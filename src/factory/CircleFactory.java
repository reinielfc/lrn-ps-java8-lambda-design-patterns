package factory;

import model.Circle;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface CircleFactory extends Supplier<Circle> {
    default Circle newInstance() {
        return get();
    }

    default List<Circle> create5Circles() {
        return IntStream.range(0, 5)
                .mapToObj(i -> newInstance())
                .collect(Collectors.toList());
    }
}
