package ru.otus.hw12CacheEngine.api;

import java.util.Optional;

public interface DBService {
    long saveObject(Identifiable entity);
    Optional<Identifiable> getObject(long id, Class<? extends Identifiable> clazz);
    Optional<Identifiable> findById(long id, Class<? extends Identifiable> clazz);
    void executeUpdate(String updateSql);
    void setUseCache(boolean useCache);
}
