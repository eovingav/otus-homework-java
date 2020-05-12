package ru.otus.hw12CacheEngine.api.cachehw;

import ru.otus.hw12CacheEngine.api.Identifiable;

import java.util.Optional;

public interface CacheManager {
    void clearCache();
    void updateCache(Long entityId, Identifiable entity);
    Optional<Identifiable> getCachedValue(Long id, String className);
    void addEntity(String cachedEntityName);
    void removeEntity(String cachedEntityName);
}
