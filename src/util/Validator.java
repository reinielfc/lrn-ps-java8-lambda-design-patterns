package util;

import model.Person;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Validator {
    ValidatorSupplier on(Person p);

    default Validator thenValidate(Predicate<Person> predicate, String errorMessage) {
        return p -> {
            try {
                on(p).validate();
                return predicate.test(p)
                        ? () -> p
                        : () -> {
                    ValidationException exception = new ValidationException("The object is not valid");
                    exception.addSuppressed(new IllegalArgumentException(errorMessage));
                    throw exception;
                };
            } catch (ValidationException validationException) {
                if (!predicate.test(p)) {
                    validationException.addSuppressed(new IllegalArgumentException(errorMessage));
                }
                return () -> {
                    throw validationException;
                };
            }
        };
    }

    static Validator validate(Predicate<Person> predicate, String errorMessage) {
        return p -> predicate.test(p)
                ? () -> p
                : () -> {
            ValidationException exception = new ValidationException("The object is not valid");
            exception.addSuppressed(new IllegalArgumentException(errorMessage));
            throw exception;
        };
    }

    interface ValidatorSupplier extends Supplier<Person> {
        default Person validate() {
            return get();
        }
    }

    class ValidationException extends RuntimeException {
        public ValidationException(String errorMessage) {
            super(errorMessage);
        }
    }
}
