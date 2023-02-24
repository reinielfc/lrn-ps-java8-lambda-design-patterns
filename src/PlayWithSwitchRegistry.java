import factory.Factory;
import model.Rectangle;
import registry.SwitchRegistry;

public class PlayWithSwitchRegistry {
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        SwitchRegistry registry = new SwitchRegistry();
        Factory<Rectangle> rectangleFactory = (Factory<Rectangle>) registry.buildShapefactory("rectangle");
        Rectangle rectangle = rectangleFactory.newInstance();
        System.out.println("rectangle = " + rectangle);
    }
    
}
