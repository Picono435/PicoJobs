package com.gmail.picono435.picojobs.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.assimbly.docconverter.DocConverter;
import org.bson.json.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;

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
		String settingsString = LanguageManager.getSubCommandAlias("settings");
		
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
		
		if(subcmd.equalsIgnoreCase("settings") || subcmd.equalsIgnoreCase(settingsString) || subcmd.equalsIgnoreCase("editor")) {
			// CREATE EDITOR
			p.sendMessage(LanguageManager.formatMessage("&7Preparing a new editor session. Please wait..."));
			p.sendMessage(LanguageManager.formatMessage("&cThis feature is still in development, for more information check our discord."));
			/*String editor = createEditor(sender);
			if(editor != null) {
				p.sendMessage(LanguageManager.formatMessage("&aClick the link below to open the editor:\n&b&ehttp://www.piconodev.tk/editor/picojobs/" + editor));
			} else {
				p.sendMessage(LanguageManager.getMessage("unknow-error"));
			}*/
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
			String settingsString = LanguageManager.getSubCommandAlias("settings");
			
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
				list.add(settingsString);
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
			
			JSONParser parser = new JSONParser();
			String jobsConfigYAML = DocConverter.convertFileToString(FileCreator.getJobsFile().getPath());
			String jobsConfigJSON = DocConverter.convertYamlToJson(jobsConfigYAML);
			
			JSONObject jsonJobs = new JSONObject();
			jsonJobs.put("plugin", PicoJobsPlugin.getInstance().getName());
			jsonJobs.put("server", InetAddress.getLocalHost() + ":" + Bukkit.getServer().getPort());
			jsonJobs.put("author", sender.getName());
			jsonJobs.put("minecraftVersion", serverVersionString);
			jsonJobs.put("economies", new JSONObject());
			jsonJobs.put("config", parser.parse(jobsConfigJSON));
			
			String charset = "UTF-8";
			
			URL url = new URL("http://localhost:3011/editor/picojobs/create");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            
            String json = jsonJobs.toString();
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
            		    org.json.simple.JSONObject response = (org.json.simple.JSONObject) parser.parse(responseString.toString());
            		    return (String)response.get("editor");
            		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
