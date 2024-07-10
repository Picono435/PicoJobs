package com.gmail.picono435.picojobs.api;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;

import java.util.*;

/**
 * Represents a job type
 * 
 * @author Picono435
 *
 */
public enum Type {
	BREAK(WhitelistType.BLOCK),
	KILL(WhitelistType.JOB),
	FISHING(WhitelistType.ITEM),
	PLACE(WhitelistType.BLOCK),
	CRAFT(WhitelistType.ITEM),
	TAME(WhitelistType.ENTITY),
	SHEAR(WhitelistType.DYE),
	FILL(WhitelistType.BLOCK),
	SMELT(WhitelistType.ITEM),
	EAT(WhitelistType.ITEM),
	ENCHANTING(WhitelistType.ITEM),
	REPAIR(WhitelistType.ITEM),
	FILL_ENTITY(WhitelistType.ENTITY),
	MOVE(WhitelistType.BLOCK),
	TRADE(WhitelistType.ITEM),
	KILL_ENTITY(WhitelistType.ENTITY),
	STRIP_LOGS(WhitelistType.BLOCK);
	
	private final static Map<String, Type> BY_NAME = new HashMap<String, Type>();
	
	static {
		for(Type type : values()) {
			BY_NAME.put(type.name(), type);
		}
	}
	
	public static Type getType(String name) {
		return BY_NAME.get(name.toUpperCase(Locale.ROOT));
	}

	public static List<Type> getTypes(List<String> names) {
		List<Type> types = new ArrayList<Type>();
		for(String name : names) {
			if(BY_NAME.containsKey(name.toUpperCase(Locale.ROOT))) {
				types.add(getType(name.toUpperCase(Locale.ROOT)));
			} else {
				PicoJobsCommon.getLogger().error("The job type '" + name.toUpperCase(Locale.ROOT) + "' does not exist. Make sure to use a valid job type.");
			}
		}
		return types;
	}
	
	private final WhitelistType whitelistType;
	
	private Type(WhitelistType whitelistType) {
		this.whitelistType = whitelistType;
	}
	
	public WhitelistType getWhitelistType() {
		return whitelistType;
	}

	public enum WhitelistType {
		ITEM(),
		BLOCK(),
		ENTITY(),
		DYE(),
		JOB()
	}
}
