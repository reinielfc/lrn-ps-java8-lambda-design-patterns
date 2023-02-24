package registry;

import factory.Factory;
import model.Shape;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface Registry {
    Factory<? extends Shape> buildShapeFactory(String shape);

    static Registry createRegistry(Consumer<Builder<Shape>> consumer) {
        Map<String, Factory<Shape>> map = new HashMap<>();
        Builder<Shape> builder = ((label, factory) -> map.put(label, factory));
        consumer.accept(builder);

        return shape -> map.get(shape);
    }
}
