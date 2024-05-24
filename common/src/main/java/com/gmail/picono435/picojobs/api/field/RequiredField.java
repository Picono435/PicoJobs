package com.gmail.picono435.picojobs.api.field;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

    public P getDefaultValue() {
        return defaultValue;
    }

    public boolean isList() {
        return list;
    }

    @Nullable
    public V getValue(Job job) {
        try {
            if(FileManager.getJobsNode().node("jobs", job.getID(), this.name).empty()) {
                if(this.defaultValue == null) {
                    PicoJobsCommon.getLogger().error(FIELD_NOT_FOUND, this.name, job.getID());
                } else {
                    return requiredFieldType.toValue(this.defaultValue);
                }
            }
            // TODO: Check for null
            return requiredFieldType.toValue(FileManager.getJobsNode().node("jobs", job.getID(), this.name).get(requiredFieldType.getPrimitiveType()));
        } catch (SerializationException e) {
            PicoJobsCommon.getLogger().error(SERIALIZATION_ERROR, name, job.getID());
            return requiredFieldType.toValue(this.defaultValue);
        }
    }

    @Nonnull
    public List<V> getValueList(Job job) {
        try {
            if(FileManager.getJobsNode().node("jobs", job.getID(), this.name).empty()) {
                if(this.defaultValue == null) {
                    PicoJobsCommon.getLogger().error(FIELD_NOT_FOUND, this.name, job.getID());
                } else {
                    return Collections.singletonList(requiredFieldType.toValue(this.defaultValue));
                }
            }
            return requiredFieldType.toValueList(FileManager.getJobsNode().node("jobs", job.getID(), this.name).getList(requiredFieldType.getPrimitiveType()));
        } catch (SerializationException e) {
            PicoJobsCommon.getLogger().error(SERIALIZATION_ERROR, name, job.getID());
            return Collections.singletonList(requiredFieldType.toValue(this.defaultValue));
        }
    }

    public JsonObject toJsonObject() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);

        if(!this.name.equalsIgnoreCase("items")) {
            JsonArray jsonSuggestions = gson.toJsonTree(this.requiredFieldType.getPrimitiveSuggestions()).getAsJsonArray();
            jsonObject.add("suggestions", jsonSuggestions);
        }

        jsonObject.addProperty("list", list);
        return jsonObject;
    }
}
