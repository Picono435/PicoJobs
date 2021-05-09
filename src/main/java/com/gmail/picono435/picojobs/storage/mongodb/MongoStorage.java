package com.gmail.picono435.picojobs.storage.mongodb;

import java.util.UUID;

import org.bson.Document;
import org.bukkit.configuration.ConfigurationSection;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.storage.StorageFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

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
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.getString("job");
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.getDouble("method");
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.getDouble("level");
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.getBoolean("is-working");
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.getDouble("salary");
	}

	@Override
	public boolean setJob(UUID uuid, String job) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		long rs = mongo.updateOne(new BasicDBObject("uuid", uuid.toString()), new BasicDBObject("job", job)).getMatchedCount();
		return rs >= 1;
	}

	@Override
	public boolean setMethod(UUID uuid, double method) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		long rs = mongo.updateOne(new BasicDBObject("uuid", uuid.toString()), new BasicDBObject("method", method)).getMatchedCount();
		return rs >= 1;
	}

	@Override
	public boolean setMethodLevel(UUID uuid, double level) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		long rs = mongo.updateOne(new BasicDBObject("uuid", uuid.toString()), new BasicDBObject("level", level)).getMatchedCount();
		return rs >= 1;
	}

	@Override
	public boolean setWorking(UUID uuid, boolean isWorking) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		long rs = mongo.updateOne(new BasicDBObject("uuid", uuid.toString()), new BasicDBObject("is-working", isWorking)).getMatchedCount();
		return rs >= 1;
	}

	@Override
	public boolean setSalary(UUID uuid, double salary) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		long rs = mongo.updateOne(new BasicDBObject("uuid", uuid.toString()), new BasicDBObject("salary", salary)).getMatchedCount();
		return rs >= 1;
	}

	@Override
	protected void destroyStorage() {
		this.mongoClient.close();
	}

}
