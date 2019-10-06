package ru.otus.hw09DIYORM.ORM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09DIYORM.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class DbExecutor<T> {
  private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);
  private final SessionManagerJdbc sessionManager;

  public DbExecutor(SessionManagerJdbc sessionManager){
    this.sessionManager = sessionManager;
  }

  public void create(T objectData){

    Class<T> clazz = (Class<T>) objectData.getClass();
    if (!isValidClass(clazz)){
      throw new DbExecutorException(new RuntimeException("ID field in class " + objectData.getClass() + " doesn't exist!"));
    }

    try {
      String sqlInsertObject = getSqlInsert(clazz);
      List<SqlParam> params = getSqlParamsInsert(objectData);
      sessionManager.beginSession();
      long key = insertRecord(getConnection(), sqlInsertObject, params, false);
      sessionManager.commitSession();
      logger.info("created " + clazz.getSimpleName() + " with ID=" + key);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      sessionManager.rollbackSession();
      throw new DbExecutorException(e);
    }

  }

  public void update(T objectData){

    Class clazz = objectData.getClass();
    if (!isValidClass(objectData.getClass())){
      throw new DbExecutorException(new RuntimeException("ID field in class " + objectData.getClass() + " doesn't exist!"));
    }

    try {
      String sqlUpdateObject = getSqlUpdate(clazz);
      List<SqlParam> params = getSqlParamsUpdate(objectData);
      sessionManager.beginSession();
      long key = insertRecord(getConnection(), sqlUpdateObject, params, true);
      sessionManager.commitSession();
      logger.info("updated in " + clazz.getSimpleName() + " " + key + " rows");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      sessionManager.rollbackSession();
      throw new DbExecutorException(e);
    }

  }
  public void createOrUpdate(T objectData){

    if (!isValidClass(objectData.getClass())){
      throw new DbExecutorException(new RuntimeException("ID field in class " + objectData.getClass() + " doesn't exist!"));
    }
    long id = getId(objectData);
    Optional<T> result = null;
    try {
       result = selectRecord(getConnection(), getSqlGetObjectByID(objectData.getClass()), id,resultSet -> {
        try{
          if (resultSet.next()) {
            return objectData;
          }else {
            return null;
          }
        }catch (Exception e){
          logger.error(e.getMessage(), e);
        }
        return null;
      });
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
    }

    if (result.equals(Optional.empty())){
      create(objectData);
    }else {
      update(objectData);
    }
  }
  public <T> T load(long id, Class<T> clazz) {

    if (!isValidClass(clazz)) {
      throw new DbExecutorException(new RuntimeException("ID field in class " + clazz + " doesn't exist!"));
    }

    Object object = null;
    try {
      String sqlGetObjectByID = getSqlGetObjectByID(clazz);
      return (T) selectRecord(getConnection(), sqlGetObjectByID, id, resultSet -> {
        try {
          if (resultSet.next()) {
            return newObjectFromRS(resultSet, clazz);
          }
        } catch (SQLException e) {
          logger.error(e.getMessage(), e);
        }
        return null;
      }).get();
    } catch (Exception e) {
      logger.error("record in " + clazz.getSimpleName() + " with id = " + id + " doesn't exist");
    }
    return null;
  }

  public long insertRecord(Connection connection, String sql, List<SqlParam> params, Boolean update) throws SQLException {
    Savepoint savePoint = connection.setSavepoint("savePointName");
    try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      for (int idx = 0; idx < params.size(); idx++) {
        SqlParam param = params.get(idx);
        String methodName = param.getMethodName();
        if (methodName.equals("setString")){
          pst.setString(idx + 1, (String) param.getValue());
        }else if (methodName.equals("setLong")){
          pst.setLong(idx + 1, (Long) param.getValue());
        }else if (methodName.equals("setInt")){
          pst.setInt(idx + 1, (Integer) param.getValue());
        }
      }

      int affectedRows = pst.executeUpdate();
      if (!update){
        try (ResultSet rs = pst.getGeneratedKeys()) {
          rs.next();
          return rs.getLong(1);
        }
      }else {
        return affectedRows;
      }
    } catch (SQLException ex) {
      connection.rollback(savePoint);
      logger.error(ex.getMessage(), ex);
      throw ex;
    }
  }

  public Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
    try (PreparedStatement pst = connection.prepareStatement(sql)) {
      pst.setLong(1, id);
      try (ResultSet rs = pst.executeQuery()) {
        return Optional.ofNullable(rsHandler.apply(rs));
      }
    }
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }

  private Boolean isValidClass(Class<?> clazz){

    Field[] fields = clazz.getDeclaredFields();
    Boolean idFieldExists = false;

    for (Field field:fields){
      Annotation idAnnotation = field.getAnnotation(ID.class);
      if (Objects.nonNull(idAnnotation)) {
        idFieldExists = true;
        break;
      }
    }

    return idFieldExists;
  }

  private String getSqlInsert(Class<T> clazz){

    StringBuilder sqlStringBuilder = new StringBuilder();
    String tableName = clazz.getSimpleName();

    sqlStringBuilder.append("insert into ");
    sqlStringBuilder.append(tableName);
    sqlStringBuilder.append("(");

    StringBuilder params = new StringBuilder();

    Field[] fields = clazz.getDeclaredFields();
    int fieldsCount = fields.length;
    int currentField = 0;
    for (Field field:fields){
      currentField++;

      Annotation idAnnotation = field.getAnnotation(ID.class);
      if (Objects.nonNull(idAnnotation)) {
        continue;
      }
      sqlStringBuilder.append(field.getName());
      params.append("?");

      if (currentField < fieldsCount) {
        sqlStringBuilder.append(",");
        params.append(",");
      }
    }

    sqlStringBuilder.append(") values (");
    sqlStringBuilder.append(params);
    sqlStringBuilder.append(")");

    return sqlStringBuilder.toString();
  }

  private List<SqlParam> getSqlParamsInsert(T objectData){

    Class<?> clazz = objectData.getClass();
    Field[] fields = clazz.getDeclaredFields();
    ArrayList<SqlParam> params = new ArrayList<>();

    try {
      for (Field field:fields){

        Annotation idAnnotation = field.getAnnotation(ID.class);
        if (Objects.nonNull(idAnnotation)) {
          continue;
        }

        String fieldName = field.getName();
        String getterFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getter = clazz.getMethod("get" + getterFieldName);
        SqlParam param = new SqlParam(getter.invoke(objectData));
        params.add(param);
      }
    } catch (Exception e) {
      throw new DbExecutorException(e);
    }

    return params;
  }

  private <T> String getSqlGetObjectByID(Class<T> clazz) {

    StringBuilder sqlStringBuilder = new StringBuilder();
    String tableName = clazz.getSimpleName();
    sqlStringBuilder.append("select ");

    Field[] fields = clazz.getDeclaredFields();
    String idFieldName = "";
    int currentField = 0;
    int totalFieldsCount = fields.length;
    for (Field field : fields) {
      currentField++;
      Annotation idAnnotation = field.getAnnotation(ID.class);
      if (Objects.nonNull(idAnnotation)) {
        idFieldName = field.getName();
      }
      sqlStringBuilder.append(field.getName());
      if (currentField < totalFieldsCount){
        sqlStringBuilder.append(", ");
      }
    }

    sqlStringBuilder.append(" from ");
    sqlStringBuilder.append(tableName);
    sqlStringBuilder.append(" where ");
    sqlStringBuilder.append(idFieldName);
    sqlStringBuilder.append("=?");

    return sqlStringBuilder.toString();
  }

  private <T> T newObjectFromRS(ResultSet resultSet, Class clazz){

    Field[] fields = clazz.getDeclaredFields();
    Class[] constructorTypes = new Class[fields.length];
    Object[] constructorValues = new Object[fields.length];
    int currentField = 0;
    Object object = null;
    try{
    for (Field field : fields) {
      constructorTypes[currentField] = field.getType();

      if (constructorTypes[currentField].equals(int.class)){
        constructorValues[currentField] = resultSet.getInt(field.getName());
      }else if (constructorTypes[currentField].equals(long.class)){
        constructorValues[currentField] = resultSet.getLong(field.getName());
      }else if (constructorTypes[currentField].equals(String.class)){
        constructorValues[currentField] = resultSet.getString(field.getName());
      }
      currentField++;
    }
      Constructor<T> constructor = clazz.getConstructor(constructorTypes);
      object = constructor.newInstance(constructorValues);
    } catch (NoSuchMethodException e) {
      logger.error("Can't find constructor with all fields in class " + clazz.getName());
      throw new DbExecutorException(e);
    } catch (Exception e) {
      logger.error(e.getCause().getMessage());
      throw new DbExecutorException(e);
    }
    return (T) object;
  }

  private String getSqlUpdate(Class<T> clazz){
    StringBuilder sqlStringBuilder = new StringBuilder();
    String tableName = clazz.getSimpleName();
    sqlStringBuilder.append("update ");
    sqlStringBuilder.append(tableName);
    sqlStringBuilder.append(" set ");

    Field[] fields = clazz.getDeclaredFields();
    String idFieldName = "";
    int currentField = 0;
    int totalFieldsCount = fields.length;
    for (Field field : fields) {
      currentField++;
      String fieldName = field.getName();
      Annotation idAnnotation = field.getAnnotation(ID.class);
      if (Objects.nonNull(idAnnotation)) {
        idFieldName = fieldName;
        continue;
      }
      sqlStringBuilder.append(fieldName);
      sqlStringBuilder.append("=?");
      if (currentField < totalFieldsCount){
        sqlStringBuilder.append(", ");
      }
    }

    sqlStringBuilder.append(" where ");
    sqlStringBuilder.append(idFieldName);
    sqlStringBuilder.append("=?");

    return sqlStringBuilder.toString();
  }

  private List<SqlParam> getSqlParamsUpdate(T objectData){

    List<SqlParam> params = getSqlParamsInsert(objectData);

    Class clazz = objectData.getClass();
    Field[] fields = clazz.getDeclaredFields();
    try {
      for (Field field : fields) {
        Annotation idAnnotation = field.getAnnotation(ID.class);
        if (Objects.nonNull(idAnnotation)) {
          String fieldName = field.getName();
          String getterFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
          Method getter = clazz.getMethod("get" + getterFieldName);
          SqlParam param = new SqlParam(getter.invoke(objectData));
          params.add(param);
        }
      }
    }catch (Exception e){
      throw new DbExecutorException(e);
    }

    return params;
  }

  private long getId(T objectData){

    Class clazz = objectData.getClass();
    Field[] fields = clazz.getDeclaredFields();
    long id = 0;
    try {
      for (Field field : fields) {
        Annotation idAnnotation = field.getAnnotation(ID.class);
        if (Objects.nonNull(idAnnotation)) {
          String fieldName = field.getName();
          String getterFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
          Method getter = clazz.getMethod("get" + getterFieldName);
          id = (long) getter.invoke(objectData);
        }
      }
    }catch (Exception e){
      logger.error("cannot get id value for " + objectData.toString());
      throw new DbExecutorException(e);
    }

    return id;
  }
}
