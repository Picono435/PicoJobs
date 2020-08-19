package com.gmail.picono435.picojobs.utils;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bukkit.configuration.ConfigurationSection;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBAPI {
	
	private ConfigurationSection conf;
	
	private MongoCollection<Document> jobPlayers;
    private MongoDatabase mcserverdb;
    private MongoClient client;
    
	public boolean startConnection() {
		conf = PicoJobsPlugin.getInstance().getConfig().getConfigurationSection("storage").getConfigurationSection("mongodb");
         openConnection();
         return true;
	}
	
	public boolean openConnection(){
        client = MongoClients.create(conf.getString("URI"));
        
        mcserverdb = client.getDatabase(conf.getString("database"));
        jobPlayers = mcserverdb.getCollection(conf.getString("collection"));
        return true;
    }
	
	public void addINDB(final String uuid, final String job, final double method, final double level, final double salary, final boolean isWorking) {
		Document obj = new Document("uuid", uuid);
        obj.put("job", job);
        obj.put("method", method);
        obj.put("level", level);
        obj.put("salary", salary);
        obj.put("isWorking", isWorking);

        jobPlayers.insertOne(obj);
	}
	
	public JobPlayer getFromDB(String uuid) {
		Document query = new Document("uuid", uuid);
		Document obj = jobPlayers.find(query).first();
		
		Job job = PicoJobsAPI.getJobsManager().getJob(obj.getString("job"));
		double method = obj.getDouble("method");
		double level = obj.getDouble("level");
		double salary = obj.getDouble("salary");
		boolean isWorking = obj.getBoolean("isWorking");
		
		return new JobPlayer(job, method, level, salary, isWorking);
	}
	
	public List<String> getAllUsers() {
		List<String> uuids = new ArrayList<String>();
		for(Document doc : jobPlayers.find()) {
			uuids.add((String) doc.get("uuid"));
		}
		return uuids;
	}
	
	public void deleteAllDocuments() {
		jobPlayers.deleteMany(new Document());
	}
	
	public void close() {
		client.close();
	}
}
