package com.gmail.picono435.picojobs.api.utils;

import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.file.FileManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Collections;
import java.util.List;

public class RequiredField<T> {

    private String name;
    private T defaultValue;

    public RequiredField(String name) {
        this(name, null);
    }

    public RequiredField(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue(JobPlayer jobPlayer, Class<T> type) {
        try {
            if(FileManager.getJobsNode().node("jobs", jobPlayer.getJob().getID(), this.name).empty()) {
                if(this.defaultValue == null) {
                    PicoJobsCommon.getLogger().error("The required economy/workzone field '" + this.name + "' in job '" + jobPlayer.getJob().getID() + "' was not found.");
                } else {
                    return this.defaultValue;
                }
            }
            return FileManager.getJobsNode().node("jobs", jobPlayer.getJob().getID(), this.name).get(type);
        } catch (SerializationException e) {
            PicoJobsCommon.getLogger().error("Error with getting required field named '" + name + "' for job '" + jobPlayer.getJob().getID() + "'.");
            return this.defaultValue;
        }
    }

    public List<T> getValueList(JobPlayer jobPlayer, Class<T> type) {
        try {
            if(FileManager.getJobsNode().node("jobs", jobPlayer.getJob().getID(), this.name).empty()) {
                if(this.defaultValue == null) {
                    PicoJobsCommon.getLogger().error("The required economy/workzone field '" + this.name + "' in job '" + jobPlayer.getJob().getID() + "' was not found.");
                } else {
                    return Collections.singletonList(this.defaultValue);
                }
            }
            return FileManager.getJobsNode().node("jobs", jobPlayer.getJob().getID(), this.name).getList(type);
        } catch (SerializationException e) {
            PicoJobsCommon.getLogger().error("Error with getting required field named '" + name + "' for job '" + jobPlayer.getJob().getID() + "'.");
            return Collections.singletonList(this.defaultValue);
        }
    }

    public JsonElement getJsonElement(T object) {
        if(object instanceof String) {
            return new JsonPrimitive((String) object);
        } else if(object instanceof Number) {
            return new JsonPrimitive((Number) object);
        } else if(object instanceof Boolean) {
            return new JsonPrimitive((boolean) object);
        } else {
            return null;
        }
    }

    public JsonElement getJsonElement(List<T> object) {
        JsonArray jsonArray = new JsonArray();
        for (T s : object) jsonArray.add(getJsonElement(s));
        return jsonArray;
    }
}
