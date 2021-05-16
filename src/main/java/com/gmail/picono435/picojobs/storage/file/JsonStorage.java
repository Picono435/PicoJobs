package com.gmail.picono435.picojobs.storage.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.UUID;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.storage.StorageFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonStorage extends StorageFactory {

	private File jsonFile;
	
	@Override
	protected boolean initializeStorage() throws Exception {
		this.jsonFile = new File(PicoJobsPlugin.getInstance().getDataFolder(), "picojobs-json.json");
        if (!jsonFile.exists()) {
        	jsonFile.getParentFile().mkdirs();
        	PicoJobsPlugin.getInstance().saveResource("picojobs-json.json", false);
         }
                
        return true;
	}

	@Override
	public boolean createPlayer(UUID uuid) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonObject json = new JsonObject();
		json.addProperty("method", 0D);
		json.addProperty("level", 0D);
		json.addProperty("salary", 0D);
		json.addProperty("is-working", false);
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        jsonObject.add(uuid.toString(), json);
        
        Writer writer = new FileWriter(jsonFile);
        new Gson().toJson(jsonObject, writer);
        writer.close();
        return true;
	}

	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		FileReader reader = new FileReader(jsonFile);
        JsonElement jsonElement = new JsonParser().parse(reader).getAsJsonObject().get(uuid.toString());
        return jsonElement != null;
	}

	@Override
	public String getJob(UUID uuid) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonElement jsonObject = new JsonParser().parse(reader).getAsJsonObject().get(uuid.toString()).getAsJsonObject()
        		.get("job");
        if(jsonObject == null) return null;
        return jsonObject.getAsString();
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonElement jsonObject = new JsonParser().parse(reader).getAsJsonObject().get(uuid.toString()).getAsJsonObject()
        		.get("method");
        return jsonObject.getAsDouble();
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonElement jsonObject = new JsonParser().parse(reader).getAsJsonObject().get(uuid.toString()).getAsJsonObject()
        		.get("level");
        return jsonObject.getAsDouble();
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonElement jsonObject = new JsonParser().parse(reader).getAsJsonObject().get(uuid.toString()).getAsJsonObject()
        		.get("is-working");
        return jsonObject.getAsBoolean();
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonElement jsonObject = new JsonParser().parse(reader).getAsJsonObject().get(uuid.toString()).getAsJsonObject()
        		.get("salary");
        return jsonObject.getAsDouble();
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
        JsonObject jsonObject = json.get(uuid.toString()).getAsJsonObject();
        jsonObject.addProperty("job", job);
        json.add(uuid.toString(), jsonObject);
        Writer writer = new FileWriter(jsonFile);
        new Gson().toJson(json, writer);
        writer.close();
        return true;
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
        JsonObject jsonObject = json.get(uuid.toString()).getAsJsonObject();
        jsonObject.addProperty("method", method);
        json.add(uuid.toString(), jsonObject);
        Writer writer = new FileWriter(jsonFile);
        new Gson().toJson(json, writer);
        writer.close();
        return true;
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
        JsonObject jsonObject = json.get(uuid.toString()).getAsJsonObject();
        jsonObject.addProperty("level", level);
        json.add(uuid.toString(), jsonObject);
        Writer writer = new FileWriter(jsonFile);
        new Gson().toJson(json, writer);
        writer.close();
        return true;
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
        JsonObject jsonObject = json.get(uuid.toString()).getAsJsonObject();
        jsonObject.addProperty("is-working", isWorking);
        json.add(uuid.toString(), jsonObject);
        Writer writer = new FileWriter(jsonFile);
        new Gson().toJson(json, writer);
        writer.close();
        return true;
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		FileReader reader = new FileReader(jsonFile);
		JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
        JsonObject jsonObject = json.get(uuid.toString()).getAsJsonObject();
        jsonObject.addProperty("salary", salary);
        json.add(uuid.toString(), jsonObject);
        Writer writer = new FileWriter(jsonFile);
        new Gson().toJson(json, writer);
        writer.close();
        return true;
	}

	@Override
	protected void destroyStorage() {}

}
