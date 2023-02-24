import model.Person;
import util.Validator;

import java.util.function.Predicate;

public class PlayWithValidator {
    public static void main(String[] args) {
        Person sarah = new Person("Sarah", 29);
        Person james = new Person(null, 29);
        Person mary = new Person("Mary", -10);
        Person john = new Person("John", 1_000);
        Person linda = new Person(null, 1_000);

        Predicate<Person> nameIsNotNull = p -> p.getName() != null;
        String nameIsNotNullErrorMsg = "The name should not be null";

        Predicate<Person> ageIsGreaterThan0 = p -> p.getAge() > 0;
        String ageIsGreaterThan0ErrorMsg = "The age should be greater than 0";

        Predicate<Person> ageIsLessThan150 = p -> p.getAge() < 150;
        String ageIsLessThan150ErrorMsg = "The age should be lesser than 150";

        sarah = Validator.validate(nameIsNotNull, nameIsNotNullErrorMsg)
                .on(sarah)
                .validate();
        System.out.println("sarah = " + sarah);

        james = Validator.validate(nameIsNotNull, nameIsNotNullErrorMsg)
                .on(james)
                .validate();
        System.out.println("james = " + james);

        mary = Validator.validate(nameIsNotNull, nameIsNotNullErrorMsg)
                .thenValidate(ageIsGreaterThan0, ageIsGreaterThan0ErrorMsg)
                .thenValidate(ageIsLessThan150, ageIsLessThan150ErrorMsg)
                .on(mary)
                .validate();
        System.out.println("mary = " + mary);

        linda = Validator.validate(nameIsNotNull, nameIsNotNullErrorMsg)
                .thenValidate(ageIsGreaterThan0, ageIsGreaterThan0ErrorMsg)
                .thenValidate(ageIsLessThan150, ageIsLessThan150ErrorMsg)
                .on(linda)
                .validate();
        System.out.println("mary = " + linda);

    }
}
