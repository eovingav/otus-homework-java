package ru.otus.hw12CacheEngine.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;

public final class HibernateUtils {

  private HibernateUtils() {
  }

  public static SessionFactory buildSessionFactory(String configResourceFileName, Class ...annotatedClasses){
    Configuration configuration = new Configuration().configure(configResourceFileName);
    MetadataSources metadataSources = new MetadataSources(createServiceRegistry(configuration));
    Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

    Metadata metadata = metadataSources.getMetadataBuilder().build();
    return metadata.getSessionFactoryBuilder().build();
  }

  private static StandardServiceRegistry createServiceRegistry(Configuration configuration){
    return new StandardServiceRegistryBuilder()
        .applySettings(configuration.getProperties()).build();
  }

}
