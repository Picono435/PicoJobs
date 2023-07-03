package com.gmail.picono435.picojobs.mod.platform;

import com.gmail.picono435.picojobs.common.inventory.ClickAction;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import com.gmail.picono435.picojobs.common.platform.inventory.InventoryAdapter;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;
import com.gmail.picono435.picojobs.mod.container.JobsContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Locale;

public class ModInventoryAdapter implements InventoryAdapter {

    private String title;
    private int size;
    private JobsContainer simpleContainer;
    private final Inventory inventory;
    private int id;
    private boolean building = true;

    public ModInventoryAdapter(Inventory inventory, int id) {
        this. inventory = inventory;
        this.id = id;
    }

    @Override
    public void create(String title, int size) {
       this.title = title;
       this.size = size;
        this.simpleContainer = new JobsContainer(size, this);
    }

    @Override
    public void setItem(int slot, ItemAdapter item) {
        this.simpleContainer.setItem(slot, toItemStack(item));
    }

    @Override
    public void setItem(int slot, ItemAdapter item, ClickAction clickAction) {
        ItemStack itemStack = toItemStack(item);
        this.simpleContainer.setItem(slot, itemStack);
        InventoryMenuListener.actionItems.put(itemStack, clickAction);
    }

    @Override
    public ItemAdapter getItem(int slot) {
        return toItemAdapter(this.simpleContainer.getItem(slot));
    }

    @Override
    public boolean isEmpty(int slot) {
        return this.simpleContainer.getItem(slot).isEmpty();
    }

    @Override
    public int getSize() {
        return this.simpleContainer.getContainerSize();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public boolean isBuilding() {
        return building;
    }

    public ItemStack toItemStack(ItemAdapter itemAdapter) {
        Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(itemAdapter.getMaterial().toLowerCase(Locale.ROOT)));
        ItemStack itemStack;
        if (itemAdapter.getAmount() != null) {
            itemStack = new ItemStack(item, itemAdapter.getAmount());
        } else {
            itemStack = new ItemStack(item);
        }

        CompoundTag compoundTag = itemStack.getOrCreateTagElement("display");
        if(itemAdapter.getName() != null) compoundTag.putString("Name", Component.Serializer.toJson(Component.literal(itemAdapter.getName())));
        if(itemAdapter.getLore() != null) {
            ListTag loreTag = new ListTag();
            loreTag.addAll(itemAdapter.getLore().stream().map((lore) -> StringTag.valueOf(Component.Serializer.toJson(Component.literal(lore)))).toList());
            compoundTag.put("Lore", loreTag);
        }
        if (itemAdapter.getData() != null) {
            itemStack.getTag().putInt("CustomModelData", itemAdapter.getData());
        }
        itemStack.getTag().putInt("HideFlags", 255);

        if (itemAdapter.isEnchanted()) itemStack.enchant(Enchantments.POWER_ARROWS, 1);

        return itemStack;
    }

    public ItemAdapter toItemAdapter(ItemStack itemStack) {
        ItemAdapter itemAdapter;
        itemAdapter = new ItemAdapter(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString(), itemStack.getCount());

        CompoundTag compoundTag = itemStack.getOrCreateTagElement("display");
        if(compoundTag.contains("Name")) itemAdapter.setName(Component.Serializer.fromJson(compoundTag.getString("Name")).getString());
        if(compoundTag.contains("Lore")) itemAdapter.setLore(((ListTag)compoundTag.get("Lore")).stream().map(Tag::getAsString).toList());
        if(compoundTag.contains("CustomModelData")) itemAdapter.setData(compoundTag.getInt("CustomModelData"));

        if (itemStack.isEnchanted()) itemAdapter.setEnchanted(true);

        return itemAdapter;
    }

    public ChestMenu getChestMenu() {
        MenuType<?> menuType = BuiltInRegistries.MENU.get(new ResourceLocation("generic_9x" + this.size / 9));
        this.building = false;
        return new ChestMenu(menuType, this.id, inventory, this.simpleContainer, this.size / 9);
    }
}
