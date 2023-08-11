package com.gmail.picono435.picojobs.sponge.platform;

import com.gmail.picono435.picojobs.common.command.api.Sender;
import com.gmail.picono435.picojobs.common.inventory.ChooseJobMenu;
import com.gmail.picono435.picojobs.common.inventory.ClickAction;
import com.gmail.picono435.picojobs.common.inventory.WorkMenu;
import com.gmail.picono435.picojobs.common.listeners.InventoryMenuListener;
import com.gmail.picono435.picojobs.sponge.PicoJobsSponge;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.menu.InventoryMenu;
import org.spongepowered.api.item.inventory.menu.handler.SlotClickHandler;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.scheduler.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpongeSender implements Sender {

    private Audience sender;

    public SpongeSender(Audience sender) {
        this.sender = sender;
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof ServerPlayer;
    }

    @Override
    public boolean hasPermission(String permission) {
        if(sender instanceof ServerPlayer) {
            return ((ServerPlayer) sender).hasPermission(permission);
        } else {
            return true;
        }
    }

    @Override
    public boolean sendMessage(String message) {
        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
        return true;
    }

    @Override
    public UUID getUUID() {
        if(isPlayer()) {
            return ((Player) sender).uniqueId();
        } else {
            return null;
        }
    }

    @Override
    public void openInventory(String inventory) {
        if(!(sender instanceof ServerPlayer)) return;
        ServerPlayer player = (ServerPlayer) sender;
        SpongeInventoryAdapter inventoryAdapter;
        SlotClickHandler slotClickHandler;
        if(inventory.equals("choose-job")) {
            inventoryAdapter = (SpongeInventoryAdapter) ChooseJobMenu.createMenu(new SpongeInventoryAdapter(), this.getUUID());
            slotClickHandler = (cause, container, slot, slotIndex, clickType) -> {
                if(!container.cursor().isPresent()) return false;
                return !InventoryMenuListener.onBasicClick(new SpongeSender(container.viewer()),
                        new SpongeInventoryAdapter(container.currentMenu().get().inventory(), PlainTextComponentSerializer.plainText().serialize(container.currentMenu().get().title().get())),
                        slot.peek());
            };
        } else {
            inventoryAdapter = (SpongeInventoryAdapter) WorkMenu.createMenu(new SpongeInventoryAdapter(), this.getUUID(), inventory);
            slotClickHandler = (cause, container, slot, slotIndex, clickType) -> {
                ItemStack cursorStack = slot.peek();
                // This is a very odd temporary fix (I hope)
                Set<Object> objectList = new HashSet<>(InventoryMenuListener.actionItems.keySet());
                for(Object object : objectList) {
                    ItemStack itemStack = (ItemStack) object;
                    if(cursorStack.equalTo(itemStack)) {
                        ClickAction action = InventoryMenuListener.actionItems.get(object);
                        InventoryMenuListener.actionItems.remove(object);
                        InventoryMenuListener.actionItems.put(cursorStack, action);
                    }
                }
                return !InventoryMenuListener.onBasicClick(new SpongeSender(container.viewer()),
                        new SpongeInventoryAdapter(container.currentMenu().get().inventory(), PlainTextComponentSerializer.plainText().serialize(container.currentMenu().get().title().get())),
                        cursorStack);
            };
        }
        InventoryMenu inventoryMenu = ((ViewableInventory)inventoryAdapter.getInventory()).asMenu();
        inventoryMenu.setTitle(LegacyComponentSerializer.legacyAmpersand().deserialize(inventoryAdapter.getTitle()));
        inventoryMenu.registerSlotClick(slotClickHandler);
        inventoryMenu.open(player);
    }

    @Override
    public void closeInventory() {
        if(!isPlayer()) return;
        Sponge.server().scheduler().submit(Task.builder().execute(() -> {
            ((ServerPlayer) sender).closeInventory();
        }).delay(5, TimeUnit.MILLISECONDS).plugin(PicoJobsSponge.getInstance().getPluginContainer()).build());
    }

    @Override
    public void dispatchCommand(String command) {
        try {
            if(isPlayer()) {
                Sponge.server().commandManager().process((ServerPlayer) sender, command);
            } else {
                Sponge.server().commandManager().process(Sponge.systemSubject(), command);
            }
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }
}
