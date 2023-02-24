import model.Body;
import model.Car;
import model.Engine;

import java.util.function.Consumer;

public class PlayWithVisitor {
    public static void main(String[] args) {
        Car car = new Car();
        Engine engine = new Engine();
        Body body = new Body();

        Consumer<VisitorBuilder<String>> consumer =
                Visitor.<Car, String>forType(Car.class).execute((Car c) -> "Visiting car: " + c)
                        .forType(Engine.class).execute((Engine e) ->"Visiting Engine: " + e)
                        .forType(Body.class).execute((Body b) -> "Visiting body: " + b);

        Visitor<String> visitor = Visitor.of(consumer);

        String visitedEngine = visitor.visit(engine);
        System.out.println("visitedEngine = " + visitedEngine);

        String visitedCar = visitor.visit(car);
        System.out.println("visitedCar = " + visitedCar);

        String visitedBody = visitor.visit(body);
        System.out.println("visitedBody = " + visitedBody);
    }
}
