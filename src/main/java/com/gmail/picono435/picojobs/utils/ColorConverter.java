package com.gmail.picono435.picojobs.utils;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorConverter {

    public static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String translateAlternateColorCodes(String string) {
        if(PicoJobsPlugin.getInstance().isMoreThan("1.16")) {
            try {
                Matcher matcher = pattern.matcher(string);
                while(matcher.find()) {
                    String color = string.substring(matcher.start(), matcher.end());
                    string = string.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                    matcher = pattern.matcher(string);
                }
            } catch(Exception e) {
                return ChatColor.translateAlternateColorCodes('&', string);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
