package ru.otus.hw12CacheEngine.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12CacheEngine.api.DBService;
import ru.otus.hw12CacheEngine.api.Identifiable;
import ru.otus.hw12CacheEngine.api.cachehw.CacheManager;
import ru.otus.hw12CacheEngine.cachehw.CacheManagerImpl;
import ru.otus.hw12CacheEngine.cachehw.MyCache;
import ru.otus.hw12CacheEngine.model.User;
import ru.otus.hw12CacheEngine.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hw12CacheEngine.sessionmanager.SessionManagerHibernate;

import java.util.*;

public class HibernateExecutor implements DBService {
    private static Logger logger = LoggerFactory.getLogger(HibernateExecutor.class);
    private final SessionManagerHibernate sessionManager;
    private CacheManager cacheManager = new CacheManagerImpl();
    private boolean useCache = false;

    public void setUseCache(boolean useCache) {
        cacheManager.clearCache();
        if (!this.useCache && useCache) {
            cacheManager.addEntity(User.class.getSimpleName());
        }
        this.useCache = useCache;
    }

    public HibernateExecutor(SessionManagerHibernate sessionManager){
        this.sessionManager = sessionManager;
        setUseCache(this.useCache);
    }

    public long saveObject(Identifiable entity) {
        sessionManager.beginSession();
        try {
            long entityId = saveEntity(entity);
            sessionManager.commitSession();
            logger.info("created " + entity.getClass().getSimpleName() + " with ID=" + entityId);
            cacheManager.updateCache(entityId, entity);
            return entityId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new HibernateExecutorException(e);
         }
    }

    public Optional<Identifiable> getObject(long id, Class<? extends Identifiable> clazz) {

            if (useCache){
                Optional<Identifiable> entityOptional = cacheManager.getCachedValue(id, clazz.getSimpleName());
                if (entityOptional.isPresent()){
                    return entityOptional;
                }
            }
            sessionManager.beginSession();
            try {
                Optional<Identifiable> entityOptional = findById(id, clazz);
                logger.info("" + clazz.getSimpleName() + ": {}", entityOptional.orElse(null));
                sessionManager.close();
                cacheManager.updateCache(id, entityOptional.get());
                return entityOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();

    }

    public Optional<Identifiable> findById(long id, Class<? extends Identifiable> clazz) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(clazz, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    public void executeUpdate(String updateSql) {
        sessionManager.beginSession();
        try {
            Query query = sessionManager.createNativeQuery(updateSql);
            query.executeUpdate();
            logger.info("executed update query: " + updateSql);
            sessionManager.commitSession();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new HibernateExecutorException(e);
        }
    }

    private long saveEntity(Identifiable entity) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (entity.getID() > 0) {
                hibernateSession.merge(entity);
            } else {
                hibernateSession.persist(entity);
            }
            return entity.getID();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new HibernateExecutorException(e);
        }
    }





}
