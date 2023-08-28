package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.ColorConverter;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BukkitColorConverter implements ColorConverter {

    public static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    @Override
    public String translateAlternateColorCodes(String string) {
        if(PicoJobsCommon.isMoreThan("1.16")) {
            try {
                Matcher matcher = pattern.matcher(string);
                while(matcher.find()) {
                    String color = string.substring(matcher.start(), matcher.end());
                    string = string.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                    matcher = pattern.matcher(string);
                }
            } catch(Exception event) {
                return ChatColor.translateAlternateColorCodes('&', string);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public String stripColor(String string) {
        return ChatColor.stripColor(string);
    }
}
