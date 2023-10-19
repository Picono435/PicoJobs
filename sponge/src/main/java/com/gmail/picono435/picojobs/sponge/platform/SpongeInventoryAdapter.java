package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.PicoJobsCommon;
import com.gmail.picono435.picojobs.common.inventory.ClickAction;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import com.gmail.picono435.picojobs.common.platform.inventory.InventoryAdapter;
import com.gmail.picono435.picojobs.common.platform.inventory.ItemAdapter;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ContainerType;
import org.spongepowered.api.item.inventory.ContainerTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.api.registry.RegistryTypes;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SpongeInventoryAdapter implements InventoryAdapter {

    private Inventory inventory;
    private String title;
    private int size;

    public SpongeInventoryAdapter() {}

    public SpongeInventoryAdapter(Inventory inventory, String title) {
        this.inventory = inventory;
        this.title = title;
    }

    @Override
    public void create(String title, int size) {
        Supplier<? extends ContainerType> containerType;
        int actualSize = size / 9;
        if(actualSize == 1) {
            containerType = ContainerTypes.GENERIC_9X1;
        } else if(actualSize == 2) {
            containerType = ContainerTypes.GENERIC_9X2;
        } else if(actualSize == 3) {
            containerType = ContainerTypes.GENERIC_9X3;
        } else if(actualSize == 4) {
            containerType = ContainerTypes.GENERIC_9X4;
        } else if(actualSize == 5) {
            containerType = ContainerTypes.GENERIC_9X5;
        } else if(actualSize == 6) {
            containerType = ContainerTypes.GENERIC_9X6;
        } else {
            containerType = ContainerTypes.GENERIC_9X3;
        }
        this.title = title;
        this.size = size;
        ViewableInventory.Builder.EndStep endStep = ViewableInventory.builder().type(containerType).completeStructure().plugin(PicoJobsSponge.getInstance().getPluginContainer());
        try {
            this.inventory = (Inventory) endStep.getClass().getMethod("build").invoke(endStep);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setItem(int slot, ItemAdapter item) {
        this.inventory.set(slot, toItemStack(item));
    }

    @Override
    public void setItem(int slot, ItemAdapter item, ClickAction clickAction) {
        ItemStack itemStack = toItemStack(item);
        this.inventory.set(slot, itemStack);
        InventoryMenuListener.actionItems.put(itemStack, clickAction);
    }

    @Override
    public ItemAdapter getItem(int slot) {
        return toItemAdapter(this.inventory.slot(slot).get().peek());
    }

    @Override
    public boolean isEmpty(int slot) {
        return this.inventory.slot(slot).get().peek() == ItemStack.empty();
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public ItemAdapter toItemAdapter(Object object) {
        ItemStack itemStack = (ItemStack) object;
        ItemAdapter itemAdapter = new ItemAdapter(itemStack.type().key(RegistryTypes.ITEM_TYPE).asString(), itemStack.quantity());

        if(itemStack.getKeys().contains(Keys.CUSTOM_NAME)) itemAdapter.setName(LegacyComponentSerializer.legacyAmpersand().serialize(itemStack.get(Keys.CUSTOM_NAME).get()));
        if(itemStack.getKeys().contains(Keys.LORE)) itemAdapter.setLore(itemStack.get(Keys.LORE).get().stream().map((component) -> LegacyComponentSerializer.legacyAmpersand().serialize(component)).collect(Collectors.toList()));
        if(PicoJobsCommon.isMoreThan("1.14")) {
            if(itemStack.getKeys().contains(Keys.CUSTOM_MODEL_DATA)) itemAdapter.setData(itemStack.get(Keys.CUSTOM_MODEL_DATA).get());
        }

        if(itemStack.getKeys().contains(Keys.APPLIED_ENCHANTMENTS)) itemAdapter.setEnchanted(true);

        return itemAdapter;
    }

    public ItemStack toItemStack(ItemAdapter itemAdapter) {
        ItemType itemType = ItemTypes.registry().value(ResourceKey.resolve(itemAdapter.getMaterial().toLowerCase(Locale.ROOT)));
        if(itemType == null) itemType = ItemTypes.STONE.get();
        ItemStack itemStack;
        /*if(itemAdapter.getDurability() != null) {
            itemStack = ItemStack.of(itemType, itemAdapter.getAmount());
        } else */
        if(itemAdapter.getAmount() != null) {
            itemStack = ItemStack.of(itemType, itemAdapter.getAmount());
        } else {
            itemStack = ItemStack.of(itemType);
        }

        if(itemAdapter.getName() != null) itemStack.offer(Keys.CUSTOM_NAME, LegacyComponentSerializer.legacyAmpersand().deserialize(itemAdapter.getName()));
        if(itemAdapter.getLore() != null) itemStack.offer(Keys.LORE, itemAdapter.getLore().stream().map((string) -> LegacyComponentSerializer.legacyAmpersand().deserialize(string)).collect(Collectors.toList()));
        if(PicoJobsCommon.isMoreThan("1.14") && itemAdapter.getData() != null) {
            itemStack.offer(Keys.CUSTOM_MODEL_DATA, itemAdapter.getData());
        }
        itemStack.offer(Keys.HIDE_ATTRIBUTES, true);
        itemStack.offer(Keys.HIDE_ENCHANTMENTS, true);

        if(itemAdapter.isEnchanted()) itemStack.offer(Keys.APPLIED_ENCHANTMENTS, Collections.singletonList(Enchantment.of(EnchantmentTypes.POWER, 1)));

        return itemStack;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
