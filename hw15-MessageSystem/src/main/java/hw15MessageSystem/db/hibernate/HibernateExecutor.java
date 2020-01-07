package hw15MessageSystem.db.hibernate;

import hw15MessageSystem.db.api.Identifiable;
import hw15MessageSystem.db.sessionmanager.DatabaseSessionHibernate;
import hw15MessageSystem.db.sessionmanager.SessionManagerHibernate;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HibernateExecutor{
    private static Logger logger = LoggerFactory.getLogger(HibernateExecutor.class);

    @Autowired
    private final SessionManagerHibernate sessionManager;

    public HibernateExecutor(SessionManagerHibernate sessionManager){
        this.sessionManager = sessionManager;
    }

    public static Logger getLogger() {
        return logger;
    }

    public SessionManagerHibernate getSessionManager() {
        return sessionManager;
    }

    public long saveObject(Identifiable entity) {
        sessionManager.beginSession();
        try {
            long entityId = saveEntity(entity);
            sessionManager.commitSession();
            logger.info("created {} with ID={}", entity.getClass().getSimpleName(), entityId);
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
                logger.info("{}: {}", clazz.getSimpleName(),  entityOptional.orElse(null));
                sessionManager.close();
                return entityOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();

    }

    public <T extends Identifiable> List<T> getObjectList(Class<T> clazz){

        List<T> objects = new ArrayList<>();

        sessionManager.beginSession();
        try {
            DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
            objects = currentSession.getHibernateSession().createQuery("from " + clazz.getSimpleName()).getResultList();
            sessionManager.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
        }
        return objects;
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

    public Optional<Identifiable> findByField(Object value, String fieldName, Class<? extends Identifiable> clazz) {
        sessionManager.beginSession();
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
                String tableName = clazz.getSimpleName();
                StringBuilder sqlStringBuilder = new StringBuilder();
                sqlStringBuilder.append("SELECT id");
                sqlStringBuilder.append(" from ");
                sqlStringBuilder.append(tableName);
                sqlStringBuilder.append(" where ");
                sqlStringBuilder.append(fieldName);
                sqlStringBuilder.append("=?1");
                long id =  (long) hibernateSession.createQuery(sqlStringBuilder.toString())
                        .setParameter(1, value)
                        .getSingleResult();
                Optional<Identifiable> result = findById(id, clazz);
                sessionManager.close();
                return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
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
