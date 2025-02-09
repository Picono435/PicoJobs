package com.gmail.picono435.picojobs.bukkit.listeners;

import com.gmail.picono435.picojobs.bukkit.platform.BukkitInventoryAdapter;
import com.gmail.picono435.picojobs.bukkit.platform.BukkitSender;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BukkitInventoryMenuListener implements Listener {

	@EventHandler()
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
		if(InventoryMenuListener.onBasicClick(new BukkitSender(event.getWhoClicked()),
				new BukkitInventoryAdapter(event.getInventory(), getInventoryTitle(event.getView())),
				event.getCurrentItem())) {
			event.setCancelled(true);
		}
	}

	/**
	 * This exists only for a single reason. InventoryView became an interface in modern spigot API versions
	 * because of this the plugin stopped working in older ones. This method uses reflection to fix a
	 * {@link IncompatibleClassChangeError}.
	 *
	 * @param view the inventory view
	 * @return the inventory title
	 */
	private String getInventoryTitle(Object view) {
        try {
            Method method = view.getClass().getMethod("getTitle");
			method.setAccessible(true);
			return (String) method.invoke(view);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
			return "Unknown Title";
        }
    }
}
