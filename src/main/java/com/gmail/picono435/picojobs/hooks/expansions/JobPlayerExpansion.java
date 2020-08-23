package com.gmail.picono435.picojobs.hooks.expansions;

import java.text.NumberFormat;
import java.util.Locale;

import org.bukkit.entity.Player;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.JobPlayer;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.managers.LanguageManager;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;

/**
 * This class will be registered through the register-method in the 
 * plugins onEnable-method.
 */
public class JobPlayerExpansion extends PlaceholderExpansion {

	NumberFormat df = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
    private PicoJobsPlugin plugin;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *        The instance of our plugin.
     */
    public JobPlayerExpansion(PicoJobsPlugin plugin){
        this.plugin = plugin;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     * 
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest 
     * method to obtain a value if a placeholder starts with our 
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "jobplayer";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier 
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  p
     *         A player.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player p, String identifier){

    	JobPlayer jp = PicoJobsAPI.getPlayersManager().getJobPlayer(p);
    	
        if(identifier.equals("job")) {
            if(!jp.hasJob()) {
            	return LanguageManager.getFormat("none-format", p);
            }
            return jp.getJob().getDisplayName();
        }
        
        if(identifier.equals("work")) {
        	Job job = jp.getJob();
        	if(job == null) {
        		return LanguageManager.getFormat("none-format", p);
        	}
        	Type type = job.getType();
        	String configString = type.name().toLowerCase() + "-work";
        	if(type == Type.KILL && !job.getKillJob().equals("")) {
        		configString = "kill-specific-work";
        	}
        	double level = jp.getMethodLevel();
        	int reqmethod = (int) (job.getMethod() * level * job.getMethodFrequency());
        	double value = reqmethod - jp.getMethod();
        	String work = LanguageManager.getFormat("general-work", p);
        	if(!job.getWorkMessage().equals("")) {
        		work = work.replace("%a%", PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', job.getWorkMessage())));
        	} else {
        		work = work.replace("%a%", LanguageManager.getFormat(configString, p));
        	}
        	work = work.replace("%a%", df.format(value));
        	return work;
        }
        
        if(identifier.equals("salary")) {
            return df.format(jp.getSalary());
        }
        
        if(identifier.equals("working")) {
        	return jp.isWorking() + "";
        }

        return "Incorrect Placeholder.";
    }
}