package me.nathancole.api.datasection;

import java.util.Map;
import java.util.Optional;

public interface Registry<T extends Model> {

    Optional<T> fromKey(String key);

    T register(T value);

    T put(String key, T value);

    void remove(String key);

    void loadFromDb(String key);

    Map<String, T> loadAllFromDb();

    void purge();

    T fromDataSection(String key, DataSection section);
}
