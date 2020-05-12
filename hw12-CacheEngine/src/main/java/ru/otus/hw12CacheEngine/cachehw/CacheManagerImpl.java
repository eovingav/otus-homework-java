package ru.otus.hw12CacheEngine.cachehw;

import ru.otus.hw12CacheEngine.api.Identifiable;
import ru.otus.hw12CacheEngine.api.cachehw.CacheManager;
import ru.otus.hw12CacheEngine.model.User;

import java.util.*;

public class CacheManagerImpl implements CacheManager {

    private static Map<String, MyCache<Long, Identifiable>> cache = new HashMap<>();
    private static Set<String> cachedEntities = new HashSet<>();

    public CacheManagerImpl() {
    }

    public void addEntity(String cachedEntityName) {
        cachedEntities.add(cachedEntityName);
        cache.put(cachedEntityName, new MyCache<>());
    }

    public void removeEntity(String cachedEntityName) {
        if (cachedEntities.contains(cachedEntityName)) {
            cachedEntities.remove(cachedEntityName);
            cache.remove(cachedEntityName);
        }
    }

    @Override
    public void clearCache() {
        cachedEntities.clear();
        cache.clear();
    }

    @Override
    public void updateCache(Long entityId, Identifiable entity) {
        String entityClassName = entity.getClass().getSimpleName();
        if (cachedEntities.contains(entityClassName)){
            MyCache<Long, Identifiable> cacheEntity = cache.get(entityClassName);
            cacheEntity.put(entityId, entity);
        }
    }

    @Override
    public Optional<Identifiable> getCachedValue(Long id, String className) {
        Optional<MyCache<Long, Identifiable>> myCache = Optional.ofNullable(cache.get(className));
        if (myCache.isPresent()) {
            return Optional.ofNullable(myCache.get().get(id));
        }
        return null;
    }
}
