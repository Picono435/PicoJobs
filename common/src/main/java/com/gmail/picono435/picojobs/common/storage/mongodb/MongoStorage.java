package com.gmail.picono435.picojobs.common.storage.mongodb;

import java.util.UUID;

import com.gmail.picono435.picojobs.common.storage.StorageFactory;
import org.bson.BsonValue;
import org.bson.Document;

import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.spongepowered.configurate.ConfigurationNode;

public class MongoStorage extends StorageFactory {
	
	private ConfigurationNode configurationNode;
	private MongoClient mongoClient;
	private String database;
	private String collection;

	@Override
	protected boolean initializeStorage() throws Exception {
		configurationNode = PicoJobsAPI.getSettingsManager().getMongoDBConfiguration();
		String uri = configurationNode.node("URI").getString();
		String database = configurationNode.node("database").getString();
		String collection = configurationNode.node("collection").getString();
		
		this.mongoClient = MongoClients.create(MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri)).build());
		this.database = database;
		this.collection = collection;
		return true;
	}

	@Override
	public boolean createPlayer(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		BsonValue rs = mongo.insertOne(new Document("uuid", uuid.toString())).getInsertedId();
		return rs != null;
	}
	
	@Override
	public boolean playerExists(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result != null;
	}
	
	@Override
	public String getJob(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.containsKey("job") ? result.getString("job") : null;
	}

	@Override
	public double getMethod(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.containsKey("method") ? result.getDouble("method") : 0;
	}

	@Override
	public double getMethodLevel(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.containsKey("level") ? result.getDouble("level") : 0;
	}

	@Override
	public boolean isWorking(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.containsKey("is-working") ? result.getBoolean("is-working") : false;
	}

	@Override
	public double getSalary(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.containsKey("salary") ? result.getDouble("salary") : 0;
	}

	@Override
	public long getSalaryCooldown(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.containsKey("salary-cooldown") ? result.getLong("salary-cooldown") : 0;
	}

	@Override
	public long getLeaveCooldown(UUID uuid) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		Document result = mongo.find(new BasicDBObject("uuid", uuid.toString())).first();
		return result.containsKey("leave-cooldown") ? result.getLong("leave-cooldown") : 0;
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
	public boolean setSalaryCooldown(UUID uuid, long salaryCooldown) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		long rs = mongo.updateOne(new BasicDBObject("uuid", uuid.toString()), new BasicDBObject("salary-cooldown", salaryCooldown)).getMatchedCount();
		return rs >= 1;
	}

	@Override
	public boolean setLeaveCooldown(UUID uuid, long leaveCooldown) throws Exception {
		MongoCollection<Document> mongo = this.mongoClient.getDatabase(this.database).getCollection(this.collection);
		long rs = mongo.updateOne(new BasicDBObject("uuid", uuid.toString()), new BasicDBObject("leave-cooldown", leaveCooldown)).getMatchedCount();
		return rs >= 1;
	}

	@Override
	protected void destroyStorage() {
		this.mongoClient.close();
	}

}
