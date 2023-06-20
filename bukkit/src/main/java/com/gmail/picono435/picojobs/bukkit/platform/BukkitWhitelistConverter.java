package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.bukkit.utils.MatchUtils;
import com.gmail.picono435.picojobs.common.platform.whitelist.WhitelistConverter;
import com.gmail.picono435.picojobs.common.platform.whitelist.WhitelistInformation;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class BukkitWhitelistConverter implements WhitelistConverter {
    @Override
    public WhitelistInformation convertWhitelist(Map<Type, List<String>> whitelist, Job job) {
        Map<Type, List<Object>> objectWhitelist = new HashMap<>();
        Map<Type, List<String>> stringWhitelist = new HashMap<>();
        for(Type type : whitelist.keySet()) {
            String whitelistType = type.getWhitelistType();
            List<Object> objects = new ArrayList<>();
            List<String> filteredNames = new ArrayList<>();
            if(whitelistType.equals("material")) {
                for(String s : whitelist.get(type)) {
                    Material matNew = MatchUtils.matchMaterial(s);
                    if(matNew == null) continue;
                    filteredNames.add("minecraft:" + matNew.toString().toLowerCase());
                    objects.add(matNew);
                }
                objectWhitelist.put(type, objects);
                stringWhitelist.put(type, filteredNames);
            } else if(whitelistType.equals("entity")) {
                for(String s : whitelist.get(type)) {
                    EntityType entityNew = MatchUtils.getEntityByName(s);
                    if(entityNew == null) continue;
                    filteredNames.add("minecraft:" + entityNew.toString().toLowerCase());
                    objects.add(entityNew);
                }
                objectWhitelist.put(type, objects);
                stringWhitelist.put(type, filteredNames);
            } else if(whitelistType.equals("job")) {
                new BukkitRunnable() {
                    public void run() {
                        for(String s : whitelist.get(type)) {
                            Job jobNew = PicoJobsAPI.getJobsManager().getJob(s);
                            if(jobNew == null) continue;
                            filteredNames.add(jobNew.getID());
                            objects.add(jobNew);
                        }
                        job.putInWhitelist(type, objects);
                        job.putInStringWhitelist(type, filteredNames);
                    }
                }.runTask(PicoJobsBukkit.getInstance());
            } else if(whitelistType.equals("color")) {
                for(String s : whitelist.get(type)) {
                    DyeColor colorNew = DyeColor.valueOf(s.toUpperCase(Locale.ROOT));
                    if(colorNew == null) continue;
                    filteredNames.add(colorNew.toString());
                    objects.add(colorNew);
                }
                objectWhitelist.put(type, objects);
                stringWhitelist.put(type, filteredNames);
            }
        }

        return new WhitelistInformation(objectWhitelist, stringWhitelist);
    }
}
