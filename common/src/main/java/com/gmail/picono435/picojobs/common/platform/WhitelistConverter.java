package com.gmail.picono435.picojobs.common.platform;

import com.gmail.picono435.picojobs.api.Type;

import java.util.List;
import java.util.Map;

public interface WhitelistConverter {

    Map<Type, List<Object>> convertWhitelist(Map<Type, List<String>> whitelist);

    //TODO: Actually do this lol
    /*
    this.whitelist = new HashMap<>();
			for(Type type : whitelist.keySet()) {
				String whitelistType = type.getWhitelistType();
				List<Object> objects = new ArrayList<>();
				List<String> filteredNames = new ArrayList<>();
				if(whitelistType.equals("material")) {
					for(String s : whitelist.get(type)) {
						Material matNew = OtherUtils.matchMaterial(s);
						if(matNew == null) continue;
						filteredNames.add("minecraft:" + matNew.toString().toLowerCase());
						objects.add(matNew);
					}
					this.whitelist.put(type, objects);
					this.stringWhitelist.put(type, filteredNames);
				} else if(whitelistType.equals("entity")) {
					for(String s : whitelist.get(type)) {
						EntityType entityNew = OtherUtils.getEntityByName(s);
						if(entityNew == null) continue;
						filteredNames.add("minecraft:" + entityNew.toString().toLowerCase());
						objects.add(entityNew);
					}
					this.whitelist.put(type, objects);
					this.stringWhitelist.put(type, filteredNames);
				} else if(whitelistType.equals("job")) {
					Job j = this;
					new BukkitRunnable() {
						public void run() {
							for(String s : whitelist.get(type)) {
								Job jobNew = PicoJobsAPI.getJobsManager().getJob(s);
								if(jobNew == null) continue;
								filteredNames.add(jobNew.getID());
								objects.add(jobNew);
							}
							j.whitelist.put(type, objects);
							j.stringWhitelist.put(type, filteredNames);
						}
					}.runTask(PicoJobsPlugin.getInstance());
				} else if(whitelistType.equals("color")) {
					for(String s : whitelist.get(type)) {
						DyeColor colorNew = DyeColor.valueOf(s.toUpperCase(Locale.ROOT));
						if(colorNew == null) continue;
						filteredNames.add(colorNew.toString());
						objects.add(colorNew);
					}
					this.whitelist.put(type, objects);
					this.stringWhitelist.put(type, filteredNames);
				}
			}
     */
}
