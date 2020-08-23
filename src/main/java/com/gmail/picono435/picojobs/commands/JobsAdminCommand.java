package com.gmail.picono435.picojobs.commands;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
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
		
		String salaryString = LanguageManager.getSubCommandAlias("salary");
		String methodString = LanguageManager.getSubCommandAlias("method");
		
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
			if(!updatePlugin(p)) {
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
			
			p.sendMessage(LanguageManager.getFormat("admin-commands", null));
		}
		return true;
	}
	
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
			
			String salaryString = LanguageManager.getSubCommandAlias("salary");
			String methodString = LanguageManager.getSubCommandAlias("method");
			
			if(!p.hasPermission("picojobs.admin")) return null;
			
			List<String> list = new ArrayList<String>();
			
			if(args.length == 1) {
				list.add(helpString);
				list.add(infoString);
				list.add(reloadString);
				list.add(updateString);
				list.add(aboutString);
				list.add(setString);
				return list;
			}
			
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase(setString)) {
					list.add(salaryString);
					list.add(methodString);
					return list;
				}
			}
			
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase(setString)) {
					list.add("[<player>]");
					return list;
				}
			}
			
			if(args.length == 4) {
				if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase(setString)) {
					if(args[1].equalsIgnoreCase("salary") || args[1].equalsIgnoreCase(salaryString)) {
						list.add("[<" + salaryString + ">]");
					} else if(args[1].equalsIgnoreCase("method") || args[1].equalsIgnoreCase(methodString)) {
						list.add("[<" + methodString + ">]");
					}
					return list;
				}
			}
		}
		return null;
	}
	
	private boolean updatePlugin(CommandSender p) {
		try {
			PicoJobsPlugin.getInstance().sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Updating the PicoJobs plugin to the version v" + PicoJobsPlugin.getInstance().getLastestPluginVersion() + ". Please wait, the server may lag a little bit...");
			
			String downloadUrl = "https://github.com/Picono435/PicoJobs/releases/download/" + PicoJobsPlugin.getInstance().getLastestPluginVersion() + "/PicoJobs-" + PicoJobsPlugin.getInstance().getLastestPluginVersion() + ".jar";
			URL url = new URL(downloadUrl);
			
			Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
			getFileMethod.setAccessible(true);
			File oldFile = (File) getFileMethod.invoke(PicoJobsPlugin.getInstance());
			
			File fileOutput = new File(PicoJobsPlugin.getInstance().getDataFolder().getParentFile().getPath() + File.separatorChar + "update" + File.separatorChar + oldFile.getName());
			if(!fileOutput.exists()) {
				fileOutput.mkdirs();
			}
			
			downloadFile(url, fileOutput, p);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void downloadFile(URL url, File localFile, CommandSender p) {
		new BukkitRunnable() {
			public void run() {
				try {
					if (localFile.exists()) {
				        localFile.delete();
				    }
				    localFile.createNewFile();
				    OutputStream out = new BufferedOutputStream(new FileOutputStream(localFile.getPath()));
				    URLConnection conn = url.openConnection();
				    String encoded = Base64.getEncoder().encodeToString(("username"+":"+"password").getBytes(StandardCharsets.UTF_8));  //Java 8
				    conn.setRequestProperty("Authorization", "Basic "+ encoded);
				    InputStream in = conn.getInputStream();
				    byte[] buffer = new byte[1024];

				    int numRead;
				    while ((numRead = in.read(buffer)) != -1) {
				        out.write(buffer, 0, numRead);
				    }
				    if (in != null) {
				        in.close();
				    }
				    if (out != null) {
				        out.close();
				    }
				    p.sendMessage(LanguageManager.getMessage("updated-sucefully"));
				    PicoJobsPlugin.getInstance().sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Updated PicoJobs plugin to version v" + PicoJobsPlugin.getInstance().getLastestPluginVersion() + " succefully.");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}.runTaskAsynchronously(PicoJobsPlugin.getInstance());
	}
}
