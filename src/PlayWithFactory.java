import factory.CircleFactory;
import factory.Factory;
import model.Circle;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class PlayWithFactory {
    public static void main(String[] args) {
        // with supplier
        Supplier<Circle> circleSupplierFactory = Circle::new;
        Circle circle = circleSupplierFactory.get();
        System.out.println("circle = " + circle);

        // with own supplier factory interface
        CircleFactory circleFactory = Circle::new;
        circle = circleFactory.newInstance();
        System.out.println("circle = " + circle);

        List<Circle> circleList = circleFactory.create5Circles();
        System.out.println("circleList = " + circleList);

        // with generic interface
        Factory<Circle> factoryOfCircles = Circle::new;
        circleList = factoryOfCircles.create5();
        System.out.println("circleList = " + circleList);

        // factory interface that creates factories
        //Factory<Circle> factoryOfRedCircles = Factory.createFactory(Color.RED);
        Factory<Circle> factoryOfRedCircles = Factory.createFactory(Circle::new, Color.RED);
        circle = factoryOfRedCircles.newInstance();
        System.out.println("circle = " + circle);
    }
}
