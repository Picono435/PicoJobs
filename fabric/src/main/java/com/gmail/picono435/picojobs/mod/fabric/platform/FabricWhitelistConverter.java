package com.gmail.picono435.picojobs.mod.fabric.platform;

import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.api.Type;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.whitelist.WhitelistConverter;
import com.gmail.picono435.picojobs.common.platform.whitelist.WhitelistInformation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.*;

public class FabricWhitelistConverter implements WhitelistConverter {
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
                    Item matNew = BuiltInRegistries.ITEM.get(new ResourceLocation(s.toLowerCase(Locale.ROOT)));
                    if(matNew == null) continue;
                    filteredNames.add("minecraft:" + matNew.toString().toLowerCase());
                    objects.add(matNew);
                }
                objectWhitelist.put(type, objects);
                stringWhitelist.put(type, filteredNames);
            } else if(whitelistType.equals("entity")) {
                for(String s : whitelist.get(type)) {
                    EntityType<?> entityNew = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(s.toLowerCase(Locale.ROOT)));
                    if(entityNew == null) continue;
                    filteredNames.add("minecraft:" + entityNew.toString().toLowerCase());
                    objects.add(entityNew);
                }
                objectWhitelist.put(type, objects);
                stringWhitelist.put(type, filteredNames);
            } else if(whitelistType.equals("job")) {
                PicoJobsCommon.getSchedulerAdapter().executeSync(() -> {
                    return;
                    //TODO: This does not work because server hasn't started yet
                    /*for(String s : whitelist.get(type)) {
                        Job jobNew = PicoJobsAPI.getJobsManager().getJob(s);
                        if(jobNew == null) continue;
                        filteredNames.add(jobNew.getID());
                        objects.add(jobNew);
                    }
                    job.putInWhitelist(type, objects);
                    job.putInStringWhitelist(type, filteredNames);*/
                });
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
