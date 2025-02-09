package com.gmail.picono435.picojobs.bukkit.platform;

import com.gmail.picono435.picojobs.bukkit.PicoJobsBukkit;
import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.platform.inventory.InventoryAdapter;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class BukkitInventoryAdapter implements InventoryAdapter {

    private Inventory inventory;
    private String title;

    public BukkitInventoryAdapter() {}

    public BukkitInventoryAdapter(Inventory inventory, String title) {
        this.inventory = inventory;
        this.title = title;
    }

    @Override
    public void create(String title, int size) {
        inventory = Bukkit.createInventory(null, size, title);
        this.title = title;
    }

    @Override
    public void setItem(int slot, ItemAdapter item) {
        inventory.setItem(slot, toItemStack(item));
    }

    @Override
    public ItemAdapter getItem(int slot) {
        return toItemAdapter(inventory.getItem(slot));
    }

    @Override
    public boolean isEmpty(int slot) {
        return inventory.getItem(slot) != null && inventory.getItem(slot).getType() == Material.AIR;
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public ItemAdapter toItemAdapter(Object object) {
        ItemStack itemStack = (ItemStack) object;
        ItemAdapter itemAdapter = new ItemAdapter(itemStack.getType().name().toLowerCase(Locale.ROOT), itemStack.getAmount(), (byte) itemStack.getDurability());

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta.hasDisplayName()) itemAdapter.setName(itemMeta.getDisplayName());
        if(itemMeta.hasLore()) itemAdapter.setLore(itemMeta.getLore());
        if(PicoJobsCommon.isMoreThan("1.14")) {
            if(itemMeta.hasCustomModelData()) itemAdapter.setData(itemMeta.getCustomModelData());
        }

        if(itemStack.containsEnchantment(Enchantment.FIRE_ASPECT)) itemAdapter.setEnchanted(true);

        return itemAdapter;
    }

    public ItemStack toItemStack(ItemAdapter itemAdapter) {
        Material material = PicoJobsBukkit.getNamespacedUtils().matchMaterial(itemAdapter.getMaterial());
        if(material == null) material = Material.STONE;
        ItemStack itemStack;
        if(itemAdapter.getDurability() != null) {
            itemStack = new ItemStack(material, itemAdapter.getAmount(), itemAdapter.getDurability());
        } else if(itemAdapter.getAmount() != null) {
            itemStack = new ItemStack(material, itemAdapter.getAmount());
        } else {
            itemStack = new ItemStack(material);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemAdapter.getName() != null) itemMeta.setDisplayName(itemAdapter.getName());
        if(itemAdapter.getLore() != null) itemMeta.setLore(itemAdapter.getLore());
        if(PicoJobsCommon.isMoreThan("1.14") && itemAdapter.getData() != null) {
            itemMeta.setCustomModelData(itemAdapter.getData());
        }

        if(itemAdapter.isEnchanted()) itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);

        if(PicoJobsCommon.isMoreThan("1.20.5")) {
            Attribute attribute;
            if(PicoJobsCommon.isMoreThan("1.21.3")) {
                attribute = Attribute.MOVEMENT_SPEED;
            } else {
                attribute = Attribute.valueOf("GENERIC_MOVEMENT_SPEED");
            }
            if(PicoJobsCommon.isMoreThan("1.21")) {
                itemMeta.addAttributeModifier(attribute, new AttributeModifier(NamespacedKey.fromString("hide_attributes"), 0, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.ANY));
            } else {
                itemMeta.addAttributeModifier(attribute, new AttributeModifier("dummy", 0, AttributeModifier.Operation.ADD_SCALAR));
            }
        }

        itemMeta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
