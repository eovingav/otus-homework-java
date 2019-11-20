package ru.otus.hw09DIYORM.ORM;

public class SqlParam {
    private String methodName;
    private Object value;

    public SqlParam(Object value){
        if (value instanceof Long){
            this.methodName = "setLong";
            this.value = (Long) value;
        }else if (value instanceof String){
            this.methodName = "setString";
            this.value = value;
        }else if (value instanceof Integer){
            this.methodName = "setInt";
            this.value = (Integer) value;
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public Object getValue() {
        return value;
    }
}
