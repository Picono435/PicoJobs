package com.gmail.picono435.picojobs.api.field;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.google.gson.*;
import org.spongepowered.configurate.serialize.SerializationException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class RequiredField<P, V> {

    private static final String SERIALIZATION_ERROR = "Error with getting required field named '{}' for job '{}'.";
    private static final String FIELD_NOT_FOUND = "The required economy/workzone field '{}' in job '{}' was not found.";

    private final String name;
    private final RequiredFieldType<P, V> requiredFieldType;
    private final boolean list;
    private final P defaultValue;

    public RequiredField(String name, RequiredFieldType<P, V> requiredFieldType, boolean list, P defaultValue) {
        this.name = name;
        this.requiredFieldType = requiredFieldType;
        this.list = list;
        this.defaultValue = defaultValue;
    }

    public RequiredField(String name, RequiredFieldType<P, V> requiredFieldType, boolean list) {
        this.name = name;
        this.requiredFieldType = requiredFieldType;
        this.list = list;
        this.defaultValue = null;
    }

    public String getName() {
        return name;
    }

    public RequiredFieldType<P, V> getType() {
        return requiredFieldType;
    }

    public P getDefaultValue() {
        return defaultValue;
    }

    public boolean isList() {
        return list;
    }

    @Nullable
    public P getPrimitive(Job job) {
        try {
            if(FileManager.getJobsNode().node("jobs", job.getID(), this.name).empty()) {
                if(this.defaultValue == null) {
                    PicoJobsCommon.getLogger().error(FIELD_NOT_FOUND, this.name, job.getID());
                } else {
                    return this.defaultValue;
                }
            }

            return FileManager.getJobsNode().node("jobs", job.getID(), this.name).get(requiredFieldType.getPrimitiveType());
        } catch (SerializationException e) {
            PicoJobsCommon.getLogger().error(SERIALIZATION_ERROR, name, job.getID());
            return this.defaultValue;
        }
    }

    @Nonnull
    public List<P> getPrimitiveList(Job job) {
        try {
            if(FileManager.getJobsNode().node("jobs", job.getID(), this.name).empty()) {
                if(this.defaultValue == null) {
                    PicoJobsCommon.getLogger().error(FIELD_NOT_FOUND, this.name, job.getID());
                } else {
                    return Collections.singletonList(this.defaultValue);
                }
            }
            return FileManager.getJobsNode().node("jobs", job.getID(), this.name).getList(requiredFieldType.getPrimitiveType());
        } catch (SerializationException e) {
            PicoJobsCommon.getLogger().error(SERIALIZATION_ERROR, name, job.getID());
            return Collections.singletonList(this.defaultValue);
        }
    }

    @Nullable
    public V getValue(Job job) {
        return requiredFieldType.toValue(getPrimitive(job));
    }

    @Nonnull
    public List<V> getValueList(Job job) {
        return requiredFieldType.toValueList(getPrimitiveList(job));
    }

    public JsonObject toJsonObject() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);

        if(!this.name.equalsIgnoreCase("items")) {
            JsonArray jsonSuggestions = new JsonArray();
            for(P suggestion : this.requiredFieldType.getPrimitiveSuggestions()) {
                JsonObject jsonSuggestion = new JsonObject();
                jsonSuggestion.add("name", gson.toJsonTree(suggestion));
                jsonSuggestion.add("id", gson.toJsonTree(suggestion));
                jsonSuggestions.add(jsonSuggestion);
            }
            jsonObject.add("suggestions", jsonSuggestions);
        }

        jsonObject.addProperty("list", list);
        return jsonObject;
    }
}
