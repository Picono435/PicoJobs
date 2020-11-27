package com.gmail.picono435.picojobs.menu;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

public class ActionMenu {
	
	private Player p;
	private ActionEnum action;
	private List<Object> values;
	
	public ActionMenu(Player p, ActionEnum action, Object... values) {
		this.p = p;
		this.action = action;
		this.values = Arrays.asList(values);
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public ActionEnum getAction() {
		return action;
	}
	
	public List<Object> getValues() {
		return values;
	}
}