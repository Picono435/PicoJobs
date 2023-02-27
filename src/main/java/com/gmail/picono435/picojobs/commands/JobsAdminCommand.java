package com.gmail.picono435.picojobs.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

import com.gmail.picono435.picojobs.api.*;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JobsAdminCommand implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equals("jobsadmin")) return false;
		CommandSender p = sender;
		if(!p.hasPermission("picojobs.admin")) {
			p.sendMessage(LanguageManager.formatMessage("&7PicoJobs v" + PicoJobsPlugin.getInstance().getDescription().getVersion() + ". (&8&nhttps://discord.gg/wQj53Hy&r&7)"));
			return true;
		}
		if(args.length < 1) {
			p.sendMessage(LanguageManager.getFormat("admin-commands", null));
			return true;
		}
		String subcmd = args[0];
		String helpString = LanguageManager.getSubCommandAlias("help");
		String infoString = LanguageManager.getSubCommandAlias("info");
		String reloadString = LanguageManager.getSubCommandAlias("reload");
		String updateString = LanguageManager.getSubCommandAlias("update");
		String aboutString = LanguageManager.getSubCommandAlias("about");
		String setString = LanguageManager.getSubCommandAlias("set");
		String editorString = LanguageManager.getSubCommandAlias("editor");
		String debugString = LanguageManager.getSubCommandAlias("debug");
		
		String salaryString = LanguageManager.getSubCommandAlias("salary");
		String methodString = LanguageManager.getSubCommandAlias("method");
		String jobString = LanguageManager.getSubCommandAlias("job");
		
		Player pl = null;
		if(sender instanceof Player) {
			pl = (Player)sender;
		}
		
		// HELP COMMAND
		if(subcmd.equalsIgnoreCase("help") || subcmd.equalsIgnoreCase(helpString)) {
			p.sendMessage(LanguageManager.getFormat("admin-commands", pl));
			return true;
		}
		
		// INFO COMMAND
		if(subcmd.equalsIgnoreCase("info") || subcmd.equalsIgnoreCase(infoString)) {
			if(args.length < 2) {
				p.sendMessage(LanguageManager.getMessage("no-args", pl));
				return true;
			}
			String playername = args[1];
			Player player = Bukkit.getPlayer(playername);
			if(player == null) {
				p.sendMessage(LanguageManager.getMessage("player-not-found", pl));
				return true;
			}
			p.sendMessage(LanguageManager.getFormat("info-command", player));
			return true;
		}
		
		// RELOAD COMMAND
		if(subcmd.equalsIgnoreCase("reload") || subcmd.equalsIgnoreCase(reloadString)) {
			if(!FileCreator.reloadFiles()) {
				p.sendMessage(LanguageManager.getMessage("unknow-error", pl));
				return true;
			}
			PicoJobsAPI.getStorageManager().destroyStorageFactory();
			PicoJobsAPI.getStorageManager().initializeStorageFactory();
			for(UUID uuid : PicoJobsAPI.getStorageManager().getCacheManager().getAllFromCache()) {
				if(PicoJobsAPI.getPlayersManager().getJobPlayer(uuid).getJob() == null) continue;
				PicoJobsAPI.getPlayersManager().getJobPlayer(uuid).setJob(PicoJobsAPI.getJobsManager().getJob(PicoJobsAPI.getPlayersManager().getJobPlayer(uuid).getJob().getID()));
			}
			p.sendMessage(LanguageManager.getMessage("reload-command", pl));
			return true;
		}
		
		// UPDATE COMMAND
		if(subcmd.equalsIgnoreCase("update") || subcmd.equalsIgnoreCase(updateString)) {
			if(!PicoJobsPlugin.getInstance().isOldVersion()) {
				p.sendMessage(LanguageManager.getMessage("already-updated", pl));
				return true;
			}
			p.sendMessage(LanguageManager.getMessage("update-started", pl));
			if(!PicoJobsPlugin.getInstance().updatePlugin(p, LanguageManager.getMessage("updated-sucefully"))) {
				p.sendMessage(LanguageManager.getMessage("unknow-error", pl));
				return true;
			}
			return true;
		}
		
		// ABOUT COMMAND
		if(subcmd.equalsIgnoreCase("about") || subcmd.equalsIgnoreCase(aboutString)) {
			String message = "&eHere are some information about the plugin\n"
					+ "&ePlugin version:&6 v" + PicoJobsPlugin.getInstance().getDescription().getVersion() + "\n"
							+ "&eBukkit Version:&6 " + Bukkit.getVersion() + "\n"
									+ "&eDiscord Server:&6 https://discord.gg/wQj53Hy\n"
									+ "&eGitHub Repo:&6 https://github.com/Picono435/PicoJobs\n"
									+ "&eIssues Tracker:&6 https://github.com/Picono435/PicoJobs/issues";
			p.sendMessage(LanguageManager.formatMessage(message));
			return true;
		}
		
		// SET COMMAND
		if(subcmd.equalsIgnoreCase("set") || subcmd.equalsIgnoreCase(setString)) {
			if(args.length < 2) {
				p.sendMessage(LanguageManager.getMessage("no-args", pl));
				return true;
			}
			// /jobsadmin set salary Frankandstile 1000
			if(args[1].equalsIgnoreCase("salary") || args[1].equalsIgnoreCase(salaryString)) {
				if(args.length < 4) {
					p.sendMessage(LanguageManager.getMessage("no-args", pl));
					return true;
				}
				JobPlayer jpNew = PicoJobsAPI.getPlayersManager().getJobPlayer(args[2]);
				if(jpNew == null)  {
					p.sendMessage(LanguageManager.getMessage("player-not-found", pl));
					return true;
				}
				int newSalary = 0;
				try {
					newSalary = Integer.parseInt(args[3]);
				} catch(Exception ex) {
					p.sendMessage(LanguageManager.getMessage("no-args", pl));
					return true;
				}
				jpNew.setSalary(newSalary);
				p.sendMessage(LanguageManager.getMessage("sucefully", pl));
				return true;
			}
			
			if(args[1].equalsIgnoreCase("method") || args[1].equalsIgnoreCase(methodString)) {
				if(args.length < 4) {
					p.sendMessage(LanguageManager.getMessage("no-args", pl));
					return true;
				}
				JobPlayer jpNew = PicoJobsAPI.getPlayersManager().getJobPlayer(args[2]);
				if(jpNew == null)  {
					p.sendMessage(LanguageManager.getMessage("player-not-found", pl));
					return true;
				}
				int newMethod = 0;
				try {
					newMethod = Integer.parseInt(args[3]);
				} catch(Exception ex) {
					p.sendMessage(LanguageManager.getMessage("no-args", pl));
					return true;
				}
				jpNew.setMethod(newMethod);
				p.sendMessage(LanguageManager.getMessage("sucefully", pl));
				return true;
			}
			
			if(args[1].equalsIgnoreCase("job") || args[1].equalsIgnoreCase(jobString)) {
				if(args.length < 4) {
					p.sendMessage(LanguageManager.getMessage("no-args", pl));
					return true;
				}
				JobPlayer jpNew = PicoJobsAPI.getPlayersManager().getJobPlayer(args[2]);
				if(jpNew == null)  {
					p.sendMessage(LanguageManager.getMessage("player-not-found", pl));
					return true;
				}
				Job job = PicoJobsAPI.getJobsManager().getJob(args[3]);
				if(job == null) {
					job = PicoJobsAPI.getJobsManager().getJobByStrippedColorDisplayname(args[3]);
					if(job == null) {
						p.sendMessage(LanguageManager.getMessage("no-args", pl));
						return true;
					}
				}
				jpNew.setJob(job);
				p.sendMessage(LanguageManager.getMessage("sucefully", pl));
				return true;
			}
			
			p.sendMessage(LanguageManager.getFormat("admin-commands", pl));
		}
		
		if(subcmd.equalsIgnoreCase("editor") || subcmd.equalsIgnoreCase(editorString) || subcmd.equalsIgnoreCase("settings")) {
			// CREATE EDITOR
			p.sendMessage(LanguageManager.formatMessage("&7Preparing a new editor session. Please wait..."));
			String editor = createEditor(sender);
			if(editor != null) {
				p.sendMessage(LanguageManager.formatMessage("&aClick the link below to open the editor:\n&b&e" + PicoJobsPlugin.EDITOR_STRING + "/picojobs/" + editor));
			} else {
				p.sendMessage(LanguageManager.formatMessage("&cThis feature is not yet avaiable for public. For more information check our discord or/and ou wiki."));
			}
			return true;
		}

		if(subcmd.equalsIgnoreCase("applyedits") || subcmd.equalsIgnoreCase("ae")) {
			// APPLYEDITS
			if(args.length < 2) {
				p.sendMessage(LanguageManager.getMessage("no-args", pl));
				return true;
			}
			if(applyEditsFromEditor(sender, args[1])) {
				p.sendMessage(LanguageManager.formatMessage("&aWeb editor data was applied to the jobs configuration successfully."));
			} else {
				p.sendMessage(LanguageManager.formatMessage("&cWeb editor data was not applied to the jobs configuration because of a unexpected error."));
			}
			return true;
		}

		if(subcmd.equalsIgnoreCase("debug") || subcmd.equalsIgnoreCase(debugString)) {
			// CREATE EDITOR
			if(PicoJobsPlugin.getInstance().getConfig().getBoolean("debug")) {
				p.sendMessage(LanguageManager.formatMessage("&7Disabling DEBUG mode..."));
				PicoJobsPlugin.getInstance().getConfig().set("debug", false);
				PicoJobsPlugin.getInstance().saveConfig();
				PicoJobsPlugin.getInstance().getLoggingHandler().setLevel(Level.INFO);
				PicoJobsPlugin.getInstance().debugMessage("Debug mode disabled.");
				p.sendMessage(LanguageManager.formatMessage("&cThe &bDEBUG&c mode was disabled successfully. This will stop spamming your console with random messages."));
			} else {
				p.sendMessage(LanguageManager.formatMessage("&7Enabling DEBUG mode..."));
				PicoJobsPlugin.getInstance().getConfig().set("debug", true);
				PicoJobsPlugin.getInstance().saveConfig();
				PicoJobsPlugin.getInstance().getLoggingHandler().setLevel(Level.FINEST);
				PicoJobsPlugin.getInstance().debugMessage("Debug mode enabled.");
				p.sendMessage(LanguageManager.formatMessage("&aThe &bDEBUG&a mode was enabled successfully. This may spam your console with random messages."));
			}
			return true;
		}
		p.sendMessage(LanguageManager.getFormat("admin-commands", pl));
		return true;
	}
	
	/*
	 * 
	 * Tab Complete
	 * 
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("jobsadmin")) {
			String helpString = LanguageManager.getSubCommandAlias("help");
			String infoString = LanguageManager.getSubCommandAlias("info");
			String reloadString = LanguageManager.getSubCommandAlias("reload");
			String updateString = LanguageManager.getSubCommandAlias("update");
			String aboutString = LanguageManager.getSubCommandAlias("about");
			String setString = LanguageManager.getSubCommandAlias("set");
			String editorString = LanguageManager.getSubCommandAlias("editor");
			
			String salaryString = LanguageManager.getSubCommandAlias("salary");
			String methodString = LanguageManager.getSubCommandAlias("method");
			String jobString = LanguageManager.getSubCommandAlias("job");
			
			if(!p.hasPermission("picojobs.admin")) return null;
			
			List<String> list = new ArrayList<String>();
			
			if(args.length == 1) {
				list.add(helpString);
				list.add(infoString);
				list.add(reloadString);
				list.add(updateString);
				list.add(aboutString);
				list.add(setString);
				list.add(editorString);
				return list;
			}
			
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase(setString)) {
					list.add(salaryString);
					list.add(methodString);
					list.add(jobString);
					return list;
				}
			}
			
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase(setString)) {
					return null;
				}
			}
			
			if(args.length == 4) {
				if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase(setString)) {
					if(args[1].equalsIgnoreCase("salary") || args[1].equalsIgnoreCase(salaryString)) {
						list.add("[<" + salaryString + ">]");
					} else if(args[1].equalsIgnoreCase("method") || args[1].equalsIgnoreCase(methodString)) {
						list.add("[<" + methodString + ">]");
					} else if(args[1].equalsIgnoreCase("job") || args[1].equalsIgnoreCase(methodString)) {
						for(Job j : PicoJobsAPI.getJobsManager().getJobs()) {
							list.add(ChatColor.stripColor(j.getDisplayName()));
						}
					}
					return list;
				}
			}
		}
		return null;
	}
	
	private String createEditor(CommandSender sender) {
		try {
			String serverVersionString = Bukkit.getBukkitVersion();
			int spaceIndex = serverVersionString.indexOf("-");
			serverVersionString = serverVersionString.substring(0, spaceIndex);
			serverVersionString = StringUtils.substringBeforeLast(serverVersionString, ".");

			JsonParser parser = new JsonParser();
			JsonObject jsonEditor = new JsonObject();
			jsonEditor.addProperty("plugin", PicoJobsPlugin.getInstance().getName());
			jsonEditor.addProperty("server", InetAddress.getLocalHost() + ":" + Bukkit.getServer().getPort());
			jsonEditor.addProperty("author", sender.getName());
			jsonEditor.addProperty("minecraftVersion", serverVersionString);

			JsonArray jsonEconomies = new JsonArray();
			for(String economy : PicoJobsPlugin.getInstance().economies.keySet()) {
				jsonEconomies.add(economy);
			}
			jsonEditor.add("economies", jsonEconomies);

			JsonObject jsonTypes = new JsonObject();
			for(Type type : Type.values()) {
				jsonTypes.addProperty(type.name(), type.getWhitelistType());
			}
			jsonEditor.add("types", jsonTypes);

			JsonObject jsonJobs = new JsonObject();
			for(Job job : PicoJobsAPI.getJobsManager().getJobs()) {
				jsonJobs.add(job.getID(), job.toJsonObject());
			}
			jsonEditor.add("jobs", jsonJobs);
			
			String charset = "UTF-8";
			
			URL url = new URL(PicoJobsPlugin.EDITOR_STRING + "/picojobs/create");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            
            String json = jsonEditor.toString();
            try (OutputStream output = con.getOutputStream()) {
                output.write(json.getBytes(charset));
             }
            
            try(BufferedReader br = new BufferedReader(
            		  new InputStreamReader(con.getInputStream(), "utf-8"))) {
            		    StringBuilder responseString = new StringBuilder();
            		    String responseLine = null;
            		    while ((responseLine = br.readLine()) != null) {
            		        responseString.append(responseLine.trim());
            		    }
            		    JsonObject response = (JsonObject) parser.parse(responseString.toString());
            		    return response.get("editor").getAsString();
            		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean applyEditsFromEditor(CommandSender sender, String editor) {
		try {
			JsonParser parser = new JsonParser();
			JsonObject jsonEditor = new JsonObject();
			jsonEditor.addProperty("plugin", PicoJobsPlugin.getInstance().getName());
			jsonEditor.addProperty("server", InetAddress.getLocalHost() + ":" + Bukkit.getServer().getPort());
			jsonEditor.addProperty("author", sender.getName());
			jsonEditor.addProperty("editor", editor);

			String charset = "UTF-8";

			URL url = new URL(PicoJobsPlugin.EDITOR_STRING + "/picojobs/get");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Charset", charset);
			con.setRequestProperty("Content-Type", "application/json;charset=" + charset);
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			String json = jsonEditor.toString();
			try (OutputStream output = con.getOutputStream()) {
				output.write(json.getBytes(charset));
			}

			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder responseString = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					responseString.append(responseLine.trim());
				}
				JsonObject response = (JsonObject) parser.parse(responseString.toString());
				FileConfiguration jobsConfiguration = new YamlConfiguration();
				Map<String, ConfigurationSection> jobsSection = new HashMap<>();
				if(response.get("status").getAsInt() == 3000) {
					PicoJobsPlugin.getInstance().jobs.clear();
					System.out.println(response);
					JsonObject jobsObject = (JsonObject) response.get("data");
					for(String jobID : jobsObject.keySet()) {
						Job job = new Job(jobsObject.get(jobID).getAsJsonObject());
						jobsSection.put(jobID, job.toYamlConfiguration());
						PicoJobsPlugin.getInstance().jobs.put(jobID, job);
					}
					jobsConfiguration.createSection("jobs", jobsSection);
					FileCreator.setJobsConfig(jobsConfiguration);
					FileCreator.getJobsConfig().save(FileCreator.getJobsFile());
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
