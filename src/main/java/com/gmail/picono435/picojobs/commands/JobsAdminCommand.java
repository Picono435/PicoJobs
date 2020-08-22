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
import org.codehaus.plexus.util.FileUtils;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.managers.LanguageManager;
import com.gmail.picono435.picojobs.utils.FileCreator;

public class JobsAdminCommand implements CommandExecutor, TabCompleter {

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
			if(!PicoJobsPlugin.isOldVersion()) {
				p.sendMessage(LanguageManager.getMessage("already-updated", pl));
				return true;
			}
			p.sendMessage(LanguageManager.getMessage("update-started", pl));
			if(!updatePlugin()) {
				p.sendMessage(LanguageManager.getMessage("unknow-error", pl));
				return true;
			}
			p.sendMessage(LanguageManager.getMessage("updated-sucefully", pl));
			return true;
		}
		
		// UPDATE COMMAND
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
			
			if(args.length == 1) {
				List<String> list = new ArrayList<String>();
				if(p.hasPermission("picojobs.admin")) {
					list.add(helpString);
					list.add(infoString);
					list.add(reloadString);
					list.add(updateString);
					list.add(aboutString);
				}
				return list;
			}
		}
		return null;
	}
	
	private boolean updatePlugin() {
		try {
			PicoJobsPlugin.sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Updating the PicoJobs plugin to the version v" + PicoJobsPlugin.getLastestPluginVersion() + ". Please wait, the server may lag a little bit...");
			
			String downloadUrl = "https://github.com/Picono435/PicoJobs/releases/download/" + PicoJobsPlugin.getLastestPluginVersion() + "/PicoJobs-" + PicoJobsPlugin.getLastestPluginVersion() + ".jar";
			URL url = new URL(downloadUrl);
			
			Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
			getFileMethod.setAccessible(true);
			File oldFile = (File) getFileMethod.invoke(PicoJobsPlugin.getPlugin());
			
			File fileOutput = new File(PicoJobsPlugin.getInstance().getDataFolder().getParentFile().getPath() + File.separatorChar + "update" + File.separatorChar + oldFile.getName());
			if(!fileOutput.exists()) {
				fileOutput.mkdirs();
			}
			
			downloadFile(url, fileOutput);
			
            PicoJobsPlugin.sendConsoleMessage(ChatColor.AQUA + "[PicoJobs] Updated PicoJobs plugin to version v" + PicoJobsPlugin.getLastestPluginVersion() + " succefully.");
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void downloadFile(URL url, File localFile) {
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
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}.runTaskAsynchronously(PicoJobsPlugin.getPlugin());
	}
}
