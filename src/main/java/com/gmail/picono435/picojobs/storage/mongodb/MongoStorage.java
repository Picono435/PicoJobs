package com.gmail.picono435.picojobs.storage.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bukkit.configuration.ConfigurationSection;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.storage.StorageFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoStorage extends StorageFactory {
	
	private ConfigurationSection configurationSection;
	private MongoClient mongoClient;
	private String database;
	private String collection;

	@Override
	protected boolean initializeStorage() throws Exception {
		configurationSection = PicoJobsAPI.getSettingsManager().getMongoDBConfiguration();
		String uri = configurationSection.getString("URI");
		String database = configurationSection.getString("database");
		String collection = configurationSection.getString("collection");
		
		this.mongoClient = MongoClients.create(MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri)).build());
		this.database = database;
		this.collection = collection;
		return true;
	}

	@Override
	public String getJob(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		List<Document> result = mongo.find(new BasicDBObject("uuid", uuid.toString())).into(new ArrayList<BasicDBObject>());
		return null;
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void destroyStorage() {
		this.mongoClient.close();
	}

}
