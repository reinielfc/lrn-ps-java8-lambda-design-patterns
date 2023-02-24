package registry;

import factory.Factory;
import model.*;

public class SwitchRegistry {
    public Factory<? extends Shape> buildShapefactory(String shape) {
        switch (shape) {
            case "square": return Square::new;
            case "triangle": return Triangle::new;
            case "rectangle": return Rectangle::new;
            default:
                throw new IllegalArgumentException("Unknown shape: " + shape);
        }
    }
}
