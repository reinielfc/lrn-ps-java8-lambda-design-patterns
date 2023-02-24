import factory.Factory;
import model.Rectangle;
import model.Shape;
import model.Triangle;
import registry.Builder;
import registry.Registry;

import java.util.function.Consumer;

public class PlayWithRegistryBuilder {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        /*
        Consumer<Builder<Rectangle>> consumer1 =
                rectangleBuilder -> rectangleBuilder.register("rectangle", Rectangle::new);
        Registry registry = Registry.createRegistry(consumer1);
        Factory<Rectangle> rectangleFactory =
                (Factory<Rectangle>) registry.buildShapeFactory("rectangle");

        Rectangle rectangle = rectangleFactory.newInstance();
        System.out.println("rectangle = " + rectangle);
        */

        Consumer<Builder<Shape>> consumer1 =
                builder -> builder.register("rectangle", Rectangle::new);
        Consumer<Builder<Shape>> consumer2 =
                builder -> builder.register("triangle", Triangle::new);

        Consumer<Builder<Shape>> consumer = consumer1.andThen(consumer2);
        Registry registry = Registry.createRegistry(consumer);

        Factory<Rectangle> buildRectangleFactory =
                (Factory<Rectangle>) registry.buildShapeFactory("rectangle");
        Factory<Triangle> buildTriangleFactory =
                (Factory<Triangle>) registry.buildShapeFactory("triangle");

        Rectangle rectangle = buildRectangleFactory.newInstance();
        Triangle triangle = buildTriangleFactory.newInstance();

        System.out.println("rectangle = " + rectangle);
        System.out.println("triangle = " + triangle);
    }
}
