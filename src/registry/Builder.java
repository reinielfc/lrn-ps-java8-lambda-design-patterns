package registry;

import factory.Factory;

public interface Builder<T> {
    void register(String label, Factory<T> factory);
}
