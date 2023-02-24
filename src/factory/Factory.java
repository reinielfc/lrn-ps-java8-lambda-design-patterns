package factory;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Factory<T> extends Supplier<T> {
    default T newInstance() {
        return get();
    }

    default List<T> create5() {
        return IntStream.range(0, 5)
                .mapToObj(i -> newInstance())
                .collect(Collectors.toList());
    }

    //static Factory<Circle> createFactory() {
    //    return Circle::new;
    //}
    //
    //static Factory<Circle> createFactory(Color color) {
    //    return () -> new Circle(color);
    //}

    static <T> Factory<T> createFactory(Supplier<T> supplier) {
        T singleton = supplier.get();
        return () -> singleton;
        //return supplier::get; // replaced with singleton
    }

    static <T, P> Factory<T> createFactory(Function<P, T> constructor, P parameter) {
        //Function<Color, Circle> constructor = Circle::new; ^ moved to argument
        return () -> constructor.apply(parameter);
    }
}
