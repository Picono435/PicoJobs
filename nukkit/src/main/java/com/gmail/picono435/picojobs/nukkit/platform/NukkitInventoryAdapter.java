package com.gmail.picono435.picojobs.nukkit.platform;

import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.bow.EnchantmentBowPower;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.inventory.ClickAction;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import com.gmail.picono435.picojobs.common.platform.inventory.InventoryAdapter;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;
import me.iwareq.fakeinventories.FakeInventory;

import java.util.Arrays;

public class NukkitInventoryAdapter implements InventoryAdapter {

    private Inventory inventory;
    private String title;

    public NukkitInventoryAdapter() {}

    public NukkitInventoryAdapter(Inventory inventory, String title) {
        this.inventory = inventory;
        this.title = title;
    }

    @Override
    public void create(String title, int size) {
        if(size == 54) {
            inventory = new FakeInventory(InventoryType.DOUBLE_CHEST, title);
        } else if(size == 27) {
            inventory = new FakeInventory(InventoryType.CHEST, title);
        } else {
            PicoJobsCommon.getLogger().warn("GUI with title '" + title + "' will not have the correct size which can cause issues. Nukkit only supports inventories with size '54' or '27'.");
            inventory = new FakeInventory(InventoryType.CHEST, title);
        }
        this.title = title;
    }

    @Override
    public void setItem(int slot, ItemAdapter item) {
        inventory.setItem(slot, toItem(item));
    }

    @Override
    public void setItem(int slot, ItemAdapter item, ClickAction clickAction) {
        Item itemStack = toItem(item);
        inventory.setItem(slot, itemStack);
        InventoryMenuListener.actionItems.put(itemStack, clickAction);
    }

    @Override
    public ItemAdapter getItem(int slot) {
        return toItemAdapter(inventory.getItem(slot));
    }

    @Override
    public boolean isEmpty(int slot) {
        return inventory.getItem(slot) != null;
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public Item toItem(ItemAdapter itemAdapter) {
        String material = itemAdapter.getMaterial();
        if(itemAdapter.getDurability() != null) material = material + ":" + itemAdapter.getDurability();
        Item item = Item.fromString(material);

        if(itemAdapter.getAmount() != null) item.setCount(itemAdapter.getAmount());

        if(itemAdapter.getName() != null) item.setCustomName(itemAdapter.getName());
        if(itemAdapter.getLore() != null) itemAdapter.setLore(itemAdapter.getLore());
        item.getNamedTag().putInt("HideFlags", 255);

        if(itemAdapter.isEnchanted()) item.addEnchantment(new EnchantmentBowPower());

        return item;
    }

    public ItemAdapter toItemAdapter(Item item) {
        ItemAdapter itemAdapter = new ItemAdapter(item.getRuntimeEntry().getIdentifier(), item.getCount(), (byte) item.getDamage());

        if(item.hasCustomName()) itemAdapter.setName(item.getCustomName());
        if(item.getLore().length > 0) itemAdapter.setLore(Arrays.asList(item.getLore()));

        if(item.hasEnchantment(19)) itemAdapter.setEnchanted(true);

        return itemAdapter;
    }

    public Inventory getInventory() {
        return inventory;
    }
}