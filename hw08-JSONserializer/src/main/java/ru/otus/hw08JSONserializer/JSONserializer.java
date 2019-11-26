package ru.otus.hw08JSONserializer;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class JSONserializer {

    public String toJSON(Object object) throws IllegalAccessException {

        Class clazz = (object == null) ? null : object.getClass();
        String json = "";
        if (object == null){
            json = "\"null\"";
        }else if (isPrimitive(clazz)){
            json = "\"" + object.toString() + "\"";
        }else if(isDate(clazz)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            json = dateFormat.format(object);
        }else if (clazz.isArray()){
            JsonArrayBuilder jsonArrayBuilder = arrayToJson(object);
            JsonArray jsonArray = jsonArrayBuilder.build();
            json = jsonArray.toString();
        }else if (object instanceof Collection){
            JsonArrayBuilder jsonArrayBuilder = collectionToJson(object);
            JsonArray jsonArray = jsonArrayBuilder.build();
            json = jsonArray.toString();
        }
        else {
            JsonObject jsonObject = getJsonObject(object);
            json = jsonObject.toString();
        }
        return json;
    }

    public String toJSON(String object) throws IllegalAccessException {
        return "\"" + object + "\"";
    }

    private JsonObject getJsonObject(Object object) throws IllegalAccessException {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Field[] allFields = object.getClass().getDeclaredFields();
        addFields(jsonObjectBuilder, object, allFields);

        return jsonObjectBuilder.build();
    }

    private void addFields(JsonObjectBuilder jsonObjectBuilder, Object object, Field[] fields) throws IllegalAccessException {
        for (Field field: fields) {
            Object fieldObject = field.get(object);
            Class clazz = (fieldObject == null) ? null : fieldObject.getClass();
            if (fieldObject == null){
                jsonObjectBuilder.add(field.getName(), "null");
            }else  if (isPrimitive(clazz)) {
                jsonObjectBuilder.add(field.getName(), fieldObject.toString());
            }else if (isDate(clazz)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                jsonObjectBuilder.add(field.getName(), dateFormat.format(fieldObject));
            }else if (clazz.isArray()){
                JsonArrayBuilder jsonArrayBuilder = arrayToJson(fieldObject);
                jsonObjectBuilder.add(field.getName(), jsonArrayBuilder);
            }else if (fieldObject instanceof Collection){
                JsonArrayBuilder jsonArrayBuilder = collectionToJson(fieldObject);
                jsonObjectBuilder.add(field.getName(), jsonArrayBuilder);
            }else {
                JsonObjectBuilder fieldObjectBuilder = Json.createObjectBuilder();
                Field[] fieldsField = fieldObject.getClass().getDeclaredFields();
                addFields(fieldObjectBuilder, fieldObject, fieldsField);
                jsonObjectBuilder.add(field.getName(), fieldObjectBuilder);
            }
        }
    }

    private JsonArrayBuilder arrayToJson(Object arrayObject) throws IllegalAccessException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        int length = Array.getLength(arrayObject);
        for (int i=0;i<length;i++) {
            Object elem = Array.get(arrayObject, i);
            addMember(jsonArrayBuilder, elem);
        }
        return jsonArrayBuilder;
    }

    private JsonArrayBuilder collectionToJson(Object collectionObject) throws IllegalAccessException {
        Collection collection = (Collection) collectionObject;
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Object elem: collection){
            addMember(jsonArrayBuilder, elem);
        }
        return jsonArrayBuilder;
    }

    private void addMember(JsonArrayBuilder jsonArrayBuilder, Object elem) throws IllegalAccessException {
        Class elemClass = (elem == null) ? null : elem.getClass();
        if (elem == null){
            jsonArrayBuilder.add("null");
        }else if (isPrimitive(elemClass)) {
            jsonArrayBuilder.add(elem.toString());

        }else if(isDate(elemClass)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            jsonArrayBuilder.add(dateFormat.format(elem));
        }else if(elemClass.isArray()){
            JsonArrayBuilder innerArrayBuilder = arrayToJson(elem);
            jsonArrayBuilder.add(innerArrayBuilder);
        }else if(elem instanceof Collection) {
            JsonArrayBuilder innerCollectionBuilder = collectionToJson(elem);
            jsonArrayBuilder.add(innerCollectionBuilder);
        }else{
            JsonObjectBuilder fieldObjectBuilder = Json.createObjectBuilder();
            Field[] fieldsField = elem.getClass().getDeclaredFields();
            addFields(fieldObjectBuilder, elem, fieldsField);
            jsonArrayBuilder.add(fieldObjectBuilder);
        }
    }

    private boolean isPrimitive(Class clazz) {
        return clazz.isPrimitive()
                || clazz.isAssignableFrom(Integer.class)
                || clazz.isAssignableFrom(BigDecimal.class)
                || clazz.isAssignableFrom(BigInteger.class)
                || clazz.isAssignableFrom(Boolean.class)
                || clazz.isAssignableFrom(Float.class)
                || clazz.isAssignableFrom(Double.class)
                || clazz.isAssignableFrom(String.class)
                || clazz.isAssignableFrom(Character.class);
    }

    private boolean isDate(Class clazz){
        return clazz.isAssignableFrom(Date.class);
    }
}
