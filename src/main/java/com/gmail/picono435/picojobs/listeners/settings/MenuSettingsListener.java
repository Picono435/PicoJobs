package com.gmail.picono435.picojobs.listeners.settings;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.picono435.picojobs.menu.GUISettingsMenu;
import com.gmail.picono435.picojobs.utils.FileCreator;

public class MenuSettingsListener implements Listener {
	
	@EventHandler()
	public void onGUISettingsClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if(!p.hasPermission("picojobs.admin")) return;
		
		/*
		 * General GUI Settings Click
		 */
		if(GUISettingsMenu.generalInventories.contains(e.getInventory())) {
			e.setCancelled(true);
			
			switch(e.getSlot()) {
			case(11): {
				p.closeInventory();
				GUISettingsMenu.openChooseJobSettings(p);
				return;
			}
			case(13): {
				p.closeInventory();
				GUISettingsMenu.openNeedWorkSettings(p);
				return;
			}
			case(15): {
				p.closeInventory();
				GUISettingsMenu.openHasWorkSettings(p);
				return;
			}
			}
			return;
		}
		
		/*
		 * Item Edit GUI Settings Click
		 */
		if(GUISettingsMenu.itemEdit.containsKey(e.getInventory())) {
			e.setCancelled(true);
			ItemStack item = GUISettingsMenu.itemEdit.get(e.getInventory());
			String gui = StringUtils.substringBetween(e.getView().getTitle(), "[", "]");
			String itemSetting = StringUtils.substringBetween(e.getView().getTitle(), "(", ")");
			ConfigurationSection itemSettings = FileCreator.getGUI().getConfigurationSection("gui-settings").getConfigurationSection(gui).getConfigurationSection("items").getConfigurationSection(itemSetting);
			
			switch(e.getSlot()) {
			case(10): {
				// RENAME
				return;
			}
			case(12): {
				// LORE EDITOR
				return;
			}
			case(14): {
				// ENCHANTED
				boolean enchanted = itemSettings.getBoolean("enchanted");
				boolean newEnchanted = false;
				if(enchanted) {
					newEnchanted = false;
				} else {
					newEnchanted = true;
				}
				itemSettings.set("enchanted", newEnchanted);
				try {
					FileCreator.getGUI().save(FileCreator.getGUIFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				p.closeInventory();
				GUISettingsMenu.openGeneral(p);
				return;
			}
			case(16): {
				// ACTION
				return;
			}
			}
			return;
		}
		
		/*
		 * Choose Job GUI Settings Click
		 */
		if(GUISettingsMenu.guiSettings.containsKey(e.getInventory()) && GUISettingsMenu.guiSettings.get(e.getInventory()).equals("choose-job")) {
			e.setCancelled(true);
			
			
			return;
		}
		
		/*
		 * Need Work GUI Settings Click
		 */
		if(GUISettingsMenu.guiSettings.containsKey(e.getInventory()) && GUISettingsMenu.guiSettings.get(e.getInventory()).equals("need-work")) {
			if(e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				e.setCancelled(true);
				p.closeInventory();
				GUISettingsMenu.openItemEdit(p, e.getCurrentItem(), "need-work", StringUtils.substringBetween(e.getCurrentItem().getItemMeta().getDisplayName(), "[[", "]]"));
				return;
			}
			return;
		}
	}
	
}
