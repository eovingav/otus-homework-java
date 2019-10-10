package ru.otus.hw10Hibernate.hibernate.sessionmanager;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10Hibernate.api.Identifiable;
import ru.otus.hw10Hibernate.api.sessionmanager.SessionManager;

import java.util.Optional;

public class HibernateExecutor{
    private static Logger logger = LoggerFactory.getLogger(HibernateExecutor.class);
    private final SessionManagerHibernate sessionManager;

    public HibernateExecutor(SessionManagerHibernate sessionManager){
        this.sessionManager = sessionManager;
    }

    public long saveObject(Identifiable entity) {
        sessionManager.beginSession();
        try {
            long entityId = saveEntity(entity);
            sessionManager.commitSession();
            logger.info("created " + entity.getClass().getSimpleName() + " with ID=" + entityId);
            return entityId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new HibernateExecutorException(e);
         }
    }

    public Optional<Identifiable> getObject(long id, Class<? extends Identifiable> clazz) {

            sessionManager.beginSession();
            try {
                Optional<Identifiable> entityOptional = findById(id, clazz);
                logger.info("" + clazz.getSimpleName() + ": {}", entityOptional.orElse(null));
                sessionManager.close();
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
