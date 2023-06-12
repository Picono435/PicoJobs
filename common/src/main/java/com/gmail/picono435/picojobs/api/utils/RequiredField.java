package com.gmail.picono435.picojobs.api.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.List;

public class RequiredField {

    private String name;
    private RequiredFieldType type;

    public RequiredField(String name, RequiredFieldType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public RequiredFieldType getType() {
        return type;
    }

    public enum RequiredFieldType {
        STRING_LIST,
        STRING,
        INTEGER,
        DOUBLE,
        LONG,
        LONG_LIST,
        BOOLEAN;

        public JsonElement getJsonElement(Object object) {
            switch (this) {
                case STRING_LIST: {
                    JsonArray jsonArray = new JsonArray();
                    for (String s : (List<String>) object) jsonArray.add(s);
                    return jsonArray;
                }
                case STRING:
                    return new JsonPrimitive((String) object);
                case INTEGER:
                    return new JsonPrimitive((int) object);
                case DOUBLE:
                    return new JsonPrimitive((double) object);
                case LONG_LIST: {
                    JsonArray jsonArray = new JsonArray();
                    for (long s : (List<Long>) object) jsonArray.add(s);
                    return jsonArray;
                }
                case LONG:
                    return new JsonPrimitive((long) object);
                case BOOLEAN:
                    return new JsonPrimitive((boolean) object);
            }
            return null;
        }
    }
}
